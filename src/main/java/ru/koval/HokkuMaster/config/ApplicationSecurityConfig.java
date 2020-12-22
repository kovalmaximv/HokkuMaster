package ru.koval.HokkuMaster.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import ru.koval.HokkuMaster.security.jwt.JwtAuthenticationFilter;
import ru.koval.HokkuMaster.security.jwt.JwtProperties;
import ru.koval.HokkuMaster.security.jwt.JwtTokenVerifierFilter;

import javax.crypto.SecretKey;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {
    private final String[] excludePaths = new String[] {"/", "index", "/css/*", "/js/*"};

    private final JwtProperties jwtProperties;
    private final SecretKey secretKey;

    @Autowired
    public ApplicationSecurityConfig(JwtProperties jwtProperties, SecretKey secretKey) {
        this.jwtProperties = jwtProperties;
        this.secretKey = secretKey;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilter(new JwtAuthenticationFilter(authenticationManager(), jwtProperties, secretKey))
                .addFilterAfter(new JwtTokenVerifierFilter(jwtProperties, excludePaths), JwtAuthenticationFilter.class)
                .authorizeRequests()
                    .antMatchers(excludePaths).permitAll()
                    .anyRequest().authenticated();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // ToDo write my own implementation of auth manager
        super.configure(auth);
    }
}
