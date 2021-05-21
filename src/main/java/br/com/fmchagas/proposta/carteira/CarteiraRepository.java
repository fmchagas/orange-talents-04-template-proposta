package br.com.fmchagas.proposta.carteira;

import java.util.UUID;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarteiraRepository extends CrudRepository<Carteira, UUID>{

}
