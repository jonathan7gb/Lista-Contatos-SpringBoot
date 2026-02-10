package com.listacontatos.jonathan.exceptions;

public class PhoneNumberAlreadyExists extends RuntimeException {
    public PhoneNumberAlreadyExists(String message) {
        super(message);
    }
}
