package ru.koval.HokkuMaster.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpStatusCodeException;

public class AuthenticationRequestParseException extends HttpStatusCodeException {
    public AuthenticationRequestParseException() {
        super(HttpStatus.BAD_REQUEST, "Authentication request parse exception.");
    }
}
