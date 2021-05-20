package br.com.fmchagas.proposta.cartao.bloqueio;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.com.fmchagas.proposta.cartao.Cartao;
import br.com.fmchagas.proposta.cartao.CartaoRepository;

@RestController // total pts 7
public class NovoBloqueioController {
	
	private CartaoRepository cartaoRepository;
	private BloqueiRepository bloqueiRepository;
	private EnviaBloqueioComFeign bloqueio;
	
	
	@Autowired
	public NovoBloqueioController(CartaoRepository cartaoRepository, 
			BloqueiRepository bloqueiRepository,
			EnviaBloqueioComFeign bloqueio) {
		
		this.cartaoRepository = cartaoRepository;
		this.bloqueiRepository = bloqueiRepository;
		this.bloqueio = bloqueio;
	}
	
	@PostMapping("/api/cartoes/{id}/bloqueios")
	public ResponseEntity<?> bloquear(@PathVariable Long id, HttpServletRequest httpServletRequest){
		
		Optional<Cartao> possivelCartao = cartaoRepository.findById(id);
		
		if(possivelCartao.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Desculpe o transtorno, não encontramos o cartão cadastrado em nosso sistema");
		}
		
		Cartao cartao = possivelCartao.get();
		
		if(!cartao.podeBloquear()) {
			throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Cartão já está bloqueado");
		}
		
		bloqueio.enviar(cartao);
		
		Bloqueio bloqueio = new NovoBloqueioRequest().toModel(httpServletRequest, cartao);
		cartao.bloqueia();
		
		bloqueiRepository.save(bloqueio);
		
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
