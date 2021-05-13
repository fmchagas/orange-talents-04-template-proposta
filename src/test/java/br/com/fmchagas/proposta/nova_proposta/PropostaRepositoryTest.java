package br.com.fmchagas.proposta.nova_proposta;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;

import br.com.fmchagas.proposta.cartao.Cartao;
import br.com.fmchagas.proposta.cartao.CartaoRepository;

@DataJpaTest
@AutoConfigureTestDatabase(replace =AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
class PropostaRepositoryTest {
	
	@Autowired
	private PropostaRepository propostaRepository;
	@Autowired
	private CartaoRepository cartaoRepository;
	
	private Proposta criarProposta() {
		return new Proposta("89005604093", "email@gmail.com", "Dunha", "Rua a", new BigDecimal("1000.0"));
	}
	
	
	@Test
	void deveSalvarProposta_QuandoNaoTiverElegibilidade() {
		Proposta proposta = criarProposta();
		
		Proposta propostaSalva = propostaRepository.save(proposta);
		assertNotNull(propostaSalva);
	}
	
	@Test
	void deveAtualizarProposta_QuandoTiverElegibilidade() {
		Proposta proposta = criarProposta();
		proposta.setElegibilidade(Elegibilidade.ELEGIVEL);
		
		proposta = propostaRepository.save(proposta);
		assertNotNull(proposta);
	}

	
	@Test
	void deveRetornarTrue_QuandoExistirPropostaCadastradaComMesmoDocumento() {
		String documento = "89005604093";
		
		Proposta proposta = criarProposta();
		propostaRepository.save(proposta);
		
		boolean existeDocumento = propostaRepository.existsByDocumento(documento);

		assertEquals(true, existeDocumento);
	}
	
	@Test
	void deveRetornarFalse_QuandoNaoExistirPropostaCadastradaComDocumento() {
		String documento = "89005604093";
		
		boolean naoExisteDocumento = propostaRepository.existsByDocumento(documento);

		assertEquals(false, naoExisteDocumento);
	}
	
	@Test
	void deveDarErroDeIntegridade_QuandoCadastrarPropostaComMesmoDocumento() {	
		assertThrows(DataIntegrityViolationException.class, ()->{
			propostaRepository.save(criarProposta());
			propostaRepository.save(criarProposta());
		});
	}
	
	@Test
	void deveRetornarPropostasElegiveis_QuandoNaoTiverCartaoAssociado() {
		String documento = "89005604093";
		
		Proposta proposta = criarProposta();
		proposta.setElegibilidade(Elegibilidade.ELEGIVEL);
		
		Proposta propostaSalva = propostaRepository.save(proposta);
		assertNotNull(propostaSalva);
		
		List<Proposta> propostas = propostaRepository.pegaPropostasElegiveisSemCartao();
		
		assertEquals(1, propostas.size());
		assertEquals(documento, propostas.get(0).getDocumento());
	}
	
	@Test
	void deveRetornarListaVazia_QuandoPropostasTiverCartaoAssociado() {
		
		Proposta proposta = criarProposta();
		proposta.setElegibilidade(Elegibilidade.ELEGIVEL);
		
		Cartao cartao = new Cartao("7658-7477-1406-1873", LocalDateTime.now());
		proposta.associaCartao(cartao);
		
		cartaoRepository.save(cartao);
		propostaRepository.save(proposta);
		
		List<Proposta> propostas = propostaRepository.pegaPropostasElegiveisSemCartao();
		
		assertEquals(true, propostas.isEmpty());
	}
	
	@Test
	void deveRetornarListaVazia_QuandoPropostasNaoForElegivel() {
		
		Proposta proposta = criarProposta();
		proposta.setElegibilidade(Elegibilidade.NAO_ELEGIVEL);
		
		propostaRepository.save(proposta);
		
		List<Proposta> propostas = propostaRepository.pegaPropostasElegiveisSemCartao();
		
		assertEquals(true, propostas.isEmpty());
	}
}
