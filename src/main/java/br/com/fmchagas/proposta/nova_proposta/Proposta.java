package br.com.fmchagas.proposta.nova_proposta;

import static javax.persistence.GenerationType.IDENTITY;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
	
	@Column(length = 14, unique = true, nullable = false)
	private @NotBlank String documento;
	
	@Column(nullable = false)
	private @NotBlank @Email String email;
	
	@Column(nullable = false)
	private @NotBlank String nome;
	
	@Column(nullable = false)
	private @NotBlank String endereco;
	
	@Column(nullable = false)
	private @NotNull @Positive BigDecimal salario;
	
	@Enumerated(EnumType.STRING)
	@Column(length = 12)
	private Elegibilidade elegibilidade ;
	
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
	
	public String getDocumento() {
		return documento;
	}
	
	public String getNome() {
		return nome;
	}
	
	public void setElegibilidade(Elegibilidade elegibilidade) {
		this.elegibilidade = elegibilidade;
	}
}
