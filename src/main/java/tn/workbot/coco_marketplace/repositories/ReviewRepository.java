package tn.workbot.coco_marketplace.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import tn.workbot.coco_marketplace.entities.Review;

import java.util.List;


@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    @Query("select r from Review r group by r.createdAt,r.emotionStatus ")
    List<Review> ClassifyReviewsByDateAndEmotons();


}
