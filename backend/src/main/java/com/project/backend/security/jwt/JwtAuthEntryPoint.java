package com.project.backend.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtAuthEntryPoint  implements AuthenticationEntryPoint {

//    This method is called when an unauthenticated user tries to access a secured resource. It handles the initiation of the authentication process.
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {

        response.setContentType(MediaType.APPLICATION_JSON_VALUE); //It tells the client ( a browser or API consumer) that the response body will be in JSON format.
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        final Map<String, Object> body = new HashMap<>();
        body.put("status", HttpServletResponse.SC_UNAUTHORIZED);
        body.put("error", "Unauthorized");
        body.put("message", authException.getMessage());
        body.put("path", request.getServletPath());

//  create a new instance of ObjectMapper.ObjectMapper is a class which is a utility from the Jackson library used to convert Java objects to JSON and vice versa.
//  The final keyword indicates that the mapper reference cannot be reassigned to a different ObjectMapper instance.
        final ObjectMapper mapper = new ObjectMapper();
//  writeValue method of ObjectMapper is used to serialize the body map (a Java object) into JSON format.
//  By passing response.getOutputStream() as the first argument, writeValue writes the serialized JSON representation of the body map directly to the response output stream.
        mapper.writeValue(response.getOutputStream(), body);
    }
}
