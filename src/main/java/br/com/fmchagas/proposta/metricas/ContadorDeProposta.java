package br.com.fmchagas.proposta.metricas;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.fmchagas.proposta.nova_proposta.Elegibilidade;
import br.com.fmchagas.proposta.nova_proposta.Proposta;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;

@Component
public class ContadorDeProposta {

	private MeterRegistry registry;
	Counter elegiel;
	Counter naoElegiel;
	
	@Autowired
	public ContadorDeProposta(MeterRegistry registry) {
		this.registry = registry;
		
		initOrderCounters();
	}
	
	private void initOrderCounters() {
		//elegiel = this.registry.counter("proposta", "sistema", "Proposta", "metodo", "POST", "tipo", "elegivel"); // cria contador
		
		elegiel = Counter.builder("proposta") //cria contador com fluent API
	            .tag("sistema", "Proposta")
	    		.tag("metodo", "POST")
	    		.tag("tipo", "elegivel")
	            .description("contador de propostas")
	            .register(registry);
		
		naoElegiel = Counter.builder("proposta") //cria contador com fluent API
	            .tag("sistema", "Proposta")
	    		.tag("metodo", "POST")
	    		.tag("tipo", "nao_elegivel")
	            .description("contador de propostas")
	            .register(registry);
	}

	public void conta(Proposta proposta) {
		if (Elegibilidade.ELEGIVEL.equals(proposta.getElegibilidade())) {
			elegiel.increment(1);
		}
		
		if(Elegibilidade.NAO_ELEGIVEL.equals(proposta.getElegibilidade())){
			naoElegiel.increment();
		}
	}
}