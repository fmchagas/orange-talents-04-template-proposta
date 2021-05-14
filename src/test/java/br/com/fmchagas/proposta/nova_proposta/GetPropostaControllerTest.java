package br.com.fmchagas.proposta.nova_proposta;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {GetPropostaController.class})
class GetPropostaControllerTest {
	
	@Autowired
	private GetPropostaController getPropostaController;
	
	@MockBean
	private PropostaRepository propostaRepository;
	
	private MockMvc mockMvc;
	
	@BeforeEach
	public void setUp() {
		mockMvc = MockMvcBuilders.standaloneSetup(getPropostaController).build();
	}
	
	@Test
	void deveRetonar404_QuandoNaoEncontrarProposta() {
		when(propostaRepository.findById(1L)).thenReturn(Optional.empty());
		
		try {
			mockMvc.perform(get("/api/propostas/1"))
				.andExpect(status().isNotFound());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	void deveRetonar200_QuandoEncontrarProposta() {
		Proposta proposta = new Proposta("89005604093", "email@gmail.com", "Dunha", "Rua a", new BigDecimal("1000.0"));
		proposta.setElegibilidade(Elegibilidade.ELEGIVEL);
		
		when(propostaRepository.findById(1L)).thenReturn(Optional.of(proposta));
		
		try {
			mockMvc.perform(get("/api/propostas/1"))
			.andExpect(status().isOk());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
