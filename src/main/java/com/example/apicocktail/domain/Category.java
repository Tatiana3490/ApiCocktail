package com.example.apicocktail.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "Category")
@Table(name="category")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Lob
    private String description; // Breve descripción de la categoría

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true) // Relación (1,N)
    private List<Cocktail> cocktails;
}
