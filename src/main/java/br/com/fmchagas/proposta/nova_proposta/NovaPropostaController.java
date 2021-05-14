package br.com.fmchagas.proposta.nova_proposta;

import java.net.URI;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.fmchagas.proposta.cliente_externo.solicitacao.SolicitacaoCliente;
import br.com.fmchagas.proposta.cliente_externo.solicitacao.SolicitacaoClienteRequest;
import br.com.fmchagas.proposta.cliente_externo.solicitacao.SolicitacaoClienteResponse;
import feign.FeignException;

@RestController
public class NovaPropostaController {
	
	private PropostaRepository propostaRepository;
	private SolicitacaoCliente solicitacao;
	
	@Autowired
	public NovaPropostaController(PropostaRepository propostaRepository, SolicitacaoCliente solicitacao) {
		this.propostaRepository = propostaRepository;
		this.solicitacao = solicitacao;
	}
	
	@PostMapping("/api/propostas")
	public ResponseEntity<?> cadastrar(@RequestBody @Valid NovaPropostaRequest request, UriComponentsBuilder uriBuilder) {
		boolean existeProposta = propostaRepository.existsByDocumento(request.getDocumento());
		
		if(existeProposta) {
			throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Desculpe o transtorno, encontramos o documento cadastrado em nosso sistema");
		}
		
		Proposta proposta = request.converteParaModelo();
		salvaEComita(proposta);
		
		atribuiElegibilidadePara(proposta);
		
		salvaEComita(proposta);
		
		URI uri = uriBuilder.path("/propostas/{id}").buildAndExpand(proposta.getId()).toUri();
		
		return ResponseEntity.created(uri).build();
	}
	
	@Transactional
	private void salvaEComita(Proposta proposta) {
		propostaRepository.save(proposta);
	}

	
	private void atribuiElegibilidadePara(Proposta proposta) {
		SolicitacaoClienteResponse resultadoSolicitacao;
		
		try {
			resultadoSolicitacao = solicitacao.consultaViaHttp(
						new SolicitacaoClienteRequest(proposta.getDocumento(),
								proposta.getNome(),
								proposta.getId()));
			
			proposta.setElegibilidade(resultadoSolicitacao.paraElegibilidade());

		}catch (FeignException.UnprocessableEntity e) {
			try {
				/*
				 * Converte Json para Objeto
				 */
				resultadoSolicitacao = new ObjectMapper().readValue(e.contentUTF8(), SolicitacaoClienteResponse.class);
				
				proposta.setElegibilidade(resultadoSolicitacao.paraElegibilidade());
			} catch (JsonProcessingException ex) {
				Assert.state(ex==null, "Algum problema aconteceu quando tentavamos converte o Json para Objeto! Mensagem da exception: "
								+ ex.getMessage());
			}
		}
	}
}
