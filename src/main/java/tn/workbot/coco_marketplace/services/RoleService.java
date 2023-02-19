package tn.workbot.coco_marketplace.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.workbot.coco_marketplace.entities.Role;
import tn.workbot.coco_marketplace.repositories.RoleRepository;
import tn.workbot.coco_marketplace.services.interfaces.RoleInterface;

@Service
public class RoleService implements RoleInterface {

    @Autowired
    RoleRepository roleRepository;

    @Override
    public Role ajouterRole(Role r) {
        return roleRepository.save(r);
    }

    @Override
    public void supprimerRoleById(long id) {
        roleRepository.deleteById(id);
    }

    @Override
    public Role modifierRole(Long id, Role r) {
        r.setId(id);
        return roleRepository.save(r);

    }

    @Override
    public Role recupererRoleAvecId(long id) {
        return roleRepository.findById(id).get();
    }
}
