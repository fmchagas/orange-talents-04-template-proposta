package br.com.fmchagas.proposta.nova_proposta;

import static javax.persistence.GenerationType.IDENTITY;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

import org.springframework.util.Assert;

import com.sun.istack.NotNull;

import br.com.fmchagas.proposta.cartao.Cartao;


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
	
	@OneToOne
	private Cartao cartao;
	
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
	
	public void setElegibilidade(Elegibilidade elegibilidade) {
		this.elegibilidade = elegibilidade;
	}
	
	public void associaCartao(Cartao cartao) {
		Assert.notNull(cartao, "Ops, não podemos fazer uma atribuição de cartão quando estiver nula");
		
		this.cartao = cartao;
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
}
