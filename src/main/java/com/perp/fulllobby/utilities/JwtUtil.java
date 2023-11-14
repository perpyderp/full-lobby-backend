package com.perp.fulllobby.utilities;

import org.springframework.stereotype.Component;

import io.github.cdimascio.dotenv.Dotenv;
import io.jsonwebtoken.*;

@Component
public class JwtUtil {

    private static final String SECRET = Dotenv.configure().load().get("DB_URL");

    public static String generateToken(String username) {
        long expirationTime = 864_000_000; // 10 days in milliseconds

        return Jwts.builder()
            .signWith(null)
                .compact();
    }


}
