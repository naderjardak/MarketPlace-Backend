package tn.workbot.coco_marketplace.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import tn.workbot.coco_marketplace.entities.Product;
import tn.workbot.coco_marketplace.entities.Review;
import tn.workbot.coco_marketplace.entities.User;
import tn.workbot.coco_marketplace.entities.enmus.ReviewEmotionStatus;
import tn.workbot.coco_marketplace.services.ReviewService;
import tn.workbot.coco_marketplace.services.interfaces.ProductInterface;

import java.util.List;


@RestController
@RequestMapping("Review")
public class ReviewController {

    @Autowired
    ReviewService reviewService;

    @Autowired
    ProductInterface productInterface;

    @GetMapping("Getsession")
    public User getUserSession(){return  this.reviewService.getUserSession();}
    @GetMapping("GetProductById")
    public Product getById(@RequestParam Long id) {
        return productInterface.getById(id);
    }
    @GetMapping("GetAllReviews")
    public List<Review> getAllReviews(){
       return reviewService.getAllReviews();
    }

    @GetMapping("GetReviewById")
    public Review getReviewById(@RequestParam Long id){
        return reviewService.getReviewById(id);
    }

    @PostMapping("AddReview")
    public void addReview(@RequestBody Review review,@RequestParam Long id,@RequestParam int rating){
        reviewService.addReview(review,id,rating);
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


    @GetMapping("ClassifyReviewByDateAndEmotions")
    public List<Review> ClassifyReviewsByDateAndEmotons(@RequestParam ReviewEmotionStatus emotion){
        return reviewService.ClassifyReviewsByDateAndEmotons(emotion);
    }



}
