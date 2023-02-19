package tn.workbot.coco_marketplace.services.interfaces;

import tn.workbot.coco_marketplace.entities.Privilege;


import java.util.List;

public interface PrivilegeInterface {

    Privilege ajouterPrivilege(Privilege p);

    void supprimerPrivilegeById(long id);

    public Privilege modifierPrivilege(Long id, Privilege p);

    Privilege recupererPrivilegeAvecId(long id);

    public List<Privilege> recupererPrivilegeAll();

}
