package br.com.psicoclinic.Exceptions;

import javax.naming.AuthenticationException;

public class InvalidJwtAuthenticationToken extends RuntimeException {
    public InvalidJwtAuthenticationToken(String msg) {
        super(msg);
    }
}
