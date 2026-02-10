package com.listacontatos.jonathan.exceptions;

public class InvalidPhoneNumber extends RuntimeException {
    public InvalidPhoneNumber(String message) {
        super(message);
    }
}
