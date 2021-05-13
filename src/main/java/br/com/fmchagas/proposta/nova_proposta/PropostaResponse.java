package br.com.fmchagas.proposta.nova_proposta;

import br.com.fmchagas.proposta.compartilhado.utils.SimplesOfuscador;

public class PropostaResponse {

	private Elegibilidade status;
	private String documento;
	private String nome;
	private String email;
	private String endereco;

	public PropostaResponse(Proposta proposta) {
		status = proposta.getElegibilidade();
		nome = proposta.getNome();
		documento = SimplesOfuscador.ofuscar(proposta.getDocumento());
		email = SimplesOfuscador.ofuscar(proposta.getEmail());
		endereco = proposta.getEndereco();
	}
	
	public Elegibilidade getStatus() {
		return status;
	}
	
	public String getNome() {
		return nome;
	}
	
	public String getDocumento() {
		return documento;
	}
	
	public String getEmail() {
		return email;
	}
	
	public String getEndereco() {
		return endereco;
	}
}
