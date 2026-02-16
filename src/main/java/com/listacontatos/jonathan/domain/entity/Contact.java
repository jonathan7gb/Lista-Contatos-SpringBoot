package com.listacontatos.jonathan.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="contact")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Contact {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    @Size(min = 3, max = 35, message = "Nome inválido!")
    @NotNull(message = "O nome não pode ser nulo!")
    private String name;

    @Column(nullable = false, length = 15, unique = true)
    @Size(min = 15, max = 15, message = "Número de telefone inválido!")
    @NotNull(message = "O telefone não pode ser nulo!")
    private String phoneNumber;

    public Contact(String name, String phoneNumber){
        this.name = name;
        this.phoneNumber = phoneNumber;
    }
}
