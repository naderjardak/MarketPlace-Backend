package tn.workbot.coco_marketplace.services.interfaces;

import tn.workbot.coco_marketplace.entities.Review;
import tn.workbot.coco_marketplace.entities.enmus.ReviewEmotionStatus;

import java.util.List;

public interface ReviewInterface {

    List<Review> getAllReviews();
    Review getReviewById(Long id);
    void addReview(Review review,Long id,int rating);
    void updateReview(Review review);
    void deleteReview(Long id);

    public float calculateProductRating(Long productId, int rating);

    void AddReviewOnDelivery(Review review, Long id);

    public ReviewEmotionStatus detectReviewEmotion(String reviewText);

    List<Review> ClassifyReviewsByDateAndEmotons(ReviewEmotionStatus emotion);
}
