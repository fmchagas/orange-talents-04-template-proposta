package br.com.fmchagas.proposta.aviso_viagem;

import java.time.LocalDate;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.util.Assert;

import br.com.fmchagas.proposta.cartao.Cartao;

public class NovoAvisoViagemRequest {
	
	@NotBlank
	private String destino;
	
	@NotNull @Future
	private LocalDate validoAte;

	public NovoAvisoViagemRequest(@NotBlank String destino, 
			@NotNull @Future LocalDate validoAte) {
		
		this.destino = destino.trim();
		this.validoAte = validoAte;
	}

	public AvisoViagem toModel(@NotNull HttpServletRequest httpServletRequest, @NotNull Cartao cartao) {
		Assert.notNull(cartao, "Ops, o Cartao não deveria ser nulo neste ponto");
		Assert.notNull(httpServletRequest, "Ops, o HttpServletRequest não deveria ser nulo neste ponto");
		
		String ip = Optional.ofNullable(httpServletRequest.getHeader("X-FORWARDED-FOR"))
													.orElse(httpServletRequest.getRemoteAddr());
		
		//Loopback - 0:0:0:0:0:0:0:1 ou ::1 = 127.0.0.1
		if (ip.equals("0:0:0:0:0:0:0:1")) { ip = "127.0.0.1"; }
		
		String userAgent = httpServletRequest.getHeader("User-Agent");
		
		return new AvisoViagem(destino, validoAte, cartao, ip, userAgent);
	}
}
