package br.com.fmchagas.proposta.cliente_externo.solicitacao;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import br.com.fmchagas.proposta.nova_proposta.Elegibilidade;

public class SolicitacaoClienteResponse {
	private @NotBlank String documento;
	private @NotBlank String nome;
	private @NotNull RestrincaoSolicitacao resultadoSolicitacao;
	private @NotNull Integer idProposta;
	
	public SolicitacaoClienteResponse(
			@NotBlank String documento,
			@NotBlank String nome,
			@NotNull RestrincaoSolicitacao resultadoSolicitacao,
			@NotNull Integer idProposta) {
		
		this.documento = documento;
		this.nome = nome;
		this.resultadoSolicitacao = resultadoSolicitacao;
		this.idProposta = idProposta;
	}
	
	public Elegibilidade paraElegibilidade() {
		return resultadoSolicitacao.getElegibilidade();
	}
}
