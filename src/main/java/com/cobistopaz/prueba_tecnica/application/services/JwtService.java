package com.cobistopaz.prueba_tecnica.application.services;

import java.security.Key;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.cobistopaz.prueba_tecnica.infraestructure.api.security.IAuthManager;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService implements IAuthManager {

    private static final String SECRET = "123521342352135454324123asdfasd134351341352351";
    private static final Long expiracion = 1000 * 60 * 60 * 24 * 7L;

    @Override
    public String generarToken(UserDetails credenciales) throws Exception {
        return nuevoToken(new HashMap<>(), credenciales);
    }

    @Override
    public boolean validarToken(String token, UserDetails details) throws Exception {
        String nombreUsuario = extraerUsername(token);
        return nombreUsuario.equals(details.getUsername()) && !(expirationTokenDate(token).before(new Date()));
    }

    @Override
    public String extraerUsername(String token) throws Exception {
        return (String) getClaim(token, Claims.SUBJECT);
    }

    private String nuevoToken(Map<String, Object> claimsExtra, UserDetails usuario) {
        return Jwts
                .builder()
                .claims(claimsExtra)
                .subject(usuario.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expiracion))
                .signWith(generarKey())
                .compact();
    }

    private Key generarKey() {
        byte[] secretBytes = Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(secretBytes);
    }

    @SuppressWarnings("deprecation")
    private Claims allClaims(String token) {
        return (Claims) Jwts
                .parser()
                .setSigningKey(generarKey())
                .build()
                .parse(token)
                .getPayload();
    }

    @SuppressWarnings("unchecked")
    public <T> T getClaim(String token, String key) {
        return (T) allClaims(token).get(key);
    }

    private Date expirationTokenDate(String token) {
        Long time = (Long) getClaim(token, Claims.EXPIRATION);
        Date fecha = Date.from(Instant.ofEpochSecond(time.longValue()));
        return fecha;
    }
}
