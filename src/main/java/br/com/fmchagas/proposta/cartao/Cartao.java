package br.com.fmchagas.proposta.cartao;

import static javax.persistence.GenerationType.IDENTITY;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
public class Cartao {
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	private Long id;
	
	@NotBlank @Column(length = 19, nullable = false)
	private String numero;
	
	@NotNull @Column(nullable = false)
	private LocalDateTime emitidoEm;
	
	/**
	 * @Deprecated - único para hibernate
	 */
	@Deprecated
	public Cartao() {}

	public Cartao(@NotBlank String numero, @NotNull LocalDateTime emitidoEm) {
		this.numero = numero;
		this.emitidoEm = emitidoEm;
	}
	
	public String getNumero() {
		return numero;
	}
}
