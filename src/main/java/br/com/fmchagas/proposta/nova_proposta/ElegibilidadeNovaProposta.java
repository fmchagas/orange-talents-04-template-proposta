package br.com.fmchagas.proposta.nova_proposta;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.fmchagas.proposta.cliente_externo.solicitacao.SolicitacaoCliente;
import br.com.fmchagas.proposta.cliente_externo.solicitacao.SolicitacaoClienteRequest;
import br.com.fmchagas.proposta.cliente_externo.solicitacao.SolicitacaoClienteResponse;
import feign.FeignException;

@Component //total 8 pts
public class ElegibilidadeNovaProposta {
	
	private SolicitacaoCliente analiseCliente;
	
	@Autowired
	public ElegibilidadeNovaProposta(SolicitacaoCliente analiseCliente) {
		this.analiseCliente = analiseCliente;
	}
	
	
	public void atribuirPara(Proposta proposta) {
		SolicitacaoClienteResponse resultadoSolicitacao;
		
		try {
			resultadoSolicitacao = analiseCliente.consultaViaHttp(
						new SolicitacaoClienteRequest(proposta.getDocumento(),
								proposta.getNome(),
								proposta.getId()));
			
			proposta.setElegibilidade(resultadoSolicitacao.paraElegibilidade());

		}catch (FeignException.UnprocessableEntity e) {
			try {
				/*
				 * Converte Json para Objeto, precisa do construtor padrão e getters
				 */
				resultadoSolicitacao = new ObjectMapper().readValue(e.contentUTF8(), SolicitacaoClienteResponse.class);
				
				proposta.setElegibilidade(resultadoSolicitacao.paraElegibilidade());
				
			} catch (JsonProcessingException ex) {
				StringBuilder sb = new StringBuilder();
				sb.append("Algum problema aconteceu quando tentavamos converte o Json para Objeto!");
				sb.append(" Mensagem da exception: ");
				sb.append(ex.getMessage());
				
				Assert.state(ex==null, sb.toString());
			}
		}
	}
}
