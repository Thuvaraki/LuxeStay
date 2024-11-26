package com.project.backend.security;

import com.project.backend.security.jwt.AuthTokenFilter;
import com.project.backend.security.jwt.JwtAuthEntryPoint;
import com.project.backend.security.user.HotelUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;


@Configuration //Indicates that this class provides configuration settings for the Spring application context.
@EnableMethodSecurity(securedEnabled = true, jsr250Enabled = true, prePostEnabled = true) //Enables method-level security annotations like @Secured, @PreAuthorize, and @PostAuthorize
public class WebSecurityConfig {
    @Autowired
    private  HotelUserDetailsService userDetailsService;

    @Autowired
    private  JwtAuthEntryPoint jwtAuthEntryPoint;

//    Creates and returns an instance of AuthTokenFilter, which handles JWT token validation.
//    @Bean annotation tells Spring that the return value of the method should be managed by the Spring container as a bean.
// For our AuthTokenFilter, we're defining it as a bean because it needs to be instantiated and registered with the Spring context, ensuring that Spring manages its lifecycle and dependencies
    @Bean
    public AuthTokenFilter authenticationTokenFilter(){
        return new AuthTokenFilter();
    }

//    Creates and returns an instance of BCryptPasswordEncoder, which is used to encode passwords securely
    //BCryptPasswordEncoder is used for securely hashing passwords before storing them in a database
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    //DaoAuthenticationProvider is a  class, part of Spring Security's authentication process. It provides the mechanism for authenticating a user based on their username and password
//    @Bean public DaoAuthenticationProvider authenticationProvider(): Configures the authentication provider, which uses HotelUserDetailsService to
//    load user details and BCryptPasswordEncoder to check passwords.
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        var authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService); //we associate the HotelUserDetailsService (which implements UserDetailsService) with the DaoAuthenticationProvider. This service is responsible for loading user-specific data  from your database.
        authProvider.setPasswordEncoder(passwordEncoder()); //This line configures the DaoAuthenticationProvider to use the BCryptPasswordEncoder for password validation.
        return authProvider;
    }

    //AuthenticationManager is an interface responsible for managing the authentication process, which typically involves checking a user's credentials against a security context.
    //AuthenticationConfiguration: This is a Spring Security class that simplifies the configuration of authentication. It provides access to the AuthenticationManager, which is required by Spring Security for handling authentication.
    //getAuthenticationManager(): This method retrieves the default AuthenticationManager provided by Spring Security's AuthenticationConfiguration. By annotating this method with @Bean, it ensures that the AuthenticationManager is available to Spring's application context.
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer :: disable) //Disables CSRF  protection
                .exceptionHandling(
                        exception -> exception.authenticationEntryPoint(jwtAuthEntryPoint)) //If a request is unauthenticated or the token is invalid, it will redirect to jwtAuthEntryPoint
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) //Configures the application to use stateless session management. //This means that no session state is stored between requests. //The user's state is entirely carried by the JWT token, which is checked on each request.
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/**", "/api/v1/rooms/**","/api/v1/bookings/**") //These endpoints are publicly accessible, meaning anyone can access them without authentication.
                        .permitAll().requestMatchers("/api/v1/roles/**").hasRole("ADMIN")
                        .anyRequest().authenticated()); //All other endpoints are restricted and require authentication.
        http.authenticationProvider(authenticationProvider());
        http.addFilterBefore(authenticationTokenFilter(), UsernamePasswordAuthenticationFilter.class); //Adds the AuthTokenFilter before the UsernamePasswordAuthenticationFilter in the filter chain. This means that the JWT authentication filter will run first, ensuring that the token is validated before Spring's default username/password-based authentication process.
        return http.build();
    }
}
