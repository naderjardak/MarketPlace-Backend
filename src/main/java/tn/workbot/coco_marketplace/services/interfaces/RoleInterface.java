package tn.workbot.coco_marketplace.services.interfaces;

import tn.workbot.coco_marketplace.entities.Role;
import tn.workbot.coco_marketplace.entities.User;

import java.util.List;

public interface RoleInterface {

    Role CreateRole(Role r);

    void DeleteRoleById(long id);

    public Role updateRole(Role r);

    Role GetRoleById(long id);
    public List<Role> GetRoleAll();

}
