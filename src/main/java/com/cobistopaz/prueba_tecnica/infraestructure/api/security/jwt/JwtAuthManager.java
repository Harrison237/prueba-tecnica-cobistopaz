package com.cobistopaz.prueba_tecnica.infraestructure.api.security.jwt;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.cobistopaz.prueba_tecnica.domain.exceptions.TokenInvalidoException;
import com.cobistopaz.prueba_tecnica.domain.model.Credenciales;
import com.cobistopaz.prueba_tecnica.domain.security.IAuthManager;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;

@Component
public class JwtAuthManager implements IAuthManager {

    private static final String secret = "CONFIGURAR_UN_SECRET_MAS_SEGURO_aaaaaaaaaaaaaaaaa";
    private static final Long expiraEn = 604800000L;

    @Override
    public String generarToken(Credenciales credenciales) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("nombreUsuario", credenciales.getNombreUsuario());
        claims.put("contrasena", credenciales.getContrasena());

        return Jwts.builder()
                .subject(credenciales.getNombreUsuario())
                .expiration(new Date(System.currentTimeMillis() + expiraEn))
                .claims(claims)
                .signWith(Keys.hmacShaKeyFor(secret.getBytes()))
                .compact();
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean validarToken(String token) {
        try {
            Jwts.parser().setSigningKey(secret).build().parseSignedClaims(token).getPayload();
            return true;
        } catch (SignatureException e) {
            return false;
        }
    }

    @SuppressWarnings("deprecation")
    @Override
    public Credenciales extraerData(String token) throws Exception {
        try {
            Claims claims = Jwts.parser().setSigningKey(secret.getBytes()).build().parseSignedClaims(token)
                    .getPayload();

            Credenciales credenciales = new Credenciales((String) claims.get("nombreUsuario"),
                    (String) claims.get("contrasena"));

            return credenciales;
        } catch (JwtException e) {
            throw new TokenInvalidoException("El token no es valido");
        }
    }

}
