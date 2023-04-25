package tn.workbot.coco_marketplace.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tn.workbot.coco_marketplace.entities.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import tn.workbot.coco_marketplace.entities.enmus.RoleType;

@Repository
public interface RoleRepository extends CrudRepository<Role,Long> {
    @Query("select r from Role r where r.type =:v1")
    public Role findRoleByType(@Param("v1") RoleType v1);
}
