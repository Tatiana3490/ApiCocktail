package com.example.apicocktail.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



import java.time.LocalDate;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "Cocktail")
@Table(name = "cocktail")
public class Cocktail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String name;
    @Column
    private String description;
    @Column
    private String category;
    @Column(name="release_date")
    private LocalDate releaseDate;
    @Column(name = "degrees", nullable = false)
    private double degrees;
    @Column(name = "latitude", nullable = false)
    private double latitude;
    @Column(name = "longitude", nullable = false)
    private double longitude;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false) // Clave foránea hacia User
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false) // Clave foránea hacia Category


    @ManyToMany
    @JoinTable(
            name = "cocktail_ingredient", // Nombre de la tabla intermedia
            joinColumns = @JoinColumn(name = "cocktail_id"), // Clave foránea hacia Cocktail
            inverseJoinColumns = @JoinColumn(name = "ingredient_id") // Clave foránea hacia Ingredient
    )
    private Set<Ingredient> ingredients;

    @OneToOne
    @JoinColumn(name = "location_id", referencedColumnName = "id")
    private Location location;

}





