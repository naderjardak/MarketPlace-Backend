package tn.workbot.coco_marketplace.services.interfaces;

import tn.workbot.coco_marketplace.entities.Privilege;


import java.util.List;

public interface PrivilegeInterface {

    Privilege CreatePrivilege(Privilege p);

    void DeletePrivilegeById(long id);

    public Privilege updatePrivilege(Privilege p);

    Privilege GetPrivilegeById(long id);

    public List<Privilege> GetPrivilegeAll();

}
