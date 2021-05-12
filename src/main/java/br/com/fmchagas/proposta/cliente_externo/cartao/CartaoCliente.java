package br.com.fmchagas.proposta.cliente_externo.cartao;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "http-cartoes", url = "${proposta.cartoes.url}" )
public interface CartaoCliente {
	
	@GetMapping
	CartaoResponse consultaViaHttp(@RequestParam("idProposta") Long idProposta);
}
