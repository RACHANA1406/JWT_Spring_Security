package com.example.spring_security.jwt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import com.example.spring_security.service.UserDetailsServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	@Autowired
	private UserDetailsServiceImpl userDetailsServiceImpl;
	
	@Autowired
	private JwtFilter jwtFilter;
	
	@Autowired
	private AuthEntryPoint authEntryPoint;

	@Bean 
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
		return http.
				csrf(c->c.disable())
				.authorizeHttpRequests(request -> request
					.requestMatchers("/public/**").permitAll()
                    .requestMatchers("/admin/**").hasAuthority("ADMIN")
					.requestMatchers("/employee/**").hasAuthority("USER")
					.requestMatchers("/auth/user/**").hasAnyAuthority("ADMIN", "USER")
					.anyRequest()
					.authenticated()
				)
				.userDetailsService(userDetailsServiceImpl)
				.sessionManagement(session->session
						.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.exceptionHandling(e-> e.authenticationEntryPoint(authEntryPoint)
						.accessDeniedHandler(accessDeniedHandler()))
				.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
				.build();
	}
	
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean 
	AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
		return configuration.getAuthenticationManager();
	}
	
	@Bean
	AccessDeniedHandler accessDeniedHandler() {
		return (HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException)->{
			response.setStatus(HttpServletResponse.SC_FORBIDDEN);
		};
	}
}