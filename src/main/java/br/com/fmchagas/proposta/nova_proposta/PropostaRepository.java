package br.com.fmchagas.proposta.nova_proposta;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PropostaRepository extends CrudRepository<Proposta, Long>{

	boolean existsByDocumento(String documento);
}
