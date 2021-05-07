package br.com.fmchagas.proposta.nova_proposta;

import static javax.persistence.GenerationType.IDENTITY;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

import com.sun.istack.NotNull;


@Entity
public class Proposta {
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	private Long id;
	
	private @NotBlank String documento;
	private @NotBlank @Email String email;
	private @NotBlank String nome;
	private @NotBlank String endereco;
	private @NotNull @Positive BigDecimal salario;
	
	/**
	 * @Deprecated - único para hibernate
	 */
	@Deprecated
	public Proposta() {}

	public Proposta(@NotBlank String documento, @NotBlank @Email String email,
			@NotBlank String nome, @NotBlank String endereco, @NotNull @Positive BigDecimal salario) {
				
		this.documento = documento;
		this.email = email;
		this.nome = nome;
		this.endereco = endereco;
		this.salario = salario;
	}

	public Long getId() {
		return id;
	}
}
