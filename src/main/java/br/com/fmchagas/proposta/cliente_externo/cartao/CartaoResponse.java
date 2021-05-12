package br.com.fmchagas.proposta.cliente_externo.cartao;

import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import br.com.fmchagas.proposta.cartao.Cartao;

public class CartaoResponse {
	
	/*
	 * id é número do cartão retornando pela API
	 */
	private @NotBlank String id;
	
	private @NotNull LocalDateTime emitidoEm;

	public CartaoResponse(@NotBlank String id, @NotNull LocalDateTime emitidoEm) {
		this.id = id;
		this.emitidoEm = emitidoEm;
	}
	
	public Cartao paraModelo() {
		return new Cartao(id, emitidoEm);
	}
}
