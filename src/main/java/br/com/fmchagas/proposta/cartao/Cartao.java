package br.com.fmchagas.proposta.cartao;

import static javax.persistence.GenerationType.IDENTITY;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
	
	@Enumerated(EnumType.STRING)
	@Column(columnDefinition = "VARCHAR(9) default 'ATIVO'")
	private Situacao situacao = Situacao.ATIVO;
	
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

	public boolean podeBloquear() {
		return isAtivo();
	}
	
	public void bloqueia() {
		situacao = Situacao.BLOQUEADO;
	}
	
	private boolean isAtivo() {
		return situacao.equals(Situacao.ATIVO);
	}
}
