package com.ecw.gateway;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    CustomUserDetailsService customUserDetailsService;

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
  /*
  http.csrf().disable().authorizeHttpRequests()
          .requestMatchers("/register").permitAll()
          .requestMatchers("/livelogin").permitAll()
          .requestMatchers(HttpMethod.POST, "/login").permitAll()
          .requestMatchers("home")
    .permitAll().and().formLogin().loginPage("/login")
          .loginProcessingUrl("/login")
    .defaultSuccessUrl("/home", true).permitAll()
          .and().logout().invalidateHttpSession(true)
    .clearAuthentication(true).logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
    .logoutSuccessUrl("/login?logout").permitAll();
*/
        http.csrf().disable().authorizeHttpRequests((requests) -> requests
                                .requestMatchers("/login", "/livelogin", "/register", "/logout", "/public/**").permitAll()
                                .requestMatchers("/login.html").permitAll()
               /*   .requestMatchers(HttpMethod.POST, "/login").permitAll()
                  .requestMatchers("/admin/**").hasRole("ADMIN")
                  //.requestMatchers("/admin/**").hasRole("ROLE_ADMIN")
                  .requestMatchers("/user/**").hasRole("USER")
                  //.requestMatchers("/user/**").hasRole("ROLE_USER")
                  .anyRequest().authenticated()*/
                )
                .formLogin((form) -> form
                        .loginPage("/login.html")
                        .defaultSuccessUrl("/home", true)
                        .permitAll()
                )
                .logout((logout) -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                );
        return http.build();


    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserDetailsService).passwordEncoder(passwordEncoder());

    }
}