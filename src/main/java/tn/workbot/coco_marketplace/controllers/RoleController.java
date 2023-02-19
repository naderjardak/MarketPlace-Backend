package tn.workbot.coco_marketplace.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tn.workbot.coco_marketplace.entities.Role;
import tn.workbot.coco_marketplace.services.interfaces.RoleInterface;
import java.util.List;

@RestController
@RequestMapping("Role")
public class RoleController {
    @Autowired

    RoleInterface roleInterface;

    @PostMapping("/addRole")
    public Role ajouterRole(@RequestBody Role r) {
        return roleInterface.ajouterRole(r);
    }

    @DeleteMapping("/deleteRole")
    void supprimerRoleById(@RequestParam long id) {
        roleInterface.supprimerRoleById(id);
    }

    @PutMapping("/updateRole")
    public Role modifierRole(@RequestParam  Long id, @RequestBody Role r) {
        return  roleInterface.modifierRole(id, r);
    }

    @GetMapping("/selectRoleById")
    Role recupererRoleAvecId(@RequestParam long id){

        return roleInterface.recupererRoleAvecId(id);
    }

    @GetMapping("/selectRoleAll")
    public List<Role> recupererRoleAll(){
        return roleInterface.recupererRoleAll();
    }
}
