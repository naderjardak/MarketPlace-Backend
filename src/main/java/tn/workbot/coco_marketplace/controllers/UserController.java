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
    public User Create(@RequestBody User u) {
        return userInterface.Create(u);
    }

    @DeleteMapping("/deleteUser")
    void DeleteById(@RequestParam long id) {
        userInterface.DeleteById(id);
    }

    @PutMapping("/updateUser")
    public User update(@RequestBody User u) {
       return  userInterface.update(u);
    }

    @GetMapping("/selectUserById")
    User GetById(@RequestParam long id){
        return userInterface.GetById(id);
    }
    
    @GetMapping("/selectUserAll")
    public List<User>GetAll(){
        return userInterface.GetAll();
    }
}
