package tn.workbot.coco_marketplace.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.workbot.coco_marketplace.entities.Privilege;
import tn.workbot.coco_marketplace.repositories.PrivilegeRepository;
import tn.workbot.coco_marketplace.services.interfaces.PrivilegeInterface;

import java.util.List;

@Service
public class PrivilegeService implements PrivilegeInterface {
    @Autowired
    PrivilegeRepository privilegeRepository;

    @Override
    public Privilege CreatePrivilege(Privilege p) {
        return privilegeRepository.save(p);
    }

    @Override
    public void DeletePrivilegeById(long id) {
        privilegeRepository.deleteById(id);
    }

    @Override
    public Privilege updatePrivilege( Privilege p) {

        return privilegeRepository.save(p);
    }

    @Override
    public Privilege GetPrivilegeById(long id) {
        return privilegeRepository.findById(id).get();
    }

    @Override
    public List<Privilege> GetPrivilegeAll() {
        return (List<Privilege>) privilegeRepository.findAll();
    }
}
