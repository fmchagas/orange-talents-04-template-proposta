package br.com.fmchagas.proposta.nova_proposta;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
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
		int contaProposta = propostaRepository.countByDocumento(request.getDocumento());
		
		if(contaProposta > 0) {
			//return ResponseEntity.unprocessableEntity().body("Desculpe o transtorno, encontramos o documento cadastrado em nosso sistema");
			throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Desculpe o transtorno, encontramos o documento cadastrado em nosso sistema");
		}
		
		Proposta proposta = request.converteParaModelo();
		propostaRepository.save(proposta);
		
		URI uri = uriBuilder.path("/propostas/{id}").buildAndExpand(proposta.getId()).toUri();
		
		return ResponseEntity.created(uri).build();
	}
}
