package br.com.fmchagas.proposta.compartilhado.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.cors().and()
			.authorizeRequests()
				.antMatchers(HttpMethod.GET, "/api/propostas/*").hasAuthority("SCOPE_proposta")
				.antMatchers(HttpMethod.POST, "/api/propostas").hasAuthority("SCOPE_proposta")
				.antMatchers(HttpMethod.POST, "/api/cartoes/**").hasAuthority("SCOPE_proposta")
				.anyRequest().authenticated()
			.and()
			
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
			.oauth2ResourceServer(rsc -> rsc.jwt());
	}
}