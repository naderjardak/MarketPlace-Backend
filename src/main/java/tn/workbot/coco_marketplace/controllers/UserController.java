package tn.workbot.coco_marketplace.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import tn.workbot.coco_marketplace.entities.User;
import tn.workbot.coco_marketplace.services.interfaces.UserInterface;

import java.util.List;

@RestController
@RequestMapping("User")
public class UserController {
    @Autowired
    UserInterface userInterface;

    @PostMapping("/addUser")
    public User ajouter(@RequestBody User u) {
        return userInterface.ajouter(u);
    }

    @DeleteMapping("/deleteUser")
    void supprimerById(@RequestParam long id) {
        userInterface.supprimerById(id);
    }

    @PutMapping("/updateUser")
    public User modifier(@RequestParam  Long id, @RequestBody User u) {
       return  userInterface.modifier(id, u);
    }

    @GetMapping("/selectUserById")
    User recupererAvecId(@RequestParam long id){
        return userInterface.recupererAvecId(id);
    }
    @GetMapping("/selectUserAll")
    public List<User> recupererAll(){
        return userInterface.recupererAll();
    }
}
