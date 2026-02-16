package com.listacontatos.jonathan.infra.exceptions;

public class PhoneNumberAlreadyExists extends RuntimeException {
    public PhoneNumberAlreadyExists(String message) {
        super(message);
    }
}
