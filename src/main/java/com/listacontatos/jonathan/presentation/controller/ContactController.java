package com.listacontatos.jonathan.presentation.controller;

import com.listacontatos.jonathan.application.dto.ContactRequestDTO;
import com.listacontatos.jonathan.application.dto.ContactResponseDTO;
import com.listacontatos.jonathan.infra.persistence.ContactRepositoryImpl;
import com.listacontatos.jonathan.application.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/contacts")
public class ContactController {

    @Autowired
    private ContactRepositoryImpl contactRepositoryImpl;

    @Autowired
    private ContactService contactService;

    @PostMapping
    public ResponseEntity<Void> save(@RequestBody ContactRequestDTO requestDTO){
        ContactResponseDTO contactSaved = contactService.save(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public ResponseEntity<List<ContactResponseDTO>> findAll(){
        List<ContactResponseDTO> contactList = contactService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(contactList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContactResponseDTO> findById(@PathVariable Long id){
        return ResponseEntity.ok(contactService.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ContactResponseDTO> update(@PathVariable Long id, @RequestBody ContactRequestDTO contactDTO){
        return ResponseEntity.ok(contactService.update(id, contactDTO));
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> delete(@PathVariable Long id){
        contactRepositoryImpl.deleteById(id);
        return ResponseEntity.noContent().build();

    }
}
