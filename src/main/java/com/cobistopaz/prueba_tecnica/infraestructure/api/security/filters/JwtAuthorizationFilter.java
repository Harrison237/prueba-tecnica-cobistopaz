package com.cobistopaz.prueba_tecnica.infraestructure.api.security.filters;

import java.io.IOException;

import com.cobistopaz.prueba_tecnica.infraestructure.api.security.IAuthManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.cobistopaz.prueba_tecnica.domain.web.factorias.RespuestaHttpFactory;

import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;

@Component("jwtAuthorizationFilter")
@AllArgsConstructor
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    @Autowired
    IAuthManager jwtService;

    @Autowired
    UserDetailsService detailsService;

    @SuppressWarnings("deprecation")
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            String token = extraerTokenDeRequest(request);
            String nombreUsuario;

            if (token == null) {
                try {
                    filterChain.doFilter(request, response);
                } catch (Exception e) {
                    response.setStatus(HttpStatus.BAD_REQUEST.value());
                    response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
                    response.getWriter().write(
                            RespuestaHttpFactory
                                    .respuestaError("Debe ingresar un token de autorización",
                                            HttpStatus.BAD_REQUEST.value())
                                    .toString());
                }
                return;
            }

            try {
                nombreUsuario = jwtService.extraerUsername(token);
            } catch (JwtException e) {
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
                response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
                response.getWriter().write(
                        RespuestaHttpFactory
                                .respuestaError("El token ingresado no es válido", HttpStatus.UNAUTHORIZED.value())
                                .toString());
                return;
            }

            if (nombreUsuario != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails details = detailsService.loadUserByUsername(nombreUsuario);

                if (jwtService.validarToken(token, details)) {
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(details,
                            null, details.getAuthorities());

                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    SecurityContextHolder.getContext().setAuthentication(authToken);
                } else {
                    response.setStatus(HttpStatus.BAD_REQUEST.value());
                    response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
                    response.getWriter().write(
                            RespuestaHttpFactory
                                    .respuestaError("El token ha expirado. Por favor genere uno nuevo y vuelva a intentar.", HttpStatus.BAD_REQUEST.value())
                                    .toString());
                    return;
                }
            }

            filterChain.doFilter(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String extraerTokenDeRequest(HttpServletRequest req) {
        String header = req.getHeader(HttpHeaders.AUTHORIZATION);

        if (header != null && header.startsWith("Bearer "))
            return header.replace("Bearer ", "");

        return null;
    }

}
