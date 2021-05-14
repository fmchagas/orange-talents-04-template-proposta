package br.com.fmchagas.proposta.cliente_externo.solicitacao;

import br.com.fmchagas.proposta.nova_proposta.Elegibilidade;

public class SolicitacaoResponse {
	private String documento;
	private String nome;
	private RestrincaoSolicitacao resultadoSolicitacao;
	private Integer idProposta;
	
	public SolicitacaoResponse(
			String documento,
			String nome,
			RestrincaoSolicitacao resultadoSolicitacao,
			Integer idProposta) {
		
		this.documento = documento;
		this.nome = nome;
		this.resultadoSolicitacao = resultadoSolicitacao;
		this.idProposta = idProposta;
	}
	
	public Elegibilidade paraElegibilidade() {
		return resultadoSolicitacao.getElegibilidade();
	}
}
