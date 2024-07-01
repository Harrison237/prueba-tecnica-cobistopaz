package com.cobistopaz.prueba_tecnica.application.security;

import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.cobistopaz.prueba_tecnica.application.implementations.UserDetailsImpl;
import com.cobistopaz.prueba_tecnica.domain.exceptions.NoAutorizadoException;
import com.cobistopaz.prueba_tecnica.domain.model.Credenciales;
import com.cobistopaz.prueba_tecnica.infraestructure.api.security.jwt.JwtAuthManager;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

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
            response.getWriter().flush();

            super.successfulAuthentication(request, response, chain, result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
