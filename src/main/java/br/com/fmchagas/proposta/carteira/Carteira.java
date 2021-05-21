package br.com.fmchagas.proposta.carteira;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import br.com.fmchagas.proposta.cartao.Cartao;

@Entity
@Table(indexes = { @Index(name = "idx_carteira_unica", columnList = "cartao_id, carteira", unique = true) })
public class Carteira {
	
	@Id
	@GeneratedValue(generator = "UUID")
	@Column(columnDefinition = "BINARY(16)")
	private UUID id;
	
	@Column(length = 128, nullable = false)
	private @NotBlank @Email String email;
	
	@Enumerated(EnumType.STRING)
	@Column(length = 24, nullable = false)
	private @NotNull TipoCarteira carteira;
	
	@ManyToOne(optional = false)
	private @NotNull Cartao cartao;
	
	/**
	 * @Deprecated - único para hibernate
	 */
	@Deprecated
	public Carteira() {}
	
	public Carteira(@NotBlank @Email String email, @NotNull TipoCarteira carteira, @NotNull Cartao cartao) {
		this.email = email;
		this.carteira = carteira;
		this.cartao = cartao;
	}
	
	public UUID getId() {
		return id;
	}

	@Override
	public String toString() {
		return "Carteira -> id=" + id + ", email=" + email + ", carteira=" + carteira;
	}
	
	public TipoCarteira getCarteira() {
		return carteira;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((carteira == null) ? 0 : carteira.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Carteira other = (Carteira) obj;
		if (carteira != other.carteira)
			return false;
		return true;
	}
}
