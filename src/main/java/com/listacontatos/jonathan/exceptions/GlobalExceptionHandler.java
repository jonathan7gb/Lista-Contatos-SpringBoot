package com.listacontatos.jonathan.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler extends RuntimeException {

    @ExceptionHandler(ContactNotFound.class)
    public ResponseEntity<String> handleContactNotFound(ContactNotFound ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(ContactDataIsNull.class)
    public ResponseEntity<String> handleContactDataIsNull(ContactDataIsNull ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @ExceptionHandler(InvalidPhoneNumber.class)
    public ResponseEntity<String> handleInvalidPhone(InvalidPhoneNumber ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @ExceptionHandler(PhoneNumberAlreadyExists.class)
    public ResponseEntity<String> handlePhoneAlreadyExists(PhoneNumberAlreadyExists ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }


}
