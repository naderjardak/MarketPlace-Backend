package tn.workbot.coco_marketplace.services.interfaces;

import tn.workbot.coco_marketplace.entities.Role;
import tn.workbot.coco_marketplace.entities.User;

public interface RoleInterface {

    Role ajouterRole(Role r);

    void supprimerRoleById(long id);

    public Role modifierRole(Long id, Role r);

    Role recupererRoleAvecId(long id);
}
