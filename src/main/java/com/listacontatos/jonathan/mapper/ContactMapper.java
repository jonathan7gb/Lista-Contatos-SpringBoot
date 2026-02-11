package com.listacontatos.jonathan.mapper;

import com.listacontatos.jonathan.dto.ContactResponseDTO;
import com.listacontatos.jonathan.model.Contact;
import org.springframework.stereotype.Component;

@Component
public class ContactMapper {

    public Contact toEntity(ContactResponseDTO contactResponseDTO){
        return new Contact(contactResponseDTO.name(), contactResponseDTO.phoneNumber());
    }

    public ContactResponseDTO toDTO(Contact contact){
        return new ContactResponseDTO(contact.getId(),contact.getName(), contact.getPhoneNumber());
    }
}
