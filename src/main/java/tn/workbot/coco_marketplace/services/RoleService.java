package tn.workbot.coco_marketplace.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.workbot.coco_marketplace.entities.Privilege;
import tn.workbot.coco_marketplace.entities.Role;
import tn.workbot.coco_marketplace.repositories.PrivilegeRepository;
import tn.workbot.coco_marketplace.repositories.RoleRepository;
import tn.workbot.coco_marketplace.services.interfaces.RoleInterface;

import java.util.List;

@Service
public class RoleService implements RoleInterface {

    @Autowired
    RoleRepository roleRepository;
    @Autowired
    PrivilegeRepository privilegeRepository;

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

    @Override
    public void AssignRolePrivilege(long idRole, long idPrivilege) {
        Role r=roleRepository.findById(idRole).get();//parent
        Privilege p=privilegeRepository.findById(idPrivilege).get();//child
        r.getPrivileges().add(p);
        roleRepository.save(r);

    }
}
