package ru.koval.HokkuMaster.security.jwt;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "application.jwt")
public class JwtProperties {
    private final String AUTHORITY_CLAIM = "authorities";

    private String secret;
    private Integer tokenExpirationAfterMinutes;

    public String getSecret() {
        return secret;
    }

    public Integer getTokenExpirationAfterMinutes() {
        return tokenExpirationAfterMinutes;
    }

    public String getAUTHORITY_CLAIM() {
        return AUTHORITY_CLAIM;
    }
}
