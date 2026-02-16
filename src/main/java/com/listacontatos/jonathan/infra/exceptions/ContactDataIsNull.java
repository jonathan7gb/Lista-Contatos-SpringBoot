package com.listacontatos.jonathan.infra.exceptions;

public class ContactDataIsNull extends RuntimeException {
    public ContactDataIsNull(String message) {
        super(message);
    }
}
