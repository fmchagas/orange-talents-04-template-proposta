package br.com.fmchagas.proposta.cliente_externo.solicitacao;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "http-solicitacao", url = "${proposta.solicitacao.url}" )
public interface Solicitacao {
	
	@PostMapping("/api/solicitacao")
	SolicitacaoResponse consultaViaHttp(@RequestBody SolicitacaoRequest request);
}
