package br.com.fmchagas.proposta.aviso_viagem;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.CreationTimestamp;

import br.com.fmchagas.proposta.cartao.Cartao;

@Entity
public class AvisoViagem {
	
	@Id
	@GeneratedValue(generator = "UUID")
	@Column(columnDefinition = "BINARY(16)")
	private UUID id;

	@Column(length = 128, nullable = false)
	private @NotBlank String destino;
	private @NotNull @Future LocalDate validoAte;

	@NotNull
	@CreationTimestamp
	@Column(nullable = false)
	private LocalDateTime avisoViagemEm = LocalDateTime.now();

	@ManyToOne(optional = false)
	private @NotNull Cartao cartao;

	@Column(length = 64)
	private String ip;

	@Column(length = 128)
	private String userAgent;

	/**
	 * @Deprecated - único para hibernate
	 */
	@Deprecated
	public AvisoViagem() {
	}

	public AvisoViagem(@NotBlank String destino, @NotNull @Future LocalDate validoAte, @NotNull Cartao cartao,
			String ip, String userAgent) {

		this.destino = destino;
		this.validoAte = validoAte;
		this.cartao = cartao;
		this.ip = ip;
		this.userAgent = userAgent;
	}
}
