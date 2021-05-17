package br.com.fmchagas.proposta.biometria;

import java.util.Base64;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;


public class FingerPrintTemBase64Validator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return NovaBiometriaRequest.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		if(errors.hasErrors()) return;
		
		NovaBiometriaRequest request = (NovaBiometriaRequest) target;
		
		Base64.Decoder decoder = Base64.getDecoder();

		try {
		    decoder.decode(request.getFingerPrint());
		} catch(IllegalArgumentException iae) {
			errors.reject("fingerPrint", null, "não é uma Base64 válida!");
		}
	}
}
