package com.api.restaurante.configs.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class ConfiguracaoSeguranca {

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.httpBasic()
		.and()
		.authorizeHttpRequests()
		.antMatchers(HttpMethod.POST, "/cadastrar/register").permitAll()
		.antMatchers(HttpMethod.GET,"/restaurante/**").hasAnyAuthority("USER", "ADMIN")
		.antMatchers(HttpMethod.GET,"/carrinho/**").hasAnyAuthority("USER", "ADMIN")
		.antMatchers("/usuarios/**").hasAnyAuthority("ADMIN")
		.antMatchers(HttpMethod.POST, "/restaurante/**").hasAnyAuthority("ADMIN")
		.antMatchers(HttpMethod.DELETE, "/restaurante/**").hasAnyAuthority("ADMIN")
		.anyRequest().authenticated().and()
		.csrf().disable()
		.exceptionHandling().accessDeniedPage("/auth/auth-acesso-negado");

		return http.build();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
