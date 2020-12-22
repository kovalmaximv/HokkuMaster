package ru.koval.HokkuMaster.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import ru.koval.HokkuMaster.dto.AuthenticationRequest;
import ru.koval.HokkuMaster.exception.AuthenticationRequestParseException;

import javax.crypto.SecretKey;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final Logger logger = LogManager.getLogger(JwtAuthenticationFilter.class);

    private final AuthenticationManager authenticationManager;
    private final JwtProperties jwtProperties;
    private final SecretKey jwtSecretKey;

    public JwtAuthenticationFilter(
            AuthenticationManager authenticationManager,
            JwtProperties jwtProperties,
            SecretKey jwtSecretKey) {
        this.authenticationManager = authenticationManager;
        this.jwtProperties = jwtProperties;
        this.jwtSecretKey = jwtSecretKey;
    }

    @Override
    public Authentication attemptAuthentication(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws AuthenticationException {
        try {
            AuthenticationRequest authenticationRequest = new ObjectMapper()
                    .readValue(request.getInputStream(), AuthenticationRequest.class);

            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    authenticationRequest.getUsername(),
                    authenticationRequest.getPassword()
            );

            return authenticationManager.authenticate(authentication);
        } catch (IOException e) {
            logger.error("Request parse exception.");
            throw new AuthenticationRequestParseException();
        }
    }

    @Override
    protected void successfulAuthentication(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain,
            Authentication authResult
    ) throws IOException, ServletException {
        String token = Jwts.builder()
                .setSubject(authResult.getName())
                .claim(jwtProperties.getAUTHORITY_CLAIM(), authResult.getAuthorities())
                .setIssuedAt(new Date())
                .setExpiration(
                        Date.from(
                                LocalDateTime.now()
                                        .plusMinutes(jwtProperties.getTokenExpirationAfterMinutes())
                                        .atZone(ZoneId.systemDefault())
                                        .toInstant()
                        )
                )
                .signWith(jwtSecretKey)
                .compact();

        response.setHeader(HttpHeaders.AUTHORIZATION, token);
    }
}
