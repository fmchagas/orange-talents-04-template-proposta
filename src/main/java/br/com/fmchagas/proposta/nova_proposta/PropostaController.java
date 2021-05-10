package br.com.fmchagas.proposta.nova_proposta;

import java.net.URI;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.fmchagas.proposta.cliente_externo.solicitacao.Solicitacao;
import br.com.fmchagas.proposta.cliente_externo.solicitacao.SolicitacaoRequest;
import br.com.fmchagas.proposta.cliente_externo.solicitacao.SolicitacaoResponse;
import feign.FeignException;

@RestController
public class PropostaController {
	
	private PropostaRepository propostaRepository;
	private Solicitacao solicitacao;
	
	@Autowired
	public PropostaController(PropostaRepository propostaRepository, Solicitacao solicitacao) {
		this.propostaRepository = propostaRepository;
		this.solicitacao = solicitacao;
	}
	
	@PostMapping("/api/propostas")
	@Transactional
	public ResponseEntity<?> cadastrar(@RequestBody @Valid NovaPropostaRequest request, UriComponentsBuilder uriBuilder) {
		int contaProposta = propostaRepository.countByDocumento(request.getDocumento());
		
		if(contaProposta > 0) {
			throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Desculpe o transtorno, encontramos o documento cadastrado em nosso sistema");
		}
		
		Proposta proposta = request.converteParaModelo();
		propostaRepository.save(proposta);
		
		atribuiElegibilidadePara(proposta);
		
		propostaRepository.save(proposta);
		
		URI uri = uriBuilder.path("/propostas/{id}").buildAndExpand(proposta.getId()).toUri();
		
		return ResponseEntity.created(uri).build();
	}

	
	private void atribuiElegibilidadePara(Proposta proposta) {
		SolicitacaoResponse resultadoSolicitacao;
		
		try {
			resultadoSolicitacao = solicitacao.consultaViaHttp(
						new SolicitacaoRequest(proposta.getDocumento(),
								proposta.getNome(),
								proposta.getId()));
			
			proposta.setElegibilidade(resultadoSolicitacao.paraElegibilidade());

		}catch (FeignException.UnprocessableEntity e) {
			try {
				/*
				 * Converte Json para Objeto
				 */
				resultadoSolicitacao = new ObjectMapper().readValue(e.contentUTF8(), SolicitacaoResponse.class);
				
				proposta.setElegibilidade(resultadoSolicitacao.paraElegibilidade());
			} catch (JsonProcessingException ex) {
				ex.printStackTrace();
			}
		}
	}
}
