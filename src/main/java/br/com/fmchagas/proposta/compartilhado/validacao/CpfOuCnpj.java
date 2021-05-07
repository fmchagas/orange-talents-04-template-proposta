package br.com.fmchagas.proposta.compartilhado.validacao;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.ReportAsSingleViolation;

import org.hibernate.validator.constraints.CompositionType;
import org.hibernate.validator.constraints.ConstraintComposition;
import org.hibernate.validator.constraints.br.CNPJ;
import org.hibernate.validator.constraints.br.CPF;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@ConstraintComposition(CompositionType.OR)
@ReportAsSingleViolation
@Constraint(validatedBy = { })
@CPF @CNPJ
public @interface CpfOuCnpj {
	String message() default "{br.com.fmchagas.proposta.compartilhado.validacao.CpfOrCnpj.message}";
	
	Class<?>[] groups() default{};
	
	Class<? extends Payload>[] payload() default {};
}
