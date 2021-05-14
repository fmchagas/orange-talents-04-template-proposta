package br.com.fmchagas.proposta.cliente_externo.solicitacao;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "http-solicitacao", url = "${proposta.solicitacao.url}" )
public interface SolicitacaoCliente {
	
	@PostMapping("/api/solicitacao")
	SolicitacaoClienteResponse consultaViaHttp(@RequestBody SolicitacaoClienteRequest request);
}
