package fr.eni.encheres.configuration;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    		http.authorizeHttpRequests(authentification -> {
    			authentification
    			.requestMatchers("/login", "/css/**").permitAll()
    			.anyRequest().permitAll();
    		});
    		http.formLogin(form -> {
    			form.usernameParameter("pseudo")
    			.passwordParameter("mot_de_passe");
    			form.loginPage("/login").permitAll()
    			.failureUrl("/login?error=true")
    			.defaultSuccessUrl("/session").permitAll();			
    		});
    		
    	
    		http.logout(logout -> 
    			logout
    			.invalidateHttpSession(true)
    			.logoutRequestMatcher(new AntPathRequestMatcher("/logout", "GET"))
    			.logoutSuccessUrl("/"));


    		return http.build();
    }

    @Bean
    public UserDetailsManager users(DataSource dataSource) {
        JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);
        jdbcUserDetailsManager.setUsersByUsernameQuery(
            "SELECT pseudo, mot_de_passe, 'true' as enable from UTILISATEURS where pseudo=?"
        );
        jdbcUserDetailsManager.setAuthoritiesByUsernameQuery(
        	"SELECT U.pseudo, R.role FROM UTILISATEURS U INNER JOIN ROLES R ON R.IS_ADMIN = U.administrateur WHERE U.pseudo = ?"
        );
        return jdbcUserDetailsManager;
    }

}
