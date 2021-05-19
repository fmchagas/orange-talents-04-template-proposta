package br.com.fmchagas.proposta.cliente_externo.cartao;

import javax.validation.Valid;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "http-cartoes", url = "${proposta.cartoes.url}" )
public interface CartaoCliente {
	
	@GetMapping
	CartaoClienteResponse consultaViaHttp(@RequestParam("idProposta") Long idProposta);
	
	@PostMapping("/{id}/bloqueios")
	void bloquearViaHttp(@PathVariable String id, @RequestBody @Valid CartaoClienteNovoBloqueioRequest request);
}
