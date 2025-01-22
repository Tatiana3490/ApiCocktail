package com.example.apicocktail.repository;

import com.example.apicocktail.domain.Review;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ReviewRepository  extends CrudRepository<Review, Long> {

    //metodo para buscar todas las reseñas
    List<Review> findAll();

    //metodo para buscar por el autor de la reseña
    List<Review> findByReviewer(String reviewer);

    //metodo para buscar por la fecha de la reseña
    List<Review> findByReviewDate(LocalDateTime reviewDate);

    //metodo para buscar por autor y fecha de la reseña
    List<Review> findByReviewerAndReviewDate(String reviewer, LocalDateTime reviewDate);

    //metodo para buscar la reseña entre fechas
    List<Review> findByReviewDateBetween(LocalDateTime reviewDate1, LocalDateTime reviewDate2);

}
