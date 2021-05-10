package br.com.fmchagas.proposta.cliente_externo.solicitacao;

import br.com.fmchagas.proposta.nova_proposta.Elegibilidade;

public enum RestrincaoSolicitacao {
	SEM_RESTRICAO(Elegibilidade.ELEGIVEL),
	COM_RESTRICAO(Elegibilidade.NAO_ELEGIVEL);
	
	private Elegibilidade elegibilidade;
	
	RestrincaoSolicitacao(Elegibilidade elegibilidade) {
		this.elegibilidade = elegibilidade;
	}

	public Elegibilidade getElegibilidade() {
		return elegibilidade;
	}
}
