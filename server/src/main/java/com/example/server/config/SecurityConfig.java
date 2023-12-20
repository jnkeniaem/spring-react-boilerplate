package com.example.server.config;

import com.example.server.auth.filter.MemberSessionAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.server.resource.web.BearerTokenAuthenticationFilter;
import org.springframework.security.oauth2.server.resource.web.DefaultBearerTokenResolver;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

	private final ClientConfig clientConfig;
	private final AuthenticationManager jwtAuthenticationManager;
	private final AuthenticationEntryPoint jwtAuthenticationEntryPoint;
	private final AccessDeniedHandler jwtAccessDeniedHandler;

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
		// @formatter:off
		httpSecurity.csrf().disable()
				.formLogin().disable()
				.addFilterAfter(new MemberSessionAuthenticationFilter(), BearerTokenAuthenticationFilter.class)
				.cors()
					.configurationSource(corsConfigurationSource())
				.and()
				.csrf()
					.disable()
				.sessionManagement()
					.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				.and()
				.oauth2ResourceServer()
					.bearerTokenResolver(new DefaultBearerTokenResolver())
					.jwt()
						.authenticationManager(jwtAuthenticationManager)
					.and()
					.accessDeniedHandler(jwtAccessDeniedHandler)
					.authenticationEntryPoint(jwtAuthenticationEntryPoint)
				;
		// @formatter:on
		return httpSecurity.build();
	}

	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();

		configuration.addAllowedOrigin(clientConfig.getClientUrl());
		configuration.addAllowedOrigin(clientConfig.getClientHttpUrl());
		configuration.addAllowedHeader("*");
		configuration.addAllowedMethod("*");
		configuration.setAllowCredentials(true);

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}

	@Bean
	public WebSecurityCustomizer ignoreCustomizer() {
		return web -> web.ignoring()
				.antMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html",
						"/actuator/**",
						"/api/v1/auth/register",
						"/api/v1/auth/login"
				);
	}
}