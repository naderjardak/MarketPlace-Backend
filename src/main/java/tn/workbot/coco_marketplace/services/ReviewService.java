package tn.workbot.coco_marketplace.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.workbot.coco_marketplace.entities.Order;
import tn.workbot.coco_marketplace.entities.Pickup;
import tn.workbot.coco_marketplace.entities.Product;
import tn.workbot.coco_marketplace.entities.Review;
import tn.workbot.coco_marketplace.entities.enmus.StatusPickupBuyer;
import tn.workbot.coco_marketplace.repositories.OrderRepository;
import tn.workbot.coco_marketplace.repositories.ProductRepository;
import tn.workbot.coco_marketplace.repositories.ReviewRepository;
import tn.workbot.coco_marketplace.services.interfaces.ReviewInterface;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
@Service
public class ReviewService implements ReviewInterface {

    @Autowired
    ReviewRepository rvp;

    @Autowired
    ProductRepository pr;

    @Autowired
    OrderRepository repository;

    @Override
    public List<Review> getAllReviews() {
        return rvp.findAll();
    }

    @Override
    public Review getReviewById(Long id) {
        return rvp.findById(id).orElse(null);
    }

    @Override
    public void addReview(Review review,Long id) {
        review.setCreatedAt(new Date());
        Review review1=rvp.save(review);
        Product product=pr.findById(id).get();
        review1.setProduct(product);
        review.setComment(review.hideBadWords(review.getComment()));
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



    @Override
    public float calculateProductRating(Long productId, int rating) {

        Product product = pr.findById(productId).orElse(null);

        if(product != null) {

            float currentRating = product.getRating();
            int numberOfRatings = product.getNumberOfRatings();


            float newRating = ((currentRating * numberOfRatings) + rating) / (numberOfRatings + 1);


            product.setRating(newRating);
            product.setNumberOfRatings(numberOfRatings + 1);


            pr.save(product);


            return newRating;
        } else {

            return -1;
        }
    }

}
