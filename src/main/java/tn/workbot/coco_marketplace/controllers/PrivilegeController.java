package tn.workbot.coco_marketplace.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import tn.workbot.coco_marketplace.entities.Privilege;
import tn.workbot.coco_marketplace.entities.Role;
import tn.workbot.coco_marketplace.services.interfaces.PrivilegeInterface;

import java.util.List;

@RestController
public class PrivilegeController {

    @Autowired
    PrivilegeInterface privilegeInterface;

    @PostMapping("/addPrivilege")
    public Privilege ajouterPrivilege(@RequestBody Privilege p) {
        return privilegeInterface.ajouterPrivilege(p);
    }
    @DeleteMapping("/deletePrivilege")
    void supprimerPrivilegeById(@RequestParam long id) {
       privilegeInterface.supprimerPrivilegeById(id);
    }
    @PutMapping("/updatePrivilege")
    public Privilege modifierPrivilege(@RequestParam  Long id, @RequestBody Privilege p) {
        return  privilegeInterface.modifierPrivilege(id,p);
    }

    @GetMapping("/selectPrivilegeById")
    Privilege recupererPrivilegeAvecId(@RequestParam long id){

        return privilegeInterface.recupererPrivilegeAvecId(id);
    }

    @GetMapping("/selectPrivilegAll")
    public List<Privilege> recupererPrivilegeAll(){
        return privilegeInterface.recupererPrivilegeAll();
    }


}
