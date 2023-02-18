package tn.workbot.coco_marketplace.repositories;

import tn.workbot.coco_marketplace.entities.Privilege;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrivilegeRepository extends CrudRepository<Privilege,Long> {
}
