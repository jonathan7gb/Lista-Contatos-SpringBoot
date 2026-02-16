package com.listacontatos.jonathan.infra.exceptions;

public class ContactNotFound extends RuntimeException {
    public ContactNotFound(String message) {
        super(message);
    }
}
