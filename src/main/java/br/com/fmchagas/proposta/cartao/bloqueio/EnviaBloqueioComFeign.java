package br.com.fmchagas.proposta.cartao.bloqueio;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import br.com.fmchagas.proposta.cartao.Cartao;
import br.com.fmchagas.proposta.cliente_externo.cartao.CartaoCliente;
import br.com.fmchagas.proposta.cliente_externo.cartao.CartaoClienteNovoBloqueioRequest;
import feign.FeignException;

@Component //total pts 5
public class EnviaBloqueioComFeign {
	
	private final Logger logger = LoggerFactory.getLogger(EnviaBloqueioComFeign.class);
	private CartaoCliente cartaoCliente;
	
	@Autowired
	public EnviaBloqueioComFeign(CartaoCliente cartaoCliente) {
		this.cartaoCliente = cartaoCliente;
	}
	
	public void enviar(Cartao cartao){
		logger.info("Iniciando envio do bloqueio");
		
		try {
			cartaoCliente.bloquearViaHttp(cartao.getNumero(), new CartaoClienteNovoBloqueioRequest());
			
			logger.info("Bloquei gerado com sucesso");
			
		}catch (FeignException ex) {
			logger.info("Possivelmente o cartão já está bloqueado");
			
			throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Não conseguimos processar o bloqueio do cartão");
		}
	}
}
