package br.com.fmchagas.proposta.cliente_externo.solicitacao;

import br.com.fmchagas.proposta.nova_proposta.Elegibilidade;

public class SolicitacaoResponse {
	private String documento;
	private String nome;
	private RestrincaoSolicitacao resultadoSolicitacao;
	private Integer idProposta;
	
	
	public String getDocumento() {
		return documento;
	}
	public String getNome() {
		return nome;
	}
	public Integer getIdProposta() {
		return idProposta;
	}
	public RestrincaoSolicitacao getResultadoSolicitacao() {
		return resultadoSolicitacao;
	}
	
	public Elegibilidade paraElegibilidade() {
		return resultadoSolicitacao.getElegibilidade();
	}
}
