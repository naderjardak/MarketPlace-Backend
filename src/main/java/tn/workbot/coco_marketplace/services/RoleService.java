package tn.workbot.coco_marketplace.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.workbot.coco_marketplace.repositories.RoleRepository;
import tn.workbot.coco_marketplace.services.interfaces.RoleInterface;

@Service
public class RoleService implements RoleInterface {

    @Autowired
    RoleRepository roleRepository;
}
