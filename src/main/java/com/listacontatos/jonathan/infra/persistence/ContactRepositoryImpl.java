package com.listacontatos.jonathan.infra.persistence;

import com.listacontatos.jonathan.domain.entity.Contact;
import com.listacontatos.jonathan.domain.repository.ContactRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactRepositoryImpl extends JpaRepository<Contact, Long>, ContactRepository {

    boolean existsByPhoneNumberAndIdNot(String phoneNumber, Long id);
    boolean existsByPhoneNumber(String phoneNumber);

}
