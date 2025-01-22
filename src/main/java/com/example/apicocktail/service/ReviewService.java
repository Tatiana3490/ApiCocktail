package com.example.apicocktail.service;

import com.example.apicocktail.domain.Ingredient;
import com.example.apicocktail.domain.Location;
import com.example.apicocktail.domain.Review;
import com.example.apicocktail.exception.IngredientNotFoundException;
import com.example.apicocktail.exception.LocationNotFoundException;
import com.example.apicocktail.exception.ReviewNotFoundException;
import com.example.apicocktail.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;

    @Autowired
    public ReviewService(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    //Para obtener todas las reseñas
    public List<Review> getAllReviews() {
        return reviewRepository.findAll();
    }

    //Para obtener las reseñas por autor
    public List<Review> getReviewsByReviewer(String reviewer) {
        return reviewRepository.findByReviewer(reviewer);
    }

    //Para obtener las reseñas por fecha
    public List<Review> getReviewsByReviewDate(LocalDateTime reviewDate) {
        return reviewRepository.findByReviewDate(reviewDate);
    }

    //Para obtener las reseñas por autor y fecha
    public List<Review> getReviewsByReviewDateAndReviewer(LocalDateTime reviewDate, String reviewer) {
        return reviewRepository.findByReviewerAndReviewDate(reviewer, reviewDate);
    }

    //Para obtener las reseñas entre dos fechas
    public List<Review> getReviewsByReviewDateBetween(LocalDateTime reviewDate1, LocalDateTime reviewDate2) {
        return reviewRepository.findByReviewDateBetween(reviewDate1, reviewDate2);
    }


    //Para guardar una reseña nueva
    public Review saveReview(Review review) {
        return reviewRepository.save(review);
    }


    //Para actualizar una reseña  por id
    public Review updateReview(Long id, Review reviewDetails) {
        Review existingReview = reviewRepository.findById(id)
                .orElseThrow(() -> new ReviewNotFoundException("Review not found with id: " + id));

        //Actualizar los campos de la reseña existentes con los nuevos valores
        existingReview.setId(reviewDetails.getId());
        existingReview.setCocktail(reviewDetails.getCocktail());
        existingReview.setRating(reviewDetails.getRating());
        existingReview.setUsername(reviewDetails.getUsername());
        existingReview.setComment(reviewDetails.getComment());
        existingReview.setCreatedAt(reviewDetails.getCreatedAt());


        return reviewRepository.save(existingReview);
    }

    public Review updateReviewPartial(Long id, Map<String, Object> updates) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Review not found by id: " + id));

        updates.forEach((key, value) -> {
            Field field = ReflectionUtils.findField(Review.class, key);
            if (field != null) {
                field.setAccessible(true);
                ReflectionUtils.setField(field, review, value);
            }
        });

        return reviewRepository.save(review);
    }

    //Para eliminar una reseña  por id
    public void deleteReview(Long id) throws ReviewNotFoundException{
        if (!reviewRepository.existsById(id)) {
            throw new ReviewNotFoundException("Review not found by id: " + id);
        }
        reviewRepository.deleteById(id);
    }

    //Para obtener una reseña por Id
    public Review getReviewById(Long id){
        return reviewRepository.findById(id)
                .orElseThrow(() -> new ReviewNotFoundException("Review not found by id: " + id));
    }
}



