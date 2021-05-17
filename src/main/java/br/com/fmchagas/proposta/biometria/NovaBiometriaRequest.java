package br.com.fmchagas.proposta.biometria;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.util.Assert;

import com.fasterxml.jackson.annotation.JsonProperty;

import br.com.fmchagas.proposta.cartao.Cartao;

public class NovaBiometriaRequest {
	
	@NotBlank
	private String fingerPrint;

	public NovaBiometriaRequest(@JsonProperty("fingerPrint") @NotBlank String fingerPrint) {
		this.fingerPrint = fingerPrint;
	}

	public Biometria toModelo(@NotNull Cartao cartao) {
		Assert.notNull(cartao, "Ops, o cartão deve existir nesse ponto para podermos criar uma biometria valida");
		
		return new Biometria(fingerPrint, cartao);
	}
	
	public String getFingerPrint() {
		return fingerPrint;
	}
}
