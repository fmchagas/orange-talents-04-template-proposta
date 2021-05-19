package br.com.fmchagas.proposta.cartao.bloqueio;

import static javax.persistence.GenerationType.IDENTITY;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.util.Assert;

import br.com.fmchagas.proposta.cartao.Cartao;

@Entity
public class Bloqueio {
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	private Long id;
	
	@NotNull @CreationTimestamp
	@Column(nullable = false)
	private LocalDateTime bloqueadoEm = LocalDateTime.now();
	
	@Column(nullable = false)
	@NotBlank
	private String ipCliente;
	
	@Column(nullable = false)
	@NotBlank
	private String userAgenteCliente;
	
	@ManyToOne(optional = false)
	private @NotNull Cartao cartao;
	
	/**
	 * @Deprecated - único para hibernate
	 */
	@Deprecated
	public Bloqueio() {}

	public Bloqueio(@NotBlank String ipCliente,
			@NotBlank String userAgenteCliente, 
			@NotNull Cartao cartao) {
		Assert.notNull(cartao, "Ops, não podemos fazer uma atribuição de cartão quando estiver nula");
		
		this.ipCliente = ipCliente;
		this.userAgenteCliente = userAgenteCliente;
		this.cartao = cartao;
	}
	
}
