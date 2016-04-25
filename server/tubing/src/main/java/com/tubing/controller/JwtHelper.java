package com.tubing.controller;

import com.tubing.logic.google.SecretsContainer;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

public class JwtHelper {

    private static final String SESSION = "session";

    public static String build(String subject, String session) {

        return Jwts.builder().setSubject(subject).claim(
                SESSION, session).setIssuedAt(new Date()).signWith(
                SignatureAlgorithm.HS256, SecretsContainer.getJwt()).
                compact();
    }

    // This will throw an exception if it is not a signed JWS (as expected)
    public static String getSession(String token) {

        final Claims claims = Jwts.parser().setSigningKey(SecretsContainer.getJwt()).parseClaimsJws(token).getBody();

        return (String) claims.get(SESSION);
    }
}
