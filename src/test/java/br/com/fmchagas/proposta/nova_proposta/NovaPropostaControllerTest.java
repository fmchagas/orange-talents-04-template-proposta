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

import br.com.fmchagas.proposta.cliente_externo.solicitacao.SolicitacaoClienteResponse;;

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
	SolicitacaoClienteResponse solicitacaoResponse;

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
					.contentType("application/json"))
					.andExpect(status().isCreated());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
