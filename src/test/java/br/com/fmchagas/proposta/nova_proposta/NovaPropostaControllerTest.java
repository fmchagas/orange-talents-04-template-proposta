package br.com.fmchagas.proposta.nova_proposta;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import br.com.fmchagas.proposta.cliente_externo.solicitacao.SolicitacaoClienteResponse;
import br.com.fmchagas.proposta.metricas.ContadorDeProposta;
import io.opentracing.Tracer;;

@ExtendWith(SpringExtension.class) // dizer ao JUnit 5 para ativar o suporte ao Spring
@WebMvcTest(controllers = NovaPropostaController.class) // restringir o contexto do app criado para unico bean debcontrolador
class NovaPropostaControllerTest {

	/*
	 * @MockBean - substitui automaticamente o bean do mesmo tipo no contexto do app
	 * por um mock Mockito
	 */
	@MockBean
	private PropostaRepository propostaRepository;
	@MockBean
	private ElegibilidadeNovaProposta elegibilidade;
	@MockBean
	private ContadorDeProposta metrica;
	@MockBean
	private Tracer tracer;

	@Autowired
	private MockMvc mockMvc;

	/*
	 * O Spring Boot fornece automaticamente beans como um @ObjectMapper para mapear
	 * JSON e uma MockMvc instância para simular solicitações HTTP.
	 */
	//@Autowired
	//private ObjectMapper objectMapper;


