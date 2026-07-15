package sn.edu.isepat.tic.dbe.p6.GestionEtudiant.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

     private final JwtFilter jwtFilter;

     @Bean
     public PasswordEncoder passwordEncoder(){
          return new BCryptPasswordEncoder();
     }

     @Bean
     public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity){
          return httpSecurity
                  .csrf(CsrfConfigurer::disable)
                  .sessionManagement(session ->
                          session.sessionCreationPolicy(
                                  SessionCreationPolicy.STATELESS
                          )
                  )
                  .authorizeHttpRequests(
                     req ->req
                             .requestMatchers("/auth/**").permitAll()
                             .requestMatchers("/swagger-ui.html", "/swagger-ui/**", "/v3/api-docs/**").permitAll()
                             .anyRequest().authenticated()
                  )
                  .addFilterBefore(
                         jwtFilter,
                          UsernamePasswordAuthenticationFilter.class
                  )
                  .build();
     }

}
