package com.listacontatos.jonathan.service;

import com.listacontatos.jonathan.dto.ContactRequestDTO;
import com.listacontatos.jonathan.dto.ContactResponseDTO;
import com.listacontatos.jonathan.exceptions.ContactDataIsNull;
import com.listacontatos.jonathan.exceptions.ContactNotFound;
import com.listacontatos.jonathan.exceptions.InvalidPhoneNumber;
import com.listacontatos.jonathan.exceptions.PhoneNumberAlreadyExists;
import com.listacontatos.jonathan.mapper.ContactMapper;
import com.listacontatos.jonathan.model.Contact;
import com.listacontatos.jonathan.repository.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ContactService {

    @Autowired
    private ContactRepository contactRepository;

    @Autowired
    private ContactMapper contactMapper;

    public ContactResponseDTO save(ContactRequestDTO requestDTO) {
        if(requestDTO.name() == null || requestDTO.name().isBlank()){
            throw new ContactDataIsNull("O nome do contato não pode ser nulo");
        }

        if(requestDTO.phoneNumber() == null || requestDTO.phoneNumber().isBlank()){
            throw new ContactDataIsNull("O número de telefone do contato não pode ser nulo");
        }

        if(contactRepository.existsByPhoneNumber(requestDTO.phoneNumber())){
            throw new PhoneNumberAlreadyExists("Este número de telefone já existe");
        }

        if(requestDTO.phoneNumber().length() > 15){
            throw new InvalidPhoneNumber("O número de telefone não pode ter mais do que 15 caracteres!");
        }

        Contact contactSaved = contactRepository.save(contactMapper.toEntity(requestDTO));

        return contactMapper.toDTO(contactSaved);
    }

    public List<ContactResponseDTO> findAll(){
        List<Contact> contactList = contactRepository.findAll();
        List<ContactResponseDTO> contactListDTO = new ArrayList<>();

        if(contactList.isEmpty()){
            throw new ContactNotFound("Nenhum contato encontrado!");
        }


        for(Contact c : contactList){
            contactListDTO.add(contactMapper.toDTO(c));
        }

        return contactListDTO;
    }

    public ContactResponseDTO findById(Long id){
        Contact contact = contactRepository.findById(id)
                .orElseThrow(() -> {
                    throw new ContactNotFound("Nenhum contato encontrado!");
                });


        return contactMapper.toDTO(contact);
    }

    public ContactResponseDTO update(Long id, ContactRequestDTO contactDTO) {
        Contact contactFind = contactRepository.findById(id).orElseThrow(() -> new ContactNotFound("Nenhum contato encontrado!") );

        if(contactDTO.name() == null || contactDTO.name().isBlank()){
            throw new ContactDataIsNull("O nome do contato não pode ser nulo");
        }

        if(contactDTO.phoneNumber() == null || contactDTO.phoneNumber().isBlank()){
            throw new ContactDataIsNull("O número de telefone do contato não pode ser nulo");
        }

        if(contactRepository.existsByPhoneNumberAndIdNot(contactDTO.phoneNumber(), id)){
            throw new PhoneNumberAlreadyExists("Este número de telefone já existe");
        }

        if(contactDTO.phoneNumber().length() > 15){
            throw new InvalidPhoneNumber("O número de telefone não pode ter mais do que 15 caracteres!");
        }

        contactFind.setName(contactDTO.name());
        contactFind.setPhoneNumber(contactDTO.phoneNumber());

        Contact contact = contactRepository.save(contactFind);
        return contactMapper.toDTO(contact);
    }

    public void delete(Long id){
        Contact contactFind = contactRepository.findById(id).orElseThrow(() -> new ContactNotFound("Nenhum contato encontrado!") );

        contactRepository.deleteById(id);
    }
}
