package com.example.apicocktail.domain;


import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "User")
@Table(name="users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true) // Nombre de usuario único y obligatorio
    private String username;
    @Column(nullable = false) // Contraseña obligatoria
    private String password;
    @Column(nullable = false) // Nombre obligatorio
    private String name;
    @Column(nullable = false) // Apellido obligatorio
    private String surname;
    @Column(nullable = false, unique = true) // Email único y obligatorio
    private String email;
    @Column(name = "creation_date", nullable = false, updatable = false) // Fecha obligatoria y no modificable
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate creationDate;

    @Column(columnDefinition = "BOOLEAN DEFAULT TRUE") // Valor predeterminado en la base de datos
    private Boolean active;

}
