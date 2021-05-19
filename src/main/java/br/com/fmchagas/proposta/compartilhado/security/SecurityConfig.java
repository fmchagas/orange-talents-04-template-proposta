package br.com.fmchagas.proposta.compartilhado.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;

@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.cors().and()
			/*.authorizeRequests()
				.antMatchers("/api/propostas/*").authenticated()
				.antMatchers("/api/cartoes/*").authenticated()
			.and()*/
			
		.authorizeRequests().anyRequest().authenticated().and()
		.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
			
		.oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt);
	}
}
