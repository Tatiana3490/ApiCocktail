package com.example.apicocktail.controller;

import com.example.apicocktail.domain.Cocktail;
import com.example.apicocktail.domain.Location;
import com.example.apicocktail.domain.Review;
import com.example.apicocktail.exception.LocationNotFoundException;
import com.example.apicocktail.exception.ReviewNotFoundException;
import com.example.apicocktail.service.CocktailService;
import com.example.apicocktail.service.ReviewService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/review")
public class ReviewController {

    private final Logger logger = LoggerFactory.getLogger(ReviewController.class);
    private final ReviewService reviewService;

    @Autowired
    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    //Para obtener todos las reviews
    @GetMapping
    public ResponseEntity<List<Review>> getAllReviews(){
        logger.info("BEGIN getAllReviews");
        List<Review> reviews = reviewService.getAllReviews();
        logger.info("END getAllReviews - Total reviews fetched: {}", reviews.size());
        return new ResponseEntity<>(reviews, HttpStatus.OK);
    }

    //Agregar una reseña nueva
    @PostMapping
    public ResponseEntity<Review> addReview(@RequestBody Review review){
        logger.info("BEGIN addReview - Adding review: {}", review);
        Review newReview = reviewService.saveReview(review);
        logger.info("END addReview - Review fetched: {}", newReview);
        return new ResponseEntity<>(newReview, HttpStatus.CREATED);

    }

    //Buscar reseñas por autor
    @GetMapping("/reviewer")
    public ResponseEntity<List<Review>> getReviewsByReviewer(@RequestBody Review reviewer){
        logger.info("BEGIN getReviewsByReviewer - Searching rewiews by Reviewer");
        List<Review> reviews = reviewService.getReviewsByReviewer(String.valueOf(reviewer));
        logger.info("END getReviewsByReviewer - Reviews fetched: {}", reviews.size());
        return new ResponseEntity<>(reviews, HttpStatus.OK);
    }

    //Buscar reseñas por fecha
    @GetMapping("/review-date")
    public ResponseEntity<List<Review>> getReviewsByReviewDate(@RequestBody Review reviewDate){
        logger.info("BEGIN getReviewsByReviewDate - Searching reviews by Review Date: {}", reviewDate);
        List<Review> reviews = reviewService.getReviewsByReviewDate(LocalDateTime.parse(String.valueOf(reviewDate)));
        logger.info("END getReviewsByReviewDate - Reviews fetched: {}", reviews.size());
        return new ResponseEntity<>(reviews, HttpStatus.OK);
    }

    //Buscar reseñas por autor y fecha
    @GetMapping("/date-and-reviewer")
    public ResponseEntity<List<Review>> getReviewsByReviewDateAndReviewer(@RequestParam LocalDateTime reviewsDate, @RequestParam String reviewer){
        logger.info("BEGIN getReviewsByReviewDateAndReviewer - Searching reviews by Review Date {} & Reviewer {}", reviewsDate, reviewer);
        List<Review> reviews = reviewService.getReviewsByReviewDateAndReviewer(reviewsDate, reviewer );
        logger.info("END getReviewsByReviewDateAndReviewer - Reviews fetched: {}", reviews.size());
        return new ResponseEntity<>(reviews, HttpStatus.OK);
    }

    //Para reseñas entre dos fechas
    @GetMapping("/range")
    public ResponseEntity<List<Review>> getReviewsByReviewDateBetween(
            @RequestParam LocalDateTime reviewDate1, @RequestParam LocalDateTime reviewDate2){
        logger.info("BEGIN getReviewsByReviewDateBetween - Searching reviews by Review Date between {} and {}", reviewDate1, reviewDate2);
        List<Review> reviews = reviewService.getReviewsByReviewDateBetween(reviewDate1, reviewDate2);
        logger.info("END getReviewsByReviewDateBetween - Reviews fetched: {}", reviews.size());
        return new ResponseEntity<>(reviews, HttpStatus.OK);
    }

    //Para actualizar una reseña por id
    @PutMapping("/{id}")
    public ResponseEntity<Review> updateReview(@PathVariable Long id, @RequestBody Review reviewDetails) throws ReviewNotFoundException {
        logger.info("BEGIN updateReview - Review update by id: {}", id);
        try{
            Review updateReview = reviewService.updateReview(id, reviewDetails);
            logger.info("END updateReview - Review update by id: {}", updateReview.getId());
            return  new ResponseEntity<>(updateReview, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error in updateReview - Review not found by Id: {}", id, e);
            throw e;
        }

    }

    //Para actualizar parcialmente una reseña
    @PatchMapping("/{id}")
    public ResponseEntity<Review> updateReview(@PathVariable Long id, @RequestBody Map<String, Object> updates) {
        logger.info("BEGIN updateReviewPartial - Partially updating review by Id: {}", id);
        try {
            Review updatedReview = reviewService.updateReviewPartial(id, updates);
            logger.info("END updateReviewPartial - Review updated by Id: {}", updatedReview.getId());
            return ResponseEntity.ok(updatedReview);
        } catch (Exception e) {
            logger.error("Error in updateReviewPartial - Review not found by Id: {}", id, e);
            throw e;
        }
    }

    //Para obtener una reseña por id
    @GetMapping("/{id}")
    public ResponseEntity<Review> getReviewById(@PathVariable Long id) throws ReviewNotFoundException {
        logger.info("BEGIN getReviewById - Fetching review by Id: {}", id);
        try {
            Review review = reviewService.getReviewById(id);
            logger.info("END getReviewById - Review found: {}", review.getId());
            return new ResponseEntity<>(review, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error in getReviewById - Review not found by Id: {}", id, e);
            throw e;
        }
    }

    //Para eliminar una reseña por Id
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReview(@PathVariable Long id) throws ReviewNotFoundException{
        logger.info("BEGIN deleteReview - Deleting review by Id: {}", id);
        try {
            reviewService.deleteReview(id);
            logger.info("END deleteReview - Review deleted by Id: {}", id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            logger.error("Error in deleteReview - Review not found by Id: {}", id, e);
            throw e;
        }
    }

    // Manejar excepciones de reseña no encontrada
    @ExceptionHandler(ReviewNotFoundException.class)
    public ResponseEntity<String> handleReviewNotFoundException(ReviewNotFoundException exception) {
        logger.error("Handling ReviewNotFoundException - {}", exception.getMessage(), exception);
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
    }





}
