package br.com.fmchagas.proposta.cliente_externo.solicitacao;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class SolicitacaoRequest {
	
	@NotBlank
	private String documento;
	@NotBlank
	private String nome;
	@NotNull
	private Long idProposta;
	
	public SolicitacaoRequest(@NotBlank String documento, String nome, Long idProposta) {
		this.documento = documento;
		this.nome = nome;
		this.idProposta = idProposta;
	}

	public String getDocumento() {
		return documento;
	}

	public String getNome() {
		return nome;
	}

	public Long getIdProposta() {
		return idProposta;
	}
}