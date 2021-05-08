package br.com.fmchagas.proposta.compartilhado.handler;

import java.util.Collection;

public class ErroPadronizadoResponse {
	private Collection<String> mensagens;
	
    public ErroPadronizadoResponse(Collection<String> mensagens) {
		this.mensagens = mensagens;
	}

	public Collection<String> getMensagens() {
		return mensagens;
	}
}