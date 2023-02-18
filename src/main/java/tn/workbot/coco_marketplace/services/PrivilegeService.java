package tn.workbot.coco_marketplace.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.workbot.coco_marketplace.repositories.PrivilegeRepository;
import tn.workbot.coco_marketplace.services.interfaces.PrivilegeInterface;

@Service
public class PrivilegeService implements PrivilegeInterface {
    @Autowired
    PrivilegeRepository privilegeRepository;
}
