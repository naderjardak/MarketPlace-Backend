package tn.workbot.coco_marketplace.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.workbot.coco_marketplace.entities.Review;
import tn.workbot.coco_marketplace.repositories.ReviewRepository;
import tn.workbot.coco_marketplace.services.interfaces.ReviewInterface;

import java.util.Date;
import java.util.List;
@Service
public class ReviewService implements ReviewInterface {

    @Autowired
    ReviewRepository rvp;

    @Override
    public List<Review> getAllReviews() {
        return rvp.findAll();
    }

    @Override
    public Review getReviewById(Long id) {
        return rvp.findById(id).orElse(null);
    }

    @Override
    public void addReview(Review review) {
        review.setCreatedAt(new Date());
        rvp.save(review);
    }

    @Override
    public void updateReview(Review review) {
        review.setUpdatedAt(new Date());
        rvp.save(review);
    }

    @Override
    public void deleteReview(Long id) {
        rvp.deleteById(id);
    }
}
