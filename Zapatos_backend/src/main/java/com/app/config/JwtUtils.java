package com.app.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import org.springframework.security.core.Authentication;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class JwtUtils {

    private String Privatekey ="4feb2db26935f2c67d976f653348c35145ee0f759a4934476435c3840112c8b4";

    @Value("${security.jwt.user.generator}")
    private String UserGenerator;


    public String createToken(Authentication authentication) {
        Algorithm algorithm = Algorithm.HMAC256(this.Privatekey);
        String username = authentication.getPrincipal().toString();
        String authorities = authentication.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));
        String jwtToken = JWT.create()
                .withIssuer(this.UserGenerator)
                .withSubject(username)
                .withClaim("permisos",authorities)
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis()+180000))
                .withJWTId(UUID.randomUUID().toString())
                .withNotBefore(new Date(System.currentTimeMillis()))
                .sign(algorithm);
        return jwtToken;
        //
    }

    public DecodedJWT validarToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(this.Privatekey);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer(this.UserGenerator)
                    .build();
            verifier.verify(token);
            DecodedJWT jwt = verifier.verify(token);
            return jwt;
        }catch(JWTVerificationException e) {
            throw new JWTVerificationException("Token invalido, sin autorización");
        }
    }

    public String extractUsername(DecodedJWT decodificador) {
        return decodificador.getSubject().toString();
    }

    public Claim getSpecificClaim(DecodedJWT jwt, String claimName){
        return jwt.getClaim(claimName);
    }

    public Map<String , Claim> returnClaims(DecodedJWT jwt){
        return jwt.getClaims();
    }
}
