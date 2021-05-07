package br.com.fmchagas.proposta.nova_proposta;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PropostaController {
	
	@PostMapping("/api/propostas")
	public String cadastrar(@RequestBody @Valid NovaPropostaRequest request) {
		
		return "aheeeeeeeeeeeee";
	}
}
