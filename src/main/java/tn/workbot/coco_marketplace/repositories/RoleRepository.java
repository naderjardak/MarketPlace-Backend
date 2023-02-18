package tn.workbot.coco_marketplace.repositories;

import tn.workbot.coco_marketplace.entities.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends CrudRepository<Role,Long> {
}
