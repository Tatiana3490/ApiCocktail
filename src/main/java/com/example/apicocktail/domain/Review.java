package com.example.apicocktail.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "Review")
@Table(name="review")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User username; //usuario que dejo la reseña

    @ManyToOne
    private Cocktail cocktail; //coctel al que pertenece la reseña

    @Column(nullable = false)
    private Integer rating;

    @Lob
    private String comment; //comentario opcional

    private LocalDateTime createdAt = LocalDateTime.now();
}
