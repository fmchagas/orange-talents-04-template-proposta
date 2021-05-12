package br.com.fmchagas.proposta.nova_proposta;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PropostaRepository extends CrudRepository<Proposta, Long>{
	@Query(value = "SELECT id, documento, elegibilidade, email, endereco, nome, salario, cartao_id FROM proposta WHERE elegibilidade = 'ELEGIVEL' AND cartao_id IS NULL", nativeQuery = true)
	List<Proposta> pegaPropostasElegiveisSemCartao();
	
	boolean existsByDocumento(String documento);
}
