package tn.workbot.coco_marketplace.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import tn.workbot.coco_marketplace.entities.Privilege;
import tn.workbot.coco_marketplace.entities.Role;
import tn.workbot.coco_marketplace.services.interfaces.PrivilegeInterface;

import java.util.List;

@RestController
@RequestMapping("Privilege")
public class PrivilegeController {

    @Autowired
    PrivilegeInterface privilegeInterface;

    @PostMapping("/addPrivilege")
    public Privilege CreatePrivilege(@RequestBody Privilege p) {
        return privilegeInterface.CreatePrivilege(p);
    }
    @DeleteMapping("/deletePrivilege")
    void DeletePrivilegeById(@RequestParam long id) {
       privilegeInterface.DeletePrivilegeById(id);
    }
    @PutMapping("/updatePrivilege")
    public Privilege updatePrivilege( @RequestBody Privilege p) {
        return  privilegeInterface.updatePrivilege(p);
    }

    @GetMapping("/selectPrivilegeById")
    Privilege GetPrivilegeById(@RequestParam long id){

        return privilegeInterface.GetPrivilegeById(id);
    }

    @GetMapping("/selectPrivilegAll")
    public List<Privilege> GetPrivilegeAll(){
        return privilegeInterface.GetPrivilegeAll();
    }


}
