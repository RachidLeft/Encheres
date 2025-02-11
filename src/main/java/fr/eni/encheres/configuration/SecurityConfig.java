package fr.eni.encheres.configuration;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
			.authorizeHttpRequests((authorize) -> authorize
				.requestMatchers("/*").permitAll()
				.requestMatchers("/login").permitAll()
				.requestMatchers("/utilisateurs/ajout").permitAll()
				//.anyRequest().denyAll() // Interdit l'accès aux URLs non configurées
			)
			.httpBasic(Customizer.withDefaults())
			.formLogin((formLogin) ->
				formLogin
					.loginPage("/login").permitAll()
					.defaultSuccessUrl("/").permitAll()
			)
			.logout((logout) ->
				logout
					.invalidateHttpSession(true)
					.logoutRequestMatcher(new AntPathRequestMatcher("/logout", "GET"))
					.logoutSuccessUrl("/")
			);	

		return http.build();
	}
	

	@Bean
	// Permet de chercher les utilisateurs en base de données
	public UserDetailsManager users(DataSource dataSource) {
		JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);
		jdbcUserDetailsManager.setUsersByUsernameQuery(
		    "SELECT pseudo, mot_de_passe, 'true' AS enabled FROM UTILISATEURS WHERE pseudo = ?"		
		);
		
		return jdbcUserDetailsManager;
	}

	@Bean
	// Ajout du PasswordEncoder pour pouvoir encoder/décoder les mots de passe
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();

	

	}
}
