package tn.workbot.coco_marketplace.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.workbot.coco_marketplace.entities.Privilege;
import tn.workbot.coco_marketplace.repositories.PrivilegeRepository;
import tn.workbot.coco_marketplace.services.interfaces.PrivilegeInterface;

@Service
public class PrivilegeService implements PrivilegeInterface {
    @Autowired
    PrivilegeRepository privilegeRepository;

    @Override
    public Privilege ajouterPrivilege(Privilege p) {
        return privilegeRepository.save(p);
    }

    @Override
    public void supprimerPrivilegeById(long id) {
        privilegeRepository.deleteById(id);
    }

    @Override
    public Privilege modifierPrivilege(Long id, Privilege p) {
        p.setId(id);
        return privilegeRepository.save(p);
    }

    @Override
    public Privilege recupererPrivilegeAvecId(long id) {
        return privilegeRepository.findById(id).get();
    }
}
