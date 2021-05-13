package br.com.fmchagas.proposta.nova_proposta;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public final class GetPropostaController {
	
	private PropostaRepository propostaRepository;
	
	@Autowired
	public GetPropostaController(PropostaRepository propostaRepository) {
		this.propostaRepository = propostaRepository;
	}
	
	@GetMapping("/api/propostas/{id}")
	public ResponseEntity<?> lista(@PathVariable Long id) {
		Optional<Proposta> possivelProposta = propostaRepository.findById(id);
		
		if(possivelProposta.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		
		PropostaResponse response = new PropostaResponse(possivelProposta.get());
		
		return ResponseEntity.ok(response);
	}
}
