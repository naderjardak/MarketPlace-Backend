package tn.workbot.coco_marketplace.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tn.workbot.coco_marketplace.entities.Review;
import tn.workbot.coco_marketplace.entities.enmus.ReviewEmotionStatus;

import java.util.List;


@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    @Query("SELECT r FROM Review r WHERE r.emotionStatus = :emotion GROUP BY r.createdAt")
    List<Review> ClassifyReviewsByDateAndEmotons(@Param("emotion")ReviewEmotionStatus emotion);
//ghj

}
