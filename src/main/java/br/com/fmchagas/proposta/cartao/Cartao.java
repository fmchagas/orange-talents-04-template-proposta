package br.com.fmchagas.proposta.cartao;

import static javax.persistence.GenerationType.IDENTITY;

import java.time.LocalDateTime;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.util.Assert;

import br.com.fmchagas.proposta.carteira.Carteira;
import br.com.fmchagas.proposta.compartilhado.utils.CriptografiaParaBancoDeDados;

@Entity
public class Cartao {

	@Id
	@GeneratedValue(strategy = IDENTITY)
	private Long id;

	@NotBlank @Convert(converter = CriptografiaParaBancoDeDados.class)
	@Column(length = 64, nullable = false)
	private String numero;

	@NotNull
	@Column(nullable = false)
	private LocalDateTime emitidoEm;

	@Enumerated(EnumType.STRING)
	@Column(columnDefinition = "VARCHAR(9) default 'ATIVO'")
	private Situacao situacao = Situacao.ATIVO;

	@OneToMany(mappedBy = "cartao")
	private Set<Carteira> carteiras;

	/**
	 * @Deprecated - único para hibernate
	 */
	@Deprecated
	public Cartao() {
	}

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

	public boolean podeAssociarCarteira(Carteira carteira) {
		Assert.notNull(carteira, "A carteira não deveria estar nula nesse ponto");
		
		return !pertenceA(carteira);
	}

	private boolean pertenceA(Carteira carteira) {
		Assert.notNull(carteira, "A carteira não deveria estar nula nesse ponto");
		
		long countador = carteiras.stream().filter(c -> c.equals(carteira)).count();
		return countador >= 1;
	}
}
