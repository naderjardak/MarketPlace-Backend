package tn.workbot.coco_marketplace.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.workbot.coco_marketplace.entities.Role;
import tn.workbot.coco_marketplace.repositories.RoleRepository;
import tn.workbot.coco_marketplace.services.interfaces.RoleInterface;

import java.util.List;

@Service
public class RoleService implements RoleInterface {

    @Autowired
    RoleRepository roleRepository;

    @Override
    public Role CreateRole(Role r) {
        return roleRepository.save(r);
    }

    @Override
    public void DeleteRoleById(long id) {
        roleRepository.deleteById(id);
    }

    @Override
    public Role updateRole( Role r) {

        return roleRepository.save(r);

    }

    @Override
    public Role GetRoleById(long id) {
        return roleRepository.findById(id).get();
    }

    @Override
    public List<Role> GetRoleAll() {
        return (List<Role>) roleRepository.findAll();
    }
}
