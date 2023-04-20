package tn.workbot.coco_marketplace.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import tn.workbot.coco_marketplace.entities.Ads;

@Repository
public interface AdsRepository extends CrudRepository<Ads,Long> {
}
