package com.cobistopaz.prueba_tecnica.application.security;

import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.cobistopaz.prueba_tecnica.application.implementations.UserDetailsImpl;
import com.cobistopaz.prueba_tecnica.domain.exceptions.NoAutorizadoException;
import com.cobistopaz.prueba_tecnica.domain.model.Credenciales;
import com.cobistopaz.prueba_tecnica.domain.web.factorias.RespuestaHttpFactory;
import com.cobistopaz.prueba_tecnica.infraestructure.api.security.jwt.JwtAuthManager;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    JwtAuthManager authManager = new JwtAuthManager();

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        Credenciales credenciales = new Credenciales();
        try {
            credenciales = new ObjectMapper().readValue(request.getReader(), Credenciales.class);
        } catch (Exception e) {
            throw new NoAutorizadoException("No se encuentra autorizado para realizar esta acción");
        }

        UsernamePasswordAuthenticationToken UPAT = new UsernamePasswordAuthenticationToken(
                credenciales.getNombreUsuario(),
                credenciales.getContrasena());

        return getAuthenticationManager().authenticate(UPAT);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
            Authentication result) {
        try {
            UserDetailsImpl userDetails = (UserDetailsImpl) result.getPrincipal();
            Credenciales credenciales = new Credenciales(userDetails.getUsername(), userDetails.getPassword());
            String token = authManager.generarToken(credenciales);

            response.addHeader("Authorization", "Bearer " + token);
            response.setStatus(HttpStatus.ACCEPTED.value());
            response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
            response.getWriter().write(
                    RespuestaHttpFactory.respuestaExitosa(
                            "Ha iniciado sesión correctamente. El token también podrá encontrarlo en las cabeceras de la petición",
                            HttpStatus.ACCEPTED.value(), token)
                            .toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException failed) throws IOException, ServletException {
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        response.getWriter().write(
                RespuestaHttpFactory.respuestaError("Las credenciales son incorrectas. Verifique por favor.",
                        HttpStatus.BAD_REQUEST.value()).toString());
        return;
    }
}
