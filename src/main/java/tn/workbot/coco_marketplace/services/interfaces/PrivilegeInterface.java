package tn.workbot.coco_marketplace.services.interfaces;

import tn.workbot.coco_marketplace.entities.Privilege;
import tn.workbot.coco_marketplace.entities.Role;

public interface PrivilegeInterface {

    Privilege ajouterPrivilege(Privilege p);

    void supprimerPrivilegeById(long id);

    public Privilege modifierPrivilege(Long id, Privilege p);

    Privilege recupererPrivilegeAvecId(long id);
}
