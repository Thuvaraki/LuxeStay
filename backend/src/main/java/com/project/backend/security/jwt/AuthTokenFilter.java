package com.project.backend.security.jwt;

import com.project.backend.security.user.HotelUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class AuthTokenFilter extends OncePerRequestFilter {
    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private HotelUserDetailsService userDetailsService;
    private static final Logger logger = LoggerFactory.getLogger(AuthTokenFilter.class);
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        try{
//            attempts to extract a JWT token (jwt) from the request using parseJwt method
            String jwt = parseJwt(request);
            if (jwt != null && jwtUtils.validateToken(jwt)){
                String email = jwtUtils.getUserNameFromToken(jwt);
                UserDetails userDetails = userDetailsService.loadUserByUsername(email);
                var authentication =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));//Adds additional details to the authentication object, such as the client IP address and session information.
                SecurityContextHolder.getContext().setAuthentication(authentication); //Stores the authentication object in the SecurityContext, which is Spring Security's mechanism for maintaining the authentication state of the user throughout the request.

            }
        }catch (Exception e){
            logger.error("Cannot set user authentication : {} ", e.getMessage());
        }
        filterChain.doFilter(request, response); //Passes the request and response to the next filter in the filter chain; Ensures that the request proceeds to the next stages of processing ( like reaching the controller).
    }

//    method to extract the JWT token from the Authorization header of the HTTP request.
    private String parseJwt(HttpServletRequest request) {
        String headerAuth = request.getHeader("Authorization");
        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")){
            return headerAuth.substring(7);
        }
        return null;
    }
}