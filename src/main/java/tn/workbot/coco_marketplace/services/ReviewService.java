package tn.workbot.coco_marketplace.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tn.workbot.coco_marketplace.entities.*;
import tn.workbot.coco_marketplace.entities.enmus.StatusPickupBuyer;
import tn.workbot.coco_marketplace.repositories.*;
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

    UserrRepository ur;

    @Autowired
    RoleRepository rr;

    @Autowired
    OrderRepository or;

    @Autowired
    BadWordsService badWordsService;



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
        //Product reference=pr.getReferenceById(id);


        review1.setProduct(product);
        BadWordsService badWordsService1=new BadWordsService();
        review.setComment(badWordsService1.hideBadWords(review.getComment()));
        rvp.save(review);

        /*Order order=or.findById(id).get();
        List<Pickup> pickups= order.getPickups();
        for(Pickup p:pickups){
            if(p.getStatusPickupBuyer().equals(StatusPickupBuyer.DELIVERED)){
                List<Request>  requests=p.getRequests();
                for(Request r:requests){
                    if(r.getAgencyDeliveryMan() != null && r.getDeliveryman() ==null) {

                        review.setDeliveryAgency(r.getAgencyDeliveryMan().getAgencyBranch().getDeliveryAgency());
                    }
                    if(r.getDeliveryman() != null && r.getAgencyDeliveryMan() ==null){
                        review.setDeliveryFreelancer(r.getDeliveryman());
                    }
                }
            }
               rvp.save(review);
        }*/





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

    @Override
    public void AddReviewOnDelivery(Review review, Long id) {
        //review.setCreatedAt(new Date());
        //Review rev=rvp.save(review);
        //Order order= or.findById(idOrder).get();
        //order.

    }

}
