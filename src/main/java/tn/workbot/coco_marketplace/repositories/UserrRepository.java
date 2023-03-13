package tn.workbot.coco_marketplace.repositories;

import tn.workbot.coco_marketplace.entities.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import tn.workbot.coco_marketplace.entities.enmus.RoleType;

import java.util.List;

@Repository
public interface UserrRepository extends CrudRepository<User,Long> {

    List<User> findUserByRoleType(RoleType roleType);

    User findUserByEmail(String email);
    User findByResetToken(String resetToken);


}
