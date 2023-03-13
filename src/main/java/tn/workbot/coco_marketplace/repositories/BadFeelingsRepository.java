package tn.workbot.coco_marketplace.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;
import tn.workbot.coco_marketplace.entities.BadFeelings;

@Repository
public interface BadFeelingsRepository extends JpaRepository<BadFeelings,Long> {
}
