package com.listacontatos.jonathan.application.mapper;

import com.listacontatos.jonathan.application.dto.ContactRequestDTO;
import com.listacontatos.jonathan.application.dto.ContactResponseDTO;
import com.listacontatos.jonathan.domain.entity.Contact;
import org.springframework.stereotype.Component;

@Component
public class ContactMapper {

    public Contact toEntity(ContactRequestDTO requestDTO){
        return new Contact(requestDTO.name(), requestDTO.phoneNumber());
    }

    public ContactResponseDTO toDTO(Contact contact){
        return new ContactResponseDTO(contact.getId(),contact.getName(), contact.getPhoneNumber());
    }
}
