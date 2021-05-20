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
import br.com.fmchagas.proposta.cliente_externo.cartao.CartaoCliente;
import br.com.fmchagas.proposta.cliente_externo.cartao.CartaoClienteNovoAvisoViagemRequest;
import feign.FeignException;

@RestController
public class AvisoViagemController {
	
	private CartaoRepository cartaoRepository;
	private AvisoViagemRepository viagemRepository;
	private CartaoCliente cartaoCliene;
	
	@Autowired
	public AvisoViagemController(CartaoRepository cartaoRepository,
			AvisoViagemRepository viagemRepository,
			CartaoCliente cartaoCliene) {
		
		this.cartaoRepository = cartaoRepository;
		this.viagemRepository = viagemRepository;
		this.cartaoCliene = cartaoCliene;
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
		
		try {
			cartaoCliene.avisoViagemViaHttp(cartao.getNumero(), new CartaoClienteNovoAvisoViagemRequest(request.getDestino(), request.getValidoAte()));
			
			AvisoViagem avisoViagem = request.toModel(httpServletRequest, cartao);
			viagemRepository.save(avisoViagem);
			
			return new ResponseEntity<>(HttpStatus.OK);
			
		}catch(FeignException ex) {
			throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Desculpe o transtorno, não conseguimos processar o aviso de viagem");
		}
	}
}
