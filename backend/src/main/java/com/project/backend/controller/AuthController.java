package com.project.backend.controller;

import com.project.backend.Response.JwtResponse;
import com.project.backend.exception.UserAlreadyExistsException;
import com.project.backend.model.User;
import com.project.backend.request.LoginRequest;
import com.project.backend.security.jwt.JwtUtils;
import com.project.backend.security.user.HotelUserDetails;
import com.project.backend.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.GrantedAuthority;


import java.util.List;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:3000")
public class AuthController {
    @Autowired //Field injection , a type of dependency injection
    private UserService userService;
    @Autowired
    private AuthenticationManager authenticationManager; //interface that is part of Spring Security

    @Autowired
    private JwtUtils jwtUtils;

    @PostMapping("/registerUser")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        try{
            userService.registerUser(user);
            return ResponseEntity.ok("Registration successful!");
        }catch(UserAlreadyExistsException e){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());

        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest request){ //@valid - ensures that the request object complies with the validation rules defined in the LoginRequest class.
        Authentication authentication =
                authenticationManager
                        .authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication); //sets the authenticated Authentication object in the SecurityContext for the current thread.
        String jwt = jwtUtils.generateJwtTokenForUser(authentication);
        HotelUserDetails userDetails = (HotelUserDetails) authentication.getPrincipal(); //retrieves the authenticated principal: the user from the object
        List<String> roles = userDetails.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority).toList();
        return ResponseEntity.ok(new JwtResponse(
                userDetails.getUserId(),
                userDetails.getEmail(),
                jwt,
                roles));
    }
}
