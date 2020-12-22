package ru.koval.HokkuMaster.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class JwtTokenException extends ResponseStatusException {
    public JwtTokenException() {
        super(HttpStatus.FORBIDDEN, "Token can't be trusted.");
    }
}
