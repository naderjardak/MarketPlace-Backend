package tn.workbot.coco_marketplace.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tn.workbot.coco_marketplace.entities.Loyalty;

@Repository
public interface LoyaltyRepository extends JpaRepository<Loyalty,Long> {
    @Query("select l from Loyalty l where l.link=:ch")
    Loyalty findByLink(@Param("ch") String ch);

    @Query("Select l.points from Loyalty l where l.user.id=:id")
    Integer pointsClaimed(@Param("id") Long id);

    @Query("select l from Loyalty l where l.user.id=:id ")
    Loyalty loyaltyExistance(@Param("id") Long id);
}
