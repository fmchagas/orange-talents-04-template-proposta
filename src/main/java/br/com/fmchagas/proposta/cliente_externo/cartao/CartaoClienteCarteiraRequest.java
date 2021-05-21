package br.com.fmchagas.proposta.cliente_externo.cartao;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import br.com.fmchagas.proposta.carteira.NovaCarteiraRequest;
import br.com.fmchagas.proposta.carteira.TipoCarteira;

public class CartaoClienteCarteiraRequest {
	
	private @NotBlank String email;
	private @NotNull TipoCarteira carteira;

	public CartaoClienteCarteiraRequest(@Valid NovaCarteiraRequest request) {
		email = request.getEmail();
		carteira = request.getCarteira();
	}
	
	public String getEmail() {
		return email;
	}
	
	public TipoCarteira getCarteira() {
		return carteira;
	}
}
