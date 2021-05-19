package br.com.fmchagas.proposta.cartao.bloqueio;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.util.Assert;

import br.com.fmchagas.proposta.cartao.Cartao;

public class NovoBloqueioRequest {

	public Bloqueio toModel(HttpServletRequest httpServletRequest, Cartao cartao) {
		Assert.notNull(cartao, "Ops, o Cartao não deveria ser nulo neste ponto");
		Assert.notNull(httpServletRequest, "Ops, o HttpServletRequest não deveria ser nulo neste ponto");
		
		String ip = Optional.ofNullable(httpServletRequest.getHeader("X-FORWARDED-FOR"))
													.orElse(httpServletRequest.getRemoteAddr());
		
		//Loopback - 0:0:0:0:0:0:0:1 ou ::1 = 127.0.0.1
		if (ip.equals("0:0:0:0:0:0:0:1")) { ip = "127.0.0.1"; }
		
		String userAgent = httpServletRequest.getHeader("User-Agent");
		
		return new Bloqueio(ip, userAgent, cartao);
	}
}
