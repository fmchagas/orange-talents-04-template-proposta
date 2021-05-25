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
			mockMvc.perform(get("/api/propostas/1")
					.header("Authorization", "Bearer eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJRY2s1N0EzclhkX2JocFhlSERCSHZnQWtuelVvWUQzcVUySG9wei11cTFVIn0.eyJleHAiOjE2MjIwNjA0OTIsImlhdCI6MTYyMTk3NDA5MiwianRpIjoiNTNmYmFlNDItMDIzOC00YzU1LTgwZjgtZDBjZjM1MzhiNGIzIiwiaXNzIjoiaHR0cDovL2hvc3QuZG9ja2VyLmludGVybmFsOjE4MDgwL2F1dGgvcmVhbG1zL3Byb3Bvc3RhIiwiYXVkIjoiYWNjb3VudCIsInN1YiI6IjQzMWI4NzNkLTJkOTAtNGNlMC1iOTNkLTUwYTY5NmMxYmEwYiIsInR5cCI6IkJlYXJlciIsImF6cCI6InByb3Bvc3RhLW1pY3Jvc2VydmljZSIsInNlc3Npb25fc3RhdGUiOiJiYjNlY2JkOC0xNTc1LTQzYmItYTYzYi03OTUxODQ4ODgyZmQiLCJhY3IiOiIxIiwicmVhbG1fYWNjZXNzIjp7InJvbGVzIjpbIm9mZmxpbmVfYWNjZXNzIiwiZGVmYXVsdC1yb2xlcy1wcm9wb3N0YSIsInVtYV9hdXRob3JpemF0aW9uIl19LCJyZXNvdXJjZV9hY2Nlc3MiOnsiYWNjb3VudCI6eyJyb2xlcyI6WyJtYW5hZ2UtYWNjb3VudCIsIm1hbmFnZS1hY2NvdW50LWxpbmtzIiwidmlldy1wcm9maWxlIl19fSwic2NvcGUiOiJlbWFpbCBwcm9maWxlIHByb3Bvc3RhIiwiZW1haWxfdmVyaWZpZWQiOnRydWUsInByZWZlcnJlZF91c2VybmFtZSI6ImZlcm5hbmRvLmNoYWdhcyIsImVtYWlsIjoiZmVybWFjY2hhQGdtYWlsLmNvbSJ9.YXllrkmw4H04CGrOb_rA9V-79Jjvc-BBTsZTrUwPqk54C_QBtPq2vqBbZnAU-RRRGc46GKK4z9VUDhhRzXlTOHl63cvlg7a4iLS2FSVu_Um3241tvB4lfMSsChRKZ2JL5pnM5Nfzf69LeM1rJRm_q0joxb4SR97Ep1g6hXe3fTPdroBYidc9GRbf1ODpSY3cW5kZ24aW9EjOTgbWG98pTbcknm3hssmrZsLe5JcOxBensFc3-mmY-Iy4dISHgbm58aH-9mah-pP93mvmTfOUT1SgsBBk7GqN4z5qEQLR6jEiikBf-CXuqswa02k1d2oWKkqXKl3ml1riybNygh3Pbw")
					).andExpect(status().isNotFound());
			
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
			mockMvc.perform(get("/api/propostas/1")
					.header("Authorization", "Bearer eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJRY2s1N0EzclhkX2JocFhlSERCSHZnQWtuelVvWUQzcVUySG9wei11cTFVIn0.eyJleHAiOjE2MjIwNjA0OTIsImlhdCI6MTYyMTk3NDA5MiwianRpIjoiNTNmYmFlNDItMDIzOC00YzU1LTgwZjgtZDBjZjM1MzhiNGIzIiwiaXNzIjoiaHR0cDovL2hvc3QuZG9ja2VyLmludGVybmFsOjE4MDgwL2F1dGgvcmVhbG1zL3Byb3Bvc3RhIiwiYXVkIjoiYWNjb3VudCIsInN1YiI6IjQzMWI4NzNkLTJkOTAtNGNlMC1iOTNkLTUwYTY5NmMxYmEwYiIsInR5cCI6IkJlYXJlciIsImF6cCI6InByb3Bvc3RhLW1pY3Jvc2VydmljZSIsInNlc3Npb25fc3RhdGUiOiJiYjNlY2JkOC0xNTc1LTQzYmItYTYzYi03OTUxODQ4ODgyZmQiLCJhY3IiOiIxIiwicmVhbG1fYWNjZXNzIjp7InJvbGVzIjpbIm9mZmxpbmVfYWNjZXNzIiwiZGVmYXVsdC1yb2xlcy1wcm9wb3N0YSIsInVtYV9hdXRob3JpemF0aW9uIl19LCJyZXNvdXJjZV9hY2Nlc3MiOnsiYWNjb3VudCI6eyJyb2xlcyI6WyJtYW5hZ2UtYWNjb3VudCIsIm1hbmFnZS1hY2NvdW50LWxpbmtzIiwidmlldy1wcm9maWxlIl19fSwic2NvcGUiOiJlbWFpbCBwcm9maWxlIHByb3Bvc3RhIiwiZW1haWxfdmVyaWZpZWQiOnRydWUsInByZWZlcnJlZF91c2VybmFtZSI6ImZlcm5hbmRvLmNoYWdhcyIsImVtYWlsIjoiZmVybWFjY2hhQGdtYWlsLmNvbSJ9.YXllrkmw4H04CGrOb_rA9V-79Jjvc-BBTsZTrUwPqk54C_QBtPq2vqBbZnAU-RRRGc46GKK4z9VUDhhRzXlTOHl63cvlg7a4iLS2FSVu_Um3241tvB4lfMSsChRKZ2JL5pnM5Nfzf69LeM1rJRm_q0joxb4SR97Ep1g6hXe3fTPdroBYidc9GRbf1ODpSY3cW5kZ24aW9EjOTgbWG98pTbcknm3hssmrZsLe5JcOxBensFc3-mmY-Iy4dISHgbm58aH-9mah-pP93mvmTfOUT1SgsBBk7GqN4z5qEQLR6jEiikBf-CXuqswa02k1d2oWKkqXKl3ml1riybNygh3Pbw")
					).andExpect(status().isOk());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
