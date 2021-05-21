package br.com.fmchagas.proposta.carteira;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import br.com.fmchagas.proposta.cartao.Cartao;
import br.com.fmchagas.proposta.cliente_externo.cartao.CartaoCliente;
import br.com.fmchagas.proposta.cliente_externo.cartao.CartaoClienteCarteiraRequest;
import feign.FeignException;

@Component
public class AssociaCarteiraComFeign {
	
	private CartaoCliente cartaoCliente;

	public AssociaCarteiraComFeign(CartaoCliente cartaoCliente) {
		this.cartaoCliente = cartaoCliente;
	}

	public void associar(@NotNull Cartao cartao, @Valid NovaCarteiraRequest request) {
		try {
			cartaoCliente.associaCarteiraViaHttp(cartao.getNumero(), new CartaoClienteCarteiraRequest(request));
		}catch(FeignException ex) {
			throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Não conseguimos processar a associação do cartão");
		}
	}
}
