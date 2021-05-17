package br.com.fmchagas.proposta.biometria;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import br.com.fmchagas.proposta.cartao.Cartao;
import br.com.fmchagas.proposta.cartao.CartaoRepository;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = NovaBiometriaController.class)
class NovaBiometriaControllerTest {
	
	@MockBean
	private CartaoRepository cartaoRepository;
	@MockBean
	private BiometriaRepository biometriaRepository;

	
	@Autowired
	private MockMvc mockMvc;
	
	@Test
	void deveRetornar404_QuadoCartaoNaoExistir() {
		when(cartaoRepository.findById(1L)).thenReturn(Optional.empty());

		String json = "{\"fingerPrint\":\"dGVzdGU=\"}";

		try {
			mockMvc.perform(post("/api/cartoes/{id}/biometria", 1L)
					.content(json)
					.contentType("application/json"))
					.andExpect(status().isNotFound());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	void deveRetornar400_QuadoInputDeDadosForInvalido() {
		
		String json = "{\"fingerPrint\":\"dGVzd\"}";

		try {
			mockMvc.perform(post("/api/cartoes/{id}/biometria", 1L)
					.content(json)
					.contentType("application/json"))
					.andExpect(status().isBadRequest());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	void deveRetornar201_QuadoSalvarComSucesso() {
		Cartao cartao = new Cartao("7839-5881-6815-7317", LocalDateTime.now());
		Biometria biometria = new Biometria("dGVzdGU=",cartao);
				
		when(cartaoRepository.findById(1L)).thenReturn(Optional.of(cartao));
		when(biometriaRepository.save(biometria)).thenReturn(biometria);
		
		String json = "{\"fingerPrint\":\"dGVzdGU=\"}";

		try {
			mockMvc.perform(post("/api/cartoes/{id}/biometria", 1L)
					.content(json)
					.contentType("application/json"))
					.andExpect(status().isCreated());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
