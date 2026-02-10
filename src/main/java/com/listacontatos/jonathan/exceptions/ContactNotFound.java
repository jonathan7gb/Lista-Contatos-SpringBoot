package com.listacontatos.jonathan.exceptions;

public class ContactNotFound extends RuntimeException {
    public ContactNotFound(String message) {
        super(message);
    }
}
