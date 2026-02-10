package com.listacontatos.jonathan.service;

import com.listacontatos.jonathan.exceptions.ContactDataIsNull;
import com.listacontatos.jonathan.exceptions.ContactNotFound;
import com.listacontatos.jonathan.exceptions.InvalidPhoneNumber;
import com.listacontatos.jonathan.exceptions.PhoneNumberAlreadyExists;
import com.listacontatos.jonathan.model.Contact;
import com.listacontatos.jonathan.repository.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ContactService {

    @Autowired
    private ContactRepository contactRepository;

    public Contact save(Contact contact) {
        if(contact.getName() == null || contact.getName().isBlank()){
            throw new ContactDataIsNull("O nome do contato não pode ser nulo");
        }

        if(contact.getPhoneNumber() == null || contact.getPhoneNumber().isBlank()){
            throw new ContactDataIsNull("O número de telefone do contato não pode ser nulo");
        }

        if(contactRepository.existsByPhoneNumber(contact.getPhoneNumber())){
            throw new PhoneNumberAlreadyExists("Este número de telefone já existe");
        }

        if(contact.getPhoneNumber().length() > 15){
            throw new InvalidPhoneNumber("O número de telefone não pode ter mais do que 15 caracteres!");
        }


        return contactRepository.save(contact);
    }

    public List<Contact> findAll(){
        List<Contact> contactList = contactRepository.findAll();

        if(contactList.isEmpty()){
            throw new ContactNotFound("Nenhum contato encontrado!");
        }

        return contactList;
    }

    public Contact findById(Long id){
        return contactRepository.findById(id).orElseThrow(() -> new ContactNotFound("Nenhum contato encontrado!") );

    }
}
