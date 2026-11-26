package com.TestingApplication.TestingApp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
   //the user can directly login into any route without the form login because there
    //is no authorization before .formlogin()
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http.authorizeHttpRequests(auth ->
                        auth.requestMatchers("/employees").permitAll()
                                .requestMatchers("/employees/**").hasAnyRole("ADMIN")
                                .anyRequest().authenticated())
                .formLogin(Customizer.withDefaults());

        return http.build();
    }

   /* @Bean
    UserDetailsService userDetailsService(){
        UserDetails userDetails = User.withUsername("harshu")
                .password(passwordEncoder().encode("harshu123"))
                .roles("USER")
                .build();

        UserDetails adminUser = User.withUsername("admin")
                .password(passwordEncoder().encode("admin"))
                .roles("ADMIN")
                .build();

        return new InMemoryUserDetailsManager(userDetails, adminUser);
    }*/

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
