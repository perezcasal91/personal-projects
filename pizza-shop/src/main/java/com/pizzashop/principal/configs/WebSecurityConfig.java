package com.pizzashop.principal.configs;

import com.pizzashop.principal.security.AccessDeniedHandlerImpl;
import com.pizzashop.principal.security.JwtAuthenticationFilter;
import com.pizzashop.principal.security.UnauthorizedEntryPointImpl;
import com.pizzashop.principal.services.impls.UserDetailsServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@Slf4j
public class WebSecurityConfig {

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private UnauthorizedEntryPointImpl unauthorizedEntryPoint;

    @Autowired
    private AccessDeniedHandlerImpl accessDeniedHandler;

    @Value("${app.environment}")
    private String APP_ENVIRONMENT;

    /**
     * Authentication Manager
     * Changing the User Details Service to a custom(UserDetailsServiceImpl).
     *
     * @return AuthenticationManager
     */
    @Bean
    public AuthenticationManager authenticationManager
    (final AuthenticationConfiguration authenticationConfiguration,
     final AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        log.info("Executing authenticationManager from WebSecurityConfig");

        authenticationManagerBuilder
                .userDetailsService(userDetailsService)
                .passwordEncoder(bCryptPasswordEncoder());

        return authenticationConfiguration.getAuthenticationManager();
    }

    /**
     * CORS Configuration for the application.
     * Allowing Origins from front-end React App(<a href="http://localhost:3000/">...</a>)
     *
     * @return CorsConfigurationSource
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        log.info("Executing corsConfigurationSource from WebSecurityConfig");

        CorsConfiguration configuration = new CorsConfiguration();

        configuration.setAllowedOrigins(List.of("http://localhost:3000/"));

        configuration.setAllowedMethods(
                List.of("GET", "POST", "OPTIONS", "DELETE", "PUT", "PATCH"));

        configuration.setAllowedHeaders(List.of("X-Requested-With", "Content-Type",
                "Authorization", "Origin", "Accept", "Access-Control-Request-Method",
                "Access-Control-Request-Headers"));

        configuration.setMaxAge(3600L);

        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }


    /**
     * Security Filter Chain
     * Free URLs (auth, swagger)
     * Exception Handler for Authentication(UnauthorizedEntryPointImpl)
     * Exception Handler for Access Denied(AccessDeniedHandlerImpl)
     * Authentication Filter(JwtAuthenticationFilter)
     * Session is Stateless
     *
     * @return SecurityFilterChain
     **/
    @Bean
    public SecurityFilterChain securityFilterChain(
            final HttpSecurity httpSecurity) throws Exception {
        log.info("Executing securityFilterChain from WebSecurityConfig");

        httpSecurity.cors().and()
                .csrf().disable()
                .authorizeHttpRequests()
                .requestMatchers("/api/v1/auth/**").permitAll()
                .requestMatchers("/v3/api-docs/**", "/configuration/**",
                        "/swagger*/**", "/webjars/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(unauthorizedEntryPoint)
                .accessDeniedHandler(accessDeniedHandler)
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilterBefore(jwtAuthenticationFilter(),
                        UsernamePasswordAuthenticationFilter.class);

        return httpSecurity.build();
    }

    /**
     * Password Encoder Bean
     * Method: BCryptPasswordEncoder
     *
     * @return BCryptPasswordEncoder
     */
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Authentication Filter Bean
     * Filter: JSON Web Token
     *
     * @return JwtAuthenticationFilter
     */
    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter();
    }

}
