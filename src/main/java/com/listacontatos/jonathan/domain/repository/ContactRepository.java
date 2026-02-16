package com.listacontatos.jonathan.domain.repository;

public interface ContactRepository {

    boolean existsByPhoneNumberAndIdNot(String phoneNumber, Long id);
    boolean existsByPhoneNumber(String phoneNumber);
}
