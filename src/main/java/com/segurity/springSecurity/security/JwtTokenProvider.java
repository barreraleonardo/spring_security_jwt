package com.segurity.springSecurity.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenProvider {

    //Metodo para generar token por medio de la autenticacion
    public String generarToken(Authentication authentication){
        String username = authentication.getName();
        Date date = new Date();
        Date expirationDate = new Date(date.getTime() + ConstSecurity.JWT_EXPIRATION_TOKEN);

        //Creacion y configuracion de token
        String token = Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS512, ConstSecurity.JWT_FIRMA)
                .compact();
        return token;
    }

    //Metodo para extraer el username del token
    public String obtenerUsernameDeJwt(String token){
        Claims claims = Jwts.parser()
                .setSigningKey(ConstSecurity.JWT_FIRMA)
                .parseClaimsJwt(token)
                .getBody();
        return claims.getSubject();
    }

    //Metodo para validar el token
    public Boolean validarToken(String token){
        try {
            Jwts.parser().setSigningKey(ConstSecurity.JWT_FIRMA).parseClaimsJws(token);
            return true;
        }catch (Exception e){
            throw new AuthenticationCredentialsNotFoundException("Token expirado o invalido");
        }
    }
}
