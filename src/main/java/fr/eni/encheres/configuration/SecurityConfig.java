package fr.eni.encheres.configuration;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
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
    		http.authorizeHttpRequests(auth -> {
    			auth
    			.requestMatchers("/login").permitAll()
    			.requestMatchers("/css/**").permitAll()
    			.requestMatchers("/js/*").permitAll()
    			.requestMatchers("/logout").permitAll();
    			
    			auth.requestMatchers("/","/encheres").permitAll();
    			auth.requestMatchers(HttpMethod.POST,"/encheres/filtre").hasAnyRole("USER", "ADMIN");
    			
    			// Accès à la page vendre un article
    			auth.requestMatchers(HttpMethod.GET,"/encheres/nouvellevente").hasAnyRole("USER", "ADMIN")
				.requestMatchers(HttpMethod.POST,"/encheres/nouvellevente").hasAnyRole("USER", "ADMIN");
    			
    			auth.requestMatchers(HttpMethod.GET,"/articleVendu/detail/{id}").hasAnyRole("USER", "ADMIN")
				.requestMatchers(HttpMethod.POST,"/articleVendu/detail/{id}").hasAnyRole("USER", "ADMIN");
    			
    			//Accès créer mon profil, modification mdp, suppression, modification 
    			auth.requestMatchers(HttpMethod.GET,"/utilisateurs/ajout").permitAll()
				.requestMatchers(HttpMethod.POST,"/utilisateurs/ajout").permitAll();
    			
    			auth.requestMatchers(HttpMethod.GET,"/utilisateurs/modif-mdp/{id}").hasAnyRole("USER", "ADMIN")
				.requestMatchers(HttpMethod.POST,"/utilisateurs/modif-mdp/{id}").hasAnyRole("USER", "ADMIN");
    			
    			auth.requestMatchers(HttpMethod.GET,"/utilisateurs/detail/{id}").hasAnyRole("USER", "ADMIN")
				.requestMatchers(HttpMethod.POST,"/utilisateurs/detail/{id}").hasAnyRole("USER", "ADMIN");

    			auth.requestMatchers(HttpMethod.GET,"/utilisateurs/modif/{id}").hasAnyRole("USER", "ADMIN")
				.requestMatchers(HttpMethod.POST,"/utilisateurs/modif/{id}").hasAnyRole("USER", "ADMIN");
    			
    			
    			auth.anyRequest().permitAll();
    		});
    		http.formLogin(form -> {
    			form.usernameParameter("username")
    			.passwordParameter("password");
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
