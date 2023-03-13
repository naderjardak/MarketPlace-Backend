package tn.workbot.coco_marketplace.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tn.workbot.coco_marketplace.entities.ClaimSav;
import tn.workbot.coco_marketplace.entities.enmus.ClaimSavStatusType;
import tn.workbot.coco_marketplace.entities.enmus.ClaimSavType;

import java.util.List;

@Repository
public interface ClaimSavRepository extends JpaRepository<ClaimSav,Long> {
    @Query("SELECT c FROM ClaimSav c WHERE c.claimSavType = :type AND c.status = :status")
    List<ClaimSav> getClaimsByTypeAndStatus(@Param("type") ClaimSavType type, @Param("status") ClaimSavStatusType status);
}


