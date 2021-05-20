package br.com.fmchagas.proposta.aviso_viagem;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.com.fmchagas.proposta.cartao.Cartao;
import br.com.fmchagas.proposta.cartao.CartaoRepository;

@RestController //total pts 7
public class AvisoViagemController {
	
	private CartaoRepository cartaoRepository;
	private AvisoViagemRepository viagemRepository;
	private EnviaAvisoViagemComFeign avisoViagemComFeign;
	
	@Autowired
	public AvisoViagemController(CartaoRepository cartaoRepository,
			AvisoViagemRepository viagemRepository,
			EnviaAvisoViagemComFeign avisoViagemComFeign) {
		
		this.cartaoRepository = cartaoRepository;
		this.viagemRepository = viagemRepository;
		this.avisoViagemComFeign = avisoViagemComFeign;
	}
	
	@PostMapping("/api/cartoes/{id}/avisos")
	public ResponseEntity<?> cadastrar(@PathVariable Long id, 
			@RequestBody @Valid NovoAvisoViagemRequest request,  
			HttpServletRequest httpServletRequest) {
		
		Optional<Cartao> possivelCartao = cartaoRepository.findById(id);
		
		if(possivelCartao.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Desculpe o transtorno, não encontramos o cartão cadastrado em nosso sistema");
		}
		
		@NotNull Cartao cartao = possivelCartao.get();
		
		avisoViagemComFeign.enviar(request, cartao);
		
		AvisoViagem avisoViagem = request.toModel(httpServletRequest, cartao);
		viagemRepository.save(avisoViagem);
		
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
