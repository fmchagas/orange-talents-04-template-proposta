package br.com.fmchagas.proposta.aviso_viagem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import br.com.fmchagas.proposta.cartao.Cartao;
import br.com.fmchagas.proposta.cliente_externo.cartao.CartaoCliente;
import br.com.fmchagas.proposta.cliente_externo.cartao.CartaoClienteNovoAvisoViagemRequest;
import feign.FeignException;

@Component //total pts 6
public class EnviaAvisoViagemComFeign {
	
	private CartaoCliente cartaoCliene;
	
	@Autowired
	public EnviaAvisoViagemComFeign(CartaoCliente cartaoCliene) {
		this.cartaoCliene = cartaoCliene;
	}

	public void enviar(NovoAvisoViagemRequest request, Cartao cartao) {
		try {
			cartaoCliene.avisoViagemViaHttp(cartao.getNumero(), new CartaoClienteNovoAvisoViagemRequest(request.getDestino(), request.getValidoAte()));
			
		}catch(FeignException ex) {
			throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Desculpe o transtorno, não conseguimos processar o aviso de viagem");
		}
	}
}
