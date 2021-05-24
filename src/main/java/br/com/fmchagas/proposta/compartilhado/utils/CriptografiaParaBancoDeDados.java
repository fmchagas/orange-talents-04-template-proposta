package br.com.fmchagas.proposta.compartilhado.utils;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.util.Assert;

@Converter
public class CriptografiaParaBancoDeDados implements AttributeConverter<String, String> {

    @Override
    public String convertToDatabaseColumn(String atributo) {
    	Assert.notNull(atributo, "O atributo não deveria ser nulo nesse ponto");
    	
    	return Encryptors.queryableText("${proposta.criptografia.secret.database}", "2A3F1B").encrypt(atributo);
    }

    @Override
    public String convertToEntityAttribute(String dbData) {
    	Assert.notNull(dbData, "O atributo não deveria ser nulo nesse ponto");
    	
    	return Encryptors.queryableText("${proposta.criptografia.secret.database}", "2A3F1B").decrypt(dbData);
    }
}