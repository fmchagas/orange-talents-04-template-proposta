package br.com.fmchagas.proposta.carteira;

import java.net.URI;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.fmchagas.proposta.cartao.Cartao;
import br.com.fmchagas.proposta.cartao.CartaoRepository;

@RestController
public class CarteiraController {

	private CartaoRepository cartaoRepository;
	private CarteiraRepository carteiraRepository;
	private AssociaCarteiraComFeign associaCarteira;

	@Autowired
	public CarteiraController(CartaoRepository cartaoRepository, 
			CarteiraRepository carteiraRepository,
			AssociaCarteiraComFeign associaCarteira) {
		
		this.cartaoRepository = cartaoRepository;
		this.carteiraRepository = carteiraRepository;
		this.associaCarteira = associaCarteira;
	}

	@PostMapping("/api/cartoes/{id}/carteiras")
	public ResponseEntity<?> cadastrar(@PathVariable Long id, @RequestBody @Valid NovaCarteiraRequest request,
			UriComponentsBuilder uriBuilder) {

		Optional<Cartao> possivelCartao = cartaoRepository.findById(id);

		if (possivelCartao.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND,
					"Desculpe o transtorno, não encontramos o cartão cadastrado em nosso sistema");
		}

		Cartao cartao = possivelCartao.get();

		Carteira carteira = request.toModel(cartao);

		if (!cartao.podeAssociarCarteira(carteira)) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Carteira já esta associada ao cartão");
		}
		
		associaCarteira.associar(cartao, request);
		
		carteiraRepository.save(carteira);

		URI uri = uriBuilder.path("/carteiras/{id}").buildAndExpand(carteira.getId()).toUri();

		return ResponseEntity.created(uri).build();
	}
}
