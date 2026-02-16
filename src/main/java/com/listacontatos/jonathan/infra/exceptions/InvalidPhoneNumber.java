package com.listacontatos.jonathan.infra.exceptions;

public class InvalidPhoneNumber extends RuntimeException {
    public InvalidPhoneNumber(String message) {
        super(message);
    }
}
