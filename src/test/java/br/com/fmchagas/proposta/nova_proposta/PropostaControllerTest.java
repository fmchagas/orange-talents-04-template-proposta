package br.com.fmchagas.proposta.nova_proposta;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import br.com.fmchagas.proposta.cliente_externo.solicitacao.RestrincaoSolicitacao;
import br.com.fmchagas.proposta.cliente_externo.solicitacao.Solicitacao;
import br.com.fmchagas.proposta.cliente_externo.solicitacao.SolicitacaoRequest;
import br.com.fmchagas.proposta.cliente_externo.solicitacao.SolicitacaoResponse;;

@ExtendWith(SpringExtension.class) // dizer ao JUnit 5 para ativar o suporte ao Spring
@WebMvcTest(controllers = PropostaController.class) // restringir o contexto do app criado para unico bean debcontrolador
class PropostaControllerTest {

	/*
	 * @MockBean - substitui automaticamente o bean do mesmo tipo no contexto do app
	 * por um mock Mockito
	 */
	@MockBean
	private PropostaRepository propostaRepository;
	@MockBean
	private Solicitacao solicitacaoCliente;

	@Autowired
	private MockMvc mockMvc;

	/*
	 * O Spring Boot fornece automaticamente beans como um @ObjectMapper para mapear
	 * JSON e uma MockMvc instância para simular solicitações HTTP.
	 */
	//@Autowired
	//private ObjectMapper objectMapper;


	@Test
	void deveRetornar422_QuadoDocumentoPropostaNaoForUnico() {
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
	 * Ainda não sei como testar quando temos um método privado do controller
	 * quando fazer chamda para método da null pointer exception
	 * ou então o null pointer é pq não consegui dizer ao mock como passar uma proposta como parametro do método
	 */
	
	@Test
	void deveRetornar201_QuadoPropostaForValida() {		
		Proposta proposta = new Proposta("60178303000120", "email@gmail.com", "Dunha", "Rua a", BigDecimal.TEN);
		proposta.setElegibilidade(Elegibilidade.ELEGIVEL);
		
		when(propostaRepository.existsByDocumento("60178303000120")).thenReturn(false);
		
		when(solicitacaoCliente.consultaViaHttp(
				new SolicitacaoRequest(proposta.getDocumento(), proposta.getNome(), 1L))
			)
		.thenReturn(new SolicitacaoResponse("60178303000120", "dunha", RestrincaoSolicitacao.SEM_RESTRICAO, 1));
		
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
