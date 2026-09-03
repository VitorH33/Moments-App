package br.com.isiflix.appmoments.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class MyWebConfiguration {

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
		http.csrf((csrf)->{
			csrf.disable();
		})
		.authorizeHttpRequests((auth) -> {
			
			auth.requestMatchers(HttpMethod.POST, "/clientes").permitAll();
            auth.requestMatchers(HttpMethod.POST, "/Login").permitAll();
            auth.requestMatchers(HttpMethod.POST, "/pictures").permitAll();
			auth.anyRequest().authenticated();
			
		})
		.addFilterBefore(new AppMomentsFilter(), UsernamePasswordAuthenticationFilter.class);
		
		
		return http.build();
	}
}
