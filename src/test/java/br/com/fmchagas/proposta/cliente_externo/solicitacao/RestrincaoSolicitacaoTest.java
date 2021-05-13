package br.com.fmchagas.proposta.cliente_externo.solicitacao;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import br.com.fmchagas.proposta.nova_proposta.Elegibilidade;

class RestrincaoSolicitacaoTest {

	@Test
	void naoElegivel_QuandoRestrincaoSolicitacaoForComRestrincao() {
		RestrincaoSolicitacao comRestricao = RestrincaoSolicitacao.COM_RESTRICAO;
		
		assertEquals(Elegibilidade.NAO_ELEGIVEL, comRestricao.getElegibilidade());
	}
	
	@Test
	void elegivel_QuandoRestrincaoSolicitacaoForSemRestrincao() {
		RestrincaoSolicitacao semRestricao = RestrincaoSolicitacao.SEM_RESTRICAO;
		
		assertEquals(Elegibilidade.ELEGIVEL, semRestricao.getElegibilidade());
	}

}
