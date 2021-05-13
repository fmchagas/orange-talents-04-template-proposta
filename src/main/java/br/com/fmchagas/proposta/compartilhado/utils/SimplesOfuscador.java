package br.com.fmchagas.proposta.compartilhado.utils;

import org.springframework.util.Assert;

public class SimplesOfuscador {
	
	/**
	 * Dado uma informação não {@code nula} maior de 7 caracteres retorna ofuscada.
	 * <pre class="code">SimplesOfuscador.ofuscar("dummy@provedor.com");</pre>
	 * <pre>
	 *	Exemplo 1:
	 *		SimplesOfuscador.ofuscar("dummy@provedor.com");
	 *		retorna : dumm***@provedor.com
	 * 
	 *	Exemplo 2:
	 *		SimplesOfuscador.ofuscar("60178303000120");
	 *		retorna : 6017***120
	 * </pre>
	 * @param informacao
	 */

	public static String ofuscar(String informacao) {
		Assert.notNull(informacao, "Ops, não podemos ofuscar um dado nulo ou vazio");
		
		if(informacao.length() <= 7) {
			return informacao;
		}
		
		String resultado = "";
		
		if (informacao.matches("^(\\S+)@((?:(?:(?!-)[a-zA-Z0-9-]{1,62}[a-zA-Z0-9])\\.)+[a-zA-Z0-9]{2,12})$")) {
			resultado = ofuscaEmail(informacao);
		}else {
			resultado = informacao.substring(4, informacao.length() -3);
			resultado = informacao.replace(resultado, "***");
		}
		
		return resultado;
	}
	
	/**
	 * Dado um {@code email} em que o usuário é maior de 4 caracteres retorna ofuscado.
	 * @param email
	 */
	
	private static String ofuscaEmail(String email) {
		Assert.notNull(email, "Ops, não podemos ofuscar um email nulo ou vazio");
		
		String[] splitEmail = email.split("@");
		
		if(splitEmail[0].length() <= 4) {
			return email;
		}
		
		email = splitEmail[0];
		
		String resultado = email.replace(email.substring(4), "***");
		
		return resultado +  "@" + splitEmail[1];
	}
}
