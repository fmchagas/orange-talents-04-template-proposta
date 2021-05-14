package br.com.fmchagas.proposta.cartao;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import br.com.fmchagas.proposta.cliente_externo.cartao.CartaoCliente;
import br.com.fmchagas.proposta.cliente_externo.cartao.CartaoClienteResponse;
import br.com.fmchagas.proposta.compartilhado.utils.SimplesOfuscador;
import br.com.fmchagas.proposta.nova_proposta.Proposta;
import br.com.fmchagas.proposta.nova_proposta.PropostaRepository;
import feign.FeignException;

@Component @EnableScheduling
public class AssociaCartaoSchedule {
	private final Logger logger = LoggerFactory.getLogger(AssociaCartaoSchedule.class);

	private PropostaRepository propostaRepository;
	private CartaoCliente cartao;
	private CartaoRepository cartaoRepository;

	@Autowired
	public AssociaCartaoSchedule(PropostaRepository propostaRepository, CartaoCliente cartao, CartaoRepository cartaoRepository) {
		this.propostaRepository = propostaRepository;
		this.cartao = cartao;
		this.cartaoRepository = cartaoRepository;
	}

	@Scheduled(fixedDelay=300000)
	public void associaCartaoComPropostasElegiveis() {
		logger.info("Iniciando consulta de propostas elegiveis sem cartão associado");

		List<Proposta> propostas = propostaRepository.pegaPropostasElegiveisSemCartao();
		
		logger.info("{} proposta(s) sem cartão associado", propostas.size());
		
		propostas.forEach(proposta -> {
			try {
				CartaoClienteResponse cartaoResponse = cartao.consultaViaHttp(proposta.getId());
				
				Cartao cartao = cartaoResponse.paraModelo();
				
				proposta.associaCartao(cartao);
				
				cartaoRepository.save(cartao);
				propostaRepository.save(proposta);
				
				logger.info("Cartão {} associado a proposta {}", SimplesOfuscador.ofuscar(cartao.getNumero()) ,proposta.getId());
			} catch (FeignException ex) {
				logger.info("Não encontramos cartão para proposta {}", proposta.getId());
			}
		});
	}
}
