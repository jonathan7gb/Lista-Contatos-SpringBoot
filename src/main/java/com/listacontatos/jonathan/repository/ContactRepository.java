package com.listacontatos.jonathan.repository;

import com.listacontatos.jonathan.model.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Long> {

    boolean existsByPhoneNumberAndIdNot(String phoneNumber, Long id);
    boolean existsByPhoneNumber(String phoneNumber);

}
