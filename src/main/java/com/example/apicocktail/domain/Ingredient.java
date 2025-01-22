package com.example.apicocktail.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "Ingredient")
@Table(name="ingredient")
public class Ingredient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;
    @Column
    private String description;
    @Column
    private int quantity;
    @Column
    private String unit;

    @ManyToMany(mappedBy = "ingredients") // Relación inversa definida en Cocktail
    private Set<Cocktail> cocktails;
}
