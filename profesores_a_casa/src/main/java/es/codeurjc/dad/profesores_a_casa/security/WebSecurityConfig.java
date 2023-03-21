package es.codeurjc.dad.profesores_a_casa.security;

import java.security.SecureRandom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	RepositoryUserDetailsService userDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(10, new SecureRandom());
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	}
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //Public pages:
        http.authorizeHttpRequests(requests -> requests.antMatchers("/").permitAll());
        http.authorizeHttpRequests(requests -> requests.antMatchers("/logIn").permitAll());
        http.authorizeHttpRequests(requests -> requests.antMatchers("/logInError").permitAll());
        http.authorizeHttpRequests(requests -> requests.antMatchers("/post").permitAll());
        http.authorizeHttpRequests(requests -> requests.antMatchers("/otherProfile").permitAll());
        http.authorizeHttpRequests(requests -> requests.antMatchers("/signUp").permitAll());

        //Private pages:
        http.authorizeHttpRequests(requests -> requests.antMatchers("/logout").hasAnyRole("USER"));
        http.authorizeHttpRequests(requests -> requests.antMatchers("/newContract").hasAnyRole("USER"));
        http.authorizeHttpRequests(requests -> requests.antMatchers("/contract").hasAnyRole("USER"));
        http.authorizeHttpRequests(requests -> requests.antMatchers("/deleteContract").hasAnyRole("USER"));
        http.authorizeHttpRequests(requests -> requests.antMatchers("/newPost").hasAnyRole("USER"));
        http.authorizeHttpRequests(requests -> requests.antMatchers("/createPost").hasAnyRole("USER"));
        http.authorizeHttpRequests(requests -> requests.antMatchers("/rank").hasAnyRole("USER"));
        http.authorizeHttpRequests(requests -> requests.antMatchers("/deletePost").hasAnyRole("USER"));
        http.authorizeHttpRequests(requests -> requests.antMatchers("/myProfile").hasAnyRole("USER"));
        http.authorizeHttpRequests(requests -> requests.antMatchers("/deleteUser").hasAnyRole("USER"));
        http.authorizeHttpRequests(requests -> requests.antMatchers("/changeDataUser").hasAnyRole("USER"));
        http.authorizeHttpRequests(requests -> requests.antMatchers("/changeProfile").hasAnyRole("USER"));
        http.authorizeHttpRequests(requests -> requests.antMatchers("/newReport").hasAnyRole("USER"));
        http.authorizeHttpRequests(requests -> requests.antMatchers("/report").hasAnyRole("USER"));
        
        //Form login:
        http.formLogin(login -> login.loginPage("/logIn"));
        http.formLogin(login -> login.usernameParameter("username"));
        http.formLogin(login -> login.passwordParameter("password"));
        http.formLogin(login -> login.defaultSuccessUrl("/"));
        http.formLogin(login -> login.failureUrl("/logInError"));
        
        //logout:
        http.logout(logout -> logout.logoutUrl("/logout"));
        http.logout(logout -> logout.logoutSuccessUrl("/"));
    }
}
