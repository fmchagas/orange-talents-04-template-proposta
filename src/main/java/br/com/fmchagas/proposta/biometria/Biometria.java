package br.com.fmchagas.proposta.biometria;

import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.util.Assert;

import br.com.fmchagas.proposta.cartao.Cartao;

@Entity
public class Biometria {
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	private Long id;
	
	
	@Lob @Column(nullable = false)
	private @NotBlank String fingerPrint;
	
	@NotNull @CreationTimestamp
	@Column(nullable = false)
	private LocalDateTime criadoEm = LocalDateTime.now();
	
	@ManyToOne(optional = false)
	private @NotNull Cartao cartao;

	public Biometria(@NotBlank String fingerPrint, @NotNull Cartao cartao) {
		Assert.notNull(cartao, "Ops, não podemos fazer uma atribuição de cartão quando estiver nula");
		
		this.fingerPrint = fingerPrint;
		this.cartao = cartao;
	}
	
	public Long getId() {
		return id;
	}
}
