package br.com.fmchagas.proposta.nova_proposta;

import java.math.BigDecimal;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

import com.sun.istack.NotNull;

import br.com.fmchagas.proposta.compartilhado.validacao.CpfOuCnpj;

public class NovaPropostaRequest {
	
	@NotBlank @CpfOuCnpj
	private String documento;
	
	@NotBlank @Email
	private String email;
	
	@NotBlank
	private String nome;
	
	@NotBlank
	private String endereco;
	
	@NotNull @Positive
	private BigDecimal salario;

	public NovaPropostaRequest(@NotBlank String documento, @NotBlank @Email String email, @NotBlank String nome,
			@NotBlank String endereco,@NotNull @Positive BigDecimal salario) {
		this.documento = limpar(documento);
		this.email = email;
		this.nome = nome.trim();
		this.endereco = endereco.trim();
		this.salario = salario;
	}
	
	public Proposta converteParaModelo() {
		return new Proposta(documento, email, nome, endereco, salario);
	}
	
	
	private String limpar(String valor) {
		return valor.replaceAll("[^0-9]+", "");
	}

	public String getDocumento() {
		return documento;
	}
}
