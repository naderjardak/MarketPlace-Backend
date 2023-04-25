package tn.workbot.coco_marketplace.services.interfaces;

import tn.workbot.coco_marketplace.entities.Role;
import tn.workbot.coco_marketplace.entities.User;
import tn.workbot.coco_marketplace.entities.enmus.RoleType;

import java.util.List;

public interface RoleInterface {

    Role CreateRole(Role r);

    void DeleteRoleById(long id);

    public Role updateRole(Role r);

    Role GetRoleById(long id);
    public List<Role> GetRoleAll();

    public void AssignRolePrivilege(long idRole ,long idPrivilege);
    public Role findRolebyRoleType(RoleType roleType);

}
