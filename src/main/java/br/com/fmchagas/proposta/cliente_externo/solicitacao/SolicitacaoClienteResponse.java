package br.com.fmchagas.proposta.cliente_externo.solicitacao;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import br.com.fmchagas.proposta.nova_proposta.Elegibilidade;

public class SolicitacaoClienteResponse {
	
	private @NotBlank String documento;
	private @NotBlank String nome;
	private @NotNull RestrincaoSolicitacao resultadoSolicitacao;
	private @NotNull Integer idProposta;
	
	
	public Elegibilidade paraElegibilidade() {
		return resultadoSolicitacao.getElegibilidade();
	}
	
	/**
	 * Default getter e construtor para Jackson conseguir fazer descerialização
	 * quando recebe a resposta externa da API diferente de 200
	 * atualmente não sei uma forma diferente de fazer quando utilizamos o Feign cliente
	 */
	public SolicitacaoClienteResponse() {}
	
	public String getDocumento() {
		return documento;
	}

	public String getNome() {
		return nome;
	}

	public RestrincaoSolicitacao getResultadoSolicitacao() {
		return resultadoSolicitacao;
	}

	public Integer getIdProposta() {
		return idProposta;
	}
}
