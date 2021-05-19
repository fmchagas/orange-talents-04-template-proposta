package br.com.fmchagas.proposta.cartao.bloqueio;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.fmchagas.proposta.cartao.Cartao;
import br.com.fmchagas.proposta.cliente_externo.cartao.CartaoCliente;
import br.com.fmchagas.proposta.cliente_externo.cartao.CartaoClienteNovoBloqueioRequest;
import feign.FeignException;

public class EnviaBloqueioComFeign {
	
	private final Logger logger = LoggerFactory.getLogger(EnviaBloqueioComFeign.class);
	
	private Cartao cartao;
	private CartaoCliente cartaoCliente;
	
	public EnviaBloqueioComFeign(Cartao cartao, CartaoCliente cartaoCliente) {
		this.cartao = cartao;
		this.cartaoCliente = cartaoCliente;
	}
	
	public boolean enviarBloqueiro(){
		logger.info("Iniciando envio do bloqueio");
		
		try {
			cartaoCliente.bloquearViaHttp(cartao.getNumero(), new CartaoClienteNovoBloqueioRequest());
			
			logger.info("Bloquei gerado com sucesso");
			
			return true;
			
		}catch (FeignException.UnprocessableEntity ex) {
			logger.info("Possivelmente o cartão já está bloqueado - " + ex.getLocalizedMessage());
			
			return false;
		}
	}
}
