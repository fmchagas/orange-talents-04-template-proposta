package br.com.fmchagas.proposta.carteira;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.util.Assert;

import br.com.fmchagas.proposta.cartao.Cartao;

public class NovaCarteiraRequest {
	
	@NotBlank @Email
	private String email;
	
	@NotNull
	private TipoCarteira carteira;

	public NovaCarteiraRequest(@NotBlank @Email String email, 
			@NotNull TipoCarteira carteira) {
		
		this.email = email;
		this.carteira = carteira;
	}

	public Carteira toModel(@NotNull Cartao cartao) {
		Assert.notNull(cartao, "Ops, o Cartao não deveria ser nulo neste ponto");
		
		return new Carteira(email, carteira, cartao);
	}
	
	public String getEmail() {
		return email;
	}
	
	public TipoCarteira getCarteira() {
		return carteira;
	}
}
