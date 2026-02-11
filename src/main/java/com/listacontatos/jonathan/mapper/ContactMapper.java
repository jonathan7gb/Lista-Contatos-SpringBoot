package com.listacontatos.jonathan.mapper;

import com.listacontatos.jonathan.dto.ContactRequestDTO;
import com.listacontatos.jonathan.dto.ContactResponseDTO;
import com.listacontatos.jonathan.model.Contact;
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