	@Test
	void deveRetornar422_QuadoDocumentoDaPropostaExistir() {
		when(propostaRepository.existsByDocumento("60178303000120")).thenReturn(true);

		String json = "{\"documento\":\"60178303000120\",\"email\":\"email@gmail.com\",\"nome\":\"Dunha\",\"endereco\":\"R a\",\"salario\":\"25000\"}";

		try {
			mockMvc.perform(post("/api/propostas")
					.content(json)
					.header("Authorization", "Bearer eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJRY2s1N0EzclhkX2JocFhlSERCSHZnQWtuelVvWUQzcVUySG9wei11cTFVIn0.eyJleHAiOjE2MjIwNjA0OTIsImlhdCI6MTYyMTk3NDA5MiwianRpIjoiNTNmYmFlNDItMDIzOC00YzU1LTgwZjgtZDBjZjM1MzhiNGIzIiwiaXNzIjoiaHR0cDovL2hvc3QuZG9ja2VyLmludGVybmFsOjE4MDgwL2F1dGgvcmVhbG1zL3Byb3Bvc3RhIiwiYXVkIjoiYWNjb3VudCIsInN1YiI6IjQzMWI4NzNkLTJkOTAtNGNlMC1iOTNkLTUwYTY5NmMxYmEwYiIsInR5cCI6IkJlYXJlciIsImF6cCI6InByb3Bvc3RhLW1pY3Jvc2VydmljZSIsInNlc3Npb25fc3RhdGUiOiJiYjNlY2JkOC0xNTc1LTQzYmItYTYzYi03OTUxODQ4ODgyZmQiLCJhY3IiOiIxIiwicmVhbG1fYWNjZXNzIjp7InJvbGVzIjpbIm9mZmxpbmVfYWNjZXNzIiwiZGVmYXVsdC1yb2xlcy1wcm9wb3N0YSIsInVtYV9hdXRob3JpemF0aW9uIl19LCJyZXNvdXJjZV9hY2Nlc3MiOnsiYWNjb3VudCI6eyJyb2xlcyI6WyJtYW5hZ2UtYWNjb3VudCIsIm1hbmFnZS1hY2NvdW50LWxpbmtzIiwidmlldy1wcm9maWxlIl19fSwic2NvcGUiOiJlbWFpbCBwcm9maWxlIHByb3Bvc3RhIiwiZW1haWxfdmVyaWZpZWQiOnRydWUsInByZWZlcnJlZF91c2VybmFtZSI6ImZlcm5hbmRvLmNoYWdhcyIsImVtYWlsIjoiZmVybWFjY2hhQGdtYWlsLmNvbSJ9.YXllrkmw4H04CGrOb_rA9V-79Jjvc-BBTsZTrUwPqk54C_QBtPq2vqBbZnAU-RRRGc46GKK4z9VUDhhRzXlTOHl63cvlg7a4iLS2FSVu_Um3241tvB4lfMSsChRKZ2JL5pnM5Nfzf69LeM1rJRm_q0joxb4SR97Ep1g6hXe3fTPdroBYidc9GRbf1ODpSY3cW5kZ24aW9EjOTgbWG98pTbcknm3hssmrZsLe5JcOxBensFc3-mmY-Iy4dISHgbm58aH-9mah-pP93mvmTfOUT1SgsBBk7GqN4z5qEQLR6jEiikBf-CXuqswa02k1d2oWKkqXKl3ml1riybNygh3Pbw")
					.contentType("application/json"))
					.andExpect(status().isUnprocessableEntity());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	void deveRetornar400_QuadoCamposDeEntradaForemInvalidos() {
		String json = "{\"documento\":\"123\",\"email\":\"email@gmail\",\"nome\":\"   \",\"endereco\":\"R a\",\"salario\":\"null\"}";

		try {
			mockMvc.perform(post("/api/propostas")
					.content(json)
					.header("Authorization", "Bearer eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJRY2s1N0EzclhkX2JocFhlSERCSHZnQWtuelVvWUQzcVUySG9wei11cTFVIn0.eyJleHAiOjE2MjIwNjA0OTIsImlhdCI6MTYyMTk3NDA5MiwianRpIjoiNTNmYmFlNDItMDIzOC00YzU1LTgwZjgtZDBjZjM1MzhiNGIzIiwiaXNzIjoiaHR0cDovL2hvc3QuZG9ja2VyLmludGVybmFsOjE4MDgwL2F1dGgvcmVhbG1zL3Byb3Bvc3RhIiwiYXVkIjoiYWNjb3VudCIsInN1YiI6IjQzMWI4NzNkLTJkOTAtNGNlMC1iOTNkLTUwYTY5NmMxYmEwYiIsInR5cCI6IkJlYXJlciIsImF6cCI6InByb3Bvc3RhLW1pY3Jvc2VydmljZSIsInNlc3Npb25fc3RhdGUiOiJiYjNlY2JkOC0xNTc1LTQzYmItYTYzYi03OTUxODQ4ODgyZmQiLCJhY3IiOiIxIiwicmVhbG1fYWNjZXNzIjp7InJvbGVzIjpbIm9mZmxpbmVfYWNjZXNzIiwiZGVmYXVsdC1yb2xlcy1wcm9wb3N0YSIsInVtYV9hdXRob3JpemF0aW9uIl19LCJyZXNvdXJjZV9hY2Nlc3MiOnsiYWNjb3VudCI6eyJyb2xlcyI6WyJtYW5hZ2UtYWNjb3VudCIsIm1hbmFnZS1hY2NvdW50LWxpbmtzIiwidmlldy1wcm9maWxlIl19fSwic2NvcGUiOiJlbWFpbCBwcm9maWxlIHByb3Bvc3RhIiwiZW1haWxfdmVyaWZpZWQiOnRydWUsInByZWZlcnJlZF91c2VybmFtZSI6ImZlcm5hbmRvLmNoYWdhcyIsImVtYWlsIjoiZmVybWFjY2hhQGdtYWlsLmNvbSJ9.YXllrkmw4H04CGrOb_rA9V-79Jjvc-BBTsZTrUwPqk54C_QBtPq2vqBbZnAU-RRRGc46GKK4z9VUDhhRzXlTOHl63cvlg7a4iLS2FSVu_Um3241tvB4lfMSsChRKZ2JL5pnM5Nfzf69LeM1rJRm_q0joxb4SR97Ep1g6hXe3fTPdroBYidc9GRbf1ODpSY3cW5kZ24aW9EjOTgbWG98pTbcknm3hssmrZsLe5JcOxBensFc3-mmY-Iy4dISHgbm58aH-9mah-pP93mvmTfOUT1SgsBBk7GqN4z5qEQLR6jEiikBf-CXuqswa02k1d2oWKkqXKl3ml1riybNygh3Pbw")
					.contentType("application/json"))
					.andExpect(status().isBadRequest());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * Ainda não consigo testar quando temos um método privado do controller fazendo chamada no feign
	 * e pegando resutando do response para attribuir da null pointer exception
	 */
	
	@Test
	void deveRetornar201_QuadoPropostaForValida(){		
		Proposta proposta = new Proposta("60178303000120", "email@gmail.com", "Dunha", "Rua a", BigDecimal.TEN);
		
		when(propostaRepository.existsByDocumento("60178303000120")).thenReturn(false);
		
		doNothing().when(elegibilidade).atribuirPara(proposta);
		
		when(propostaRepository.save(proposta)).thenReturn(proposta);

		String json = "{\"documento\":\"60178303000120\",\"email\":\"email@gmail.com\",\"nome\":\"Dunha\",\"endereco\":\"R a\",\"salario\":\"25000\"}";

		try {
			mockMvc.perform(post("/api/propostas")
					.content(json)
					.header("Authorization", "Bearer eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJRY2s1N0EzclhkX2JocFhlSERCSHZnQWtuelVvWUQzcVUySG9wei11cTFVIn0.eyJleHAiOjE2MjIwNjA0OTIsImlhdCI6MTYyMTk3NDA5MiwianRpIjoiNTNmYmFlNDItMDIzOC00YzU1LTgwZjgtZDBjZjM1MzhiNGIzIiwiaXNzIjoiaHR0cDovL2hvc3QuZG9ja2VyLmludGVybmFsOjE4MDgwL2F1dGgvcmVhbG1zL3Byb3Bvc3RhIiwiYXVkIjoiYWNjb3VudCIsInN1YiI6IjQzMWI4NzNkLTJkOTAtNGNlMC1iOTNkLTUwYTY5NmMxYmEwYiIsInR5cCI6IkJlYXJlciIsImF6cCI6InByb3Bvc3RhLW1pY3Jvc2VydmljZSIsInNlc3Npb25fc3RhdGUiOiJiYjNlY2JkOC0xNTc1LTQzYmItYTYzYi03OTUxODQ4ODgyZmQiLCJhY3IiOiIxIiwicmVhbG1fYWNjZXNzIjp7InJvbGVzIjpbIm9mZmxpbmVfYWNjZXNzIiwiZGVmYXVsdC1yb2xlcy1wcm9wb3N0YSIsInVtYV9hdXRob3JpemF0aW9uIl19LCJyZXNvdXJjZV9hY2Nlc3MiOnsiYWNjb3VudCI6eyJyb2xlcyI6WyJtYW5hZ2UtYWNjb3VudCIsIm1hbmFnZS1hY2NvdW50LWxpbmtzIiwidmlldy1wcm9maWxlIl19fSwic2NvcGUiOiJlbWFpbCBwcm9maWxlIHByb3Bvc3RhIiwiZW1haWxfdmVyaWZpZWQiOnRydWUsInByZWZlcnJlZF91c2VybmFtZSI6ImZlcm5hbmRvLmNoYWdhcyIsImVtYWlsIjoiZmVybWFjY2hhQGdtYWlsLmNvbSJ9.YXllrkmw4H04CGrOb_rA9V-79Jjvc-BBTsZTrUwPqk54C_QBtPq2vqBbZnAU-RRRGc46GKK4z9VUDhhRzXlTOHl63cvlg7a4iLS2FSVu_Um3241tvB4lfMSsChRKZ2JL5pnM5Nfzf69LeM1rJRm_q0joxb4SR97Ep1g6hXe3fTPdroBYidc9GRbf1ODpSY3cW5kZ24aW9EjOTgbWG98pTbcknm3hssmrZsLe5JcOxBensFc3-mmY-Iy4dISHgbm58aH-9mah-pP93mvmTfOUT1SgsBBk7GqN4z5qEQLR6jEiikBf-CXuqswa02k1d2oWKkqXKl3ml1riybNygh3Pbw")
					.contentType("application/json"))
					.andExpect(status().isCreated());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
