package com.example.authentication.configuration.WebConfiguration;

import com.example.authentication.configuration.jwt.JwtAuthenticationFilter;
import com.example.authentication.configuration.jwt.JwtAuthorizationFilter;
import com.example.authentication.service.UserDetailsImpl;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
@AllArgsConstructor
public class WebConfiguration {
    private UserDetailsImpl userDetailServiceImpl;
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationConfiguration authenticationConfiguration;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable());
        http.sessionManagement(sess->sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http.headers(headers -> headers.disable());
        http.formLogin(f -> f.permitAll());
        http.httpBasic(Customizer.withDefaults());
        http.authorizeHttpRequests(authorize -> authorize.anyRequest().authenticated());
        http.userDetailsService(userDetailServiceImpl);
        http.addFilter(new JwtAuthenticationFilter(authenticationManager(authenticationConfiguration)));
        http.addFilterBefore(new JwtAuthorizationFilter(),UsernamePasswordAuthenticationFilter.class);

        return http.build();

    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
