package br.com.fmchagas.proposta.cliente_externo.cartao;

import java.time.LocalDate;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class CartaoClienteNovoAvisoViagemRequest {
	@NotBlank
	private String destino;
	
	@NotNull @Future
	private LocalDate validoAte;

	public CartaoClienteNovoAvisoViagemRequest(@NotBlank String destino, @NotNull @Future LocalDate validoAte) {
		this.destino = destino;
		this.validoAte = validoAte;
	}
	
	public String getDestino() {
		return destino;
	}
	
	public LocalDate getValidoAte() {
		return validoAte;
	}
}
