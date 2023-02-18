package tn.workbot.coco_marketplace.repositories;

import tn.workbot.coco_marketplace.entities.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StoreRepository extends JpaRepository<Store, Long> {
}
