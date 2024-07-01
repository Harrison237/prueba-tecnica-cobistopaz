package com.cobistopaz.prueba_tecnica.application.security;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.stereotype.Component;
import com.cobistopaz.prueba_tecnica.domain.security.IAuthManager;
import com.cobistopaz.prueba_tecnica.domain.web.factorias.RespuestaHttpFactory;

import java.util.regex.Matcher;

@Component
public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    private final IAuthManager authManager;
    private final UserDetailsService userDetailsService;

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, IAuthManager authManager,
            UserDetailsService userDetailsService) {
        super(authenticationManager);
        this.authManager = authManager;
        this.userDetailsService = userDetailsService;
    }

    private static final List<String> URL_EXCLUIDAS = Arrays.asList(
            "/swagger-resources",
            "/webjars/*",
            "/swagger-ui",
            "/v3/api-docs",
            "/v3/api-docs",
            "/api/auth/login");

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        final String tokenHeader = request.getHeader("Authorization");

        String username = null;
        String token = null;

        if (tokenHeader == null) {
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
            response.getWriter().write(
                    RespuestaHttpFactory
                            .respuestaError("Debe ingresar un token de autorización", HttpStatus.BAD_REQUEST.value())
                            .toString());
            return;
        }

        try {
            token = tokenHeader.replace("Bearer ", "");
            username = authManager.extraerData(token).getNombreUsuario();
        } catch (Exception e) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
            response.getWriter().write(
                    RespuestaHttpFactory
                            .respuestaError("El token ingresado no es válido", HttpStatus.UNAUTHORIZED.value())
                            .toString());
            return;
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.getAuthorities());
            usernamePasswordAuthenticationToken
                    .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        }
        chain.doFilter(request, response);
    }

    // Método para que este filtro permita utilizar Swagger. Fue la mejor manera que
    // se encontró para hacerlo ya que sigue haciendo parte de la lógica encapsulada
    // de la clase
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String path = request.getRequestURI();

        // Comparación de URLs mediante expresiones regulares
        for (String pattern : URL_EXCLUIDAS) {
            Pattern pat = Pattern.compile(pattern);
            Matcher match = pat.matcher(path);

            if (match.find()) {
                return true;
            }
        }

        return false;
    }
}
