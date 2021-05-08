package br.com.fmchagas.proposta.nova_proposta;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@AutoConfigureTestDatabase(replace =AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
class PropostaRepositoryTest {
	
	@Autowired
	private PropostaRepository propostaRepository;
	
	private Proposta criarProposta() {
		return new Proposta("89005604093", "email@gmail.com", "Dunha", "Rua a", new BigDecimal("1000.0"));
	}
	
	
	@Test
	void deveSalvarProposta() {
		Proposta proposta = criarProposta();
		
		Proposta propostaSalva = propostaRepository.save(proposta);
		assertNotNull(propostaSalva);
	}

	
	@Test
	void deveRetornarUm_QuandoExistirPropostaCadastradaComMemsmoDocumento() {
		String documento = "89005604093";
		
		Proposta proposta = criarProposta();
		propostaRepository.save(proposta);
		
		int countaDocumento = propostaRepository.countByDocumento(documento);

		assertEquals(1, countaDocumento);
	}
	
	@Test
	void deveRetornarZero_QuandoNaoExistirPropostaCadastradaComDocumento() {
		String documento = "89005604093";
		
		int countaDocumento = propostaRepository.countByDocumento(documento);

		assertEquals(0, countaDocumento);
	}
	
	@Test
	void deveDarErroDeIntegridade_QuandoCadastrarPropostaComMemsmoDocumento() {	
		assertThrows(DataIntegrityViolationException.class, ()->{
			propostaRepository.save(criarProposta());
			propostaRepository.save(criarProposta());
		});
	}
}
