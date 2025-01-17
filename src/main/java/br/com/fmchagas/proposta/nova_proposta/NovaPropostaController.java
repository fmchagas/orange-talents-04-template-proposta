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

import br.com.fmchagas.proposta.metricas.ContadorDeProposta;
import io.opentracing.Span;
import io.opentracing.Tracer;

@RestController //total 5 pts
public class NovaPropostaController {
	
	private PropostaRepository propostaRepository;
	private ElegibilidadeNovaProposta elegibilidade;
	private ContadorDeProposta metrica;
	private Tracer tracer;
	
	@Autowired
	public NovaPropostaController(PropostaRepository propostaRepository, 
			ElegibilidadeNovaProposta elegibilidade,
			ContadorDeProposta metrica, Tracer tracer) {
		
		this.propostaRepository = propostaRepository;
		this.elegibilidade = elegibilidade;
		this.metrica = metrica;
		this.tracer = tracer;
	}
	
	@PostMapping("/api/propostas")
	public ResponseEntity<?> cadastrar(@RequestBody @Valid NovaPropostaRequest request, UriComponentsBuilder uriBuilder) {
		Span activeSpan = tracer.activeSpan();
		activeSpan.setTag("tag.nome", "api.propostas");
		activeSpan.setBaggageItem("bad.nome", "api.propostas");
		
		boolean existeProposta = propostaRepository.existsByDocumento(request.getDocumento());
		
		if(existeProposta) {
			throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Desculpe o transtorno, encontramos o documento cadastrado em nosso sistema");
		}
		
		Proposta proposta = request.converteParaModelo();
		
		salvaECommit(proposta);
		
		elegibilidade.atribuirPara(proposta);
		
		salvaECommit(proposta);
		
		metrica.conta(proposta);
		
		URI uri = uriBuilder.path("/propostas/{id}").buildAndExpand(proposta.getId()).toUri();
		
		return ResponseEntity.created(uri).build();
	}
	
	@Transactional
	private void salvaECommit(Proposta proposta) {
		propostaRepository.save(proposta);
	}
}
