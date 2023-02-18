package tn.workbot.coco_marketplace.repositories;

import tn.workbot.coco_marketplace.entities.PromotionCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PromotionCodeRepository extends JpaRepository<PromotionCode, Long> {
}
