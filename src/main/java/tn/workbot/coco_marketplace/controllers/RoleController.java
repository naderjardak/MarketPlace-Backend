package tn.workbot.coco_marketplace.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import tn.workbot.coco_marketplace.entities.Role;
import tn.workbot.coco_marketplace.services.interfaces.RoleInterface;
import java.util.List;

@RestController
@RequestMapping("Role")
@PreAuthorize("hasAuthority('ADMIN')")

public class RoleController {
    @Autowired

    RoleInterface roleInterface;

    @PostMapping("/addRole")
    public Role CreateRole(@RequestBody Role r) {
        return roleInterface.CreateRole(r);
    }

    @DeleteMapping("/deleteRole")
    void DeleteRoleById(@RequestParam long id) {
        roleInterface.DeleteRoleById(id);
    }

    @PutMapping("/updateRole")
    public Role updateRole(@RequestBody Role r) {
        return  roleInterface.updateRole( r);
    }

    @GetMapping("/selectRoleById")
    Role GetRoleById(@RequestParam long id){

        return roleInterface.GetRoleById(id);
    }

    @GetMapping("/selectRoleAll")
    public List<Role> GetRoleAll(){
        return roleInterface.GetRoleAll();
    }

    @PutMapping("/affectRolePrivilege")
    public void AssignRolePrivilege(@RequestParam long idRole , @RequestParam long idPrivilege){
        roleInterface.AssignRolePrivilege(idRole,idPrivilege);
    }
}
//test