package br.com.fmchagas.proposta.aviso_viagem;

import java.util.UUID;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AvisoViagemRepository extends CrudRepository<AvisoViagem, UUID>{

}
