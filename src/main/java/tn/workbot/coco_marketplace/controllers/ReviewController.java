package tn.workbot.coco_marketplace.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tn.workbot.coco_marketplace.entities.Review;
import tn.workbot.coco_marketplace.services.ReviewService;

import java.util.List;


@RestController
@RequestMapping("Review")
public class ReviewController {

    @Autowired
    ReviewService reviewService;

    @GetMapping("GetAllReviews")
    public List<Review> getAllReviews(){
       return reviewService.getAllReviews();
    }

    @GetMapping("GetReviewById")
    public Review getReviewById(@RequestParam Long id){
        return reviewService.getReviewById(id);
    }

    @PostMapping("AddReview")
    public void addReview(@RequestBody Review review,@RequestParam Long id){
        reviewService.addReview(review,id);
    }

    @PutMapping("UpdateReview")
    public void updateReview(@RequestBody Review review){
        reviewService.updateReview(review);
    }

    @DeleteMapping("DeleteReview")
    public void deleteReview(@RequestParam Long id){
        reviewService.deleteReview(id);
    }

    @PutMapping("rating")
    public float calculateProductRating(@RequestParam Long productId, @RequestParam int rating)
    {
        return reviewService.calculateProductRating(productId, rating);
    }


}
