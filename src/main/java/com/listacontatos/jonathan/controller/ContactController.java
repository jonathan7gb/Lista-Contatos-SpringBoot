package com.listacontatos.jonathan.controller;

import com.listacontatos.jonathan.model.Contact;
import com.listacontatos.jonathan.repository.ContactRepository;
import com.listacontatos.jonathan.service.ContactService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/contacts")
public class ContactController {

    @Autowired
    private ContactRepository contactRepository;

    @Autowired
    private ContactService contactService;

    @PostMapping
    public ResponseEntity<Void> save(@RequestBody Contact contact){
        Contact contactSaved = contactService.save(contact);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public ResponseEntity<List<Contact>> findAll(){
        List<Contact> contactList = contactService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(contactList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Contact> findById(@PathVariable Long id){
        return ResponseEntity.ok(contactService.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Contact> update(@PathVariable Long id, @RequestBody Contact contact){
        return ResponseEntity.ok(contactService.update(id, contact));
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> delete(@PathVariable Long id){
        contactRepository.deleteById(id);
        return ResponseEntity.noContent().build();

    }
}
