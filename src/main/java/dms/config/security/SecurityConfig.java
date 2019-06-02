package dms.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public UserDetailsService userDetailsServiceBean() throws Exception {
        return new CustomUserDetailsService();
    }

    @Override
    protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsServiceBean()).passwordEncoder(passwordEncoder());
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http
                .authorizeRequests().
                antMatchers("/resources/**").permitAll()
                .and().authorizeRequests().antMatchers("/companies/**").access("hasAnyRole('ROLE_ADMIN')")
                .and().authorizeRequests().antMatchers("/users/**").access("hasAnyRole('ROLE_ADMIN')")
                .and().authorizeRequests().antMatchers("/activity/**").access("hasAnyRole('ROLE_PROCESS_MANAGER', 'ROLE_ADMIN')")
                .and().authorizeRequests().antMatchers("/process/**").access("hasAnyRole('ROLE_PROCESS_MANAGER', 'ROLE_ADMIN')")
                .and().authorizeRequests().antMatchers("/document/all", "/document/download/**").access("hasAnyRole('ROLE_DOCUMENT_CONSUMER', 'ROLE_ADMIN', 'ROLE_DOCUMENT_MANAGER')")
                .and().authorizeRequests().antMatchers("/document/type/**").access("hasAnyRole('ROLE_DOCUMENT_MANAGER', 'ROLE_ADMIN')")
                .and().authorizeRequests().antMatchers("/document/**").access("hasAnyRole('ROLE_DOCUMENT_MANAGER', 'ROLE_ADMIN')")
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .usernameParameter("username")
                .passwordParameter("password")
                .loginProcessingUrl("/login")
                .defaultSuccessUrl("/home")
                .failureUrl("/login?error=true")
                .permitAll()
                .and()
                .logout()
                .permitAll()
                .and()
                .csrf()
                .disable();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
