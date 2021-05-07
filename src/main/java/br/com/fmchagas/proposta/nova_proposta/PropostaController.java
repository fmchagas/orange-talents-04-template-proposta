package br.com.fmchagas.proposta.nova_proposta;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
public class PropostaController {
	
	private PropostaRepository propostaRepository;
	
	@Autowired
	public PropostaController(PropostaRepository propostaRepository) {
		this.propostaRepository = propostaRepository;
	}
	
	@PostMapping("/api/propostas")
	public ResponseEntity<?> cadastrar(@RequestBody @Valid NovaPropostaRequest request, UriComponentsBuilder uriBuilder) {
		
		Proposta proposta = request.converteParaModelo();
		propostaRepository.save(proposta);
		
		URI uri = uriBuilder.path("/propostas/{id}").buildAndExpand(proposta.getId()).toUri();
		
		return ResponseEntity.created(uri).build();
	}
}
