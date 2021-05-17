package br.com.fmchagas.proposta.biometria;

import java.net.URI;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.fmchagas.proposta.cartao.Cartao;
import br.com.fmchagas.proposta.cartao.CartaoRepository;

@RestController
public class NovaBiometriaController {
	
	private CartaoRepository cartaoRepository;
	private BiometriaRepository biometriaRepository;
	private FingerPrintTemBase64Validator fingerPrintValidator;

	@Autowired
	public NovaBiometriaController(CartaoRepository cartaoRepository,
			BiometriaRepository biometriaRepository,
			FingerPrintTemBase64Validator fingerPrintValidator) {
		
		this.cartaoRepository = cartaoRepository;
		this.biometriaRepository = biometriaRepository;
		this.fingerPrintValidator = fingerPrintValidator;
	}
	
	@InitBinder
	public void init(WebDataBinder binder) {
		binder.addValidators(fingerPrintValidator);
	}
	
	@PostMapping("api/cartoes/{id}/biometria")
	@Transactional
	public ResponseEntity<?> cadastrar(@PathVariable Long id, 
			@RequestBody @Valid NovaBiometriaRequest request, 
			UriComponentsBuilder uriBuilder) {
		
		Optional<Cartao> possivelCartao = cartaoRepository.findById(id);
		
		if(possivelCartao.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Desculpe o transtorno, não encontramos o cartão cadastrado em nosso sistema");
		}
		
		Biometria biometria = request.toModelo(possivelCartao.get());
		
		biometriaRepository.save(biometria);
		
		URI location = uriBuilder.path("/api/biometrias/{id}").buildAndExpand(biometria.getId()).toUri();
		
		return ResponseEntity.created(location).build();
	}
}
