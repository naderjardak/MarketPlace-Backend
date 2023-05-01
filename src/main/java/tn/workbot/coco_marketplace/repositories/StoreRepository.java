package tn.workbot.coco_marketplace.repositories;

import tn.workbot.coco_marketplace.entities.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.workbot.coco_marketplace.entities.User;

import java.util.List;

@Repository
public interface StoreRepository extends JpaRepository<Store, Long> {
        List<Store> findByGovernorate(String governorat);

        Store findByNameAndSeller(String name, User user);

        List<Store> findStoresBySeller(User user);
}
