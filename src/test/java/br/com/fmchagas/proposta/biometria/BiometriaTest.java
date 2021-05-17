package br.com.fmchagas.proposta.biometria;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import br.com.fmchagas.proposta.cartao.Cartao;


class BiometriaTest {

	@Test
	void deveLancarIllegalArgumentException_QuandoCriarBiometriaSemCartao() {
		Cartao cartao = null;
		
		assertThrows(IllegalArgumentException.class, () -> {
			new Biometria("dGVzdGU=", cartao);
		  });
	}
}
