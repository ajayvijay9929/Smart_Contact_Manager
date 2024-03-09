package com.smart.smartcontactmanager.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.HttpBasicConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class MyConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        // deperacated after spring boot 3 version
        // http.authorizeHttpRequests().anyRequest().denyAll();
        // http.authorizeHttpRequests().anyRequest().permitAll();
        // http.authorizeRequests().requestMatchers(AntPathRequestMatcher.antMatcher("/user/**")).authenticated();
        // http.formLogin();
        // http.httpBasic();

        // this is for new version for spring boot
        http.csrf(c -> c.disable()).authorizeHttpRequests(cus -> {
            // cus.requestMatchers("/user/**").authenticated();
            cus.requestMatchers("/user/**").hasRole("USER");
            cus.requestMatchers("/user/create_order").permitAll();
            cus.requestMatchers("/**").permitAll();
            cus.anyRequest().authenticated();
        });

        // custom login page
        http.formLogin(cus -> {
            cus.loginPage("/login").defaultSuccessUrl("/user/index");
        });

        // logout

        // this is user for default spring security login page
        // http.formLogin(Customizer.withDefaults());
        // http.httpBasic(Customizer.withDefaults());

        return http.build();
    }

    // this is security
    // @Bean
    // public SecurityFilterChain securityFilterChain(HttpSecurity http) throws
    // Exception {
    // http.csrf(c -> c.disable())
    // .authorizeHttpRequests(request -> request.requestMatchers("/user/**")
    // .permitAll().anyRequest().authenticated())
    // .formLogin(form ->
    // form.loginPage("/signup").loginProcessingUrl("/signup").defaultSuccessUrl("/")
    // .permitAll());
    // return http.build();
    // }

    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(this.userDetailsService());
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;

    }

    @Bean
    public CsrfTokenRepository csrfTokenRepository() {
        HttpSessionCsrfTokenRepository repository = new HttpSessionCsrfTokenRepository();
        repository.setHeaderName("X-CSRF-TOKEN"); // Customize the header name if needed
        return repository;
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new UserDetailsServiceImpl();
    }
}
