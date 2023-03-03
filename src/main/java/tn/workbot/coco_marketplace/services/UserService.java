package tn.workbot.coco_marketplace.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.workbot.coco_marketplace.entities.Role;
import tn.workbot.coco_marketplace.entities.User;
import tn.workbot.coco_marketplace.repositories.RoleRepository;
import tn.workbot.coco_marketplace.repositories.UserrRepository;
import tn.workbot.coco_marketplace.services.interfaces.UserInterface;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class UserService implements UserInterface {

    @Autowired
    UserrRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Override
    public User Create(User u) {
        return userRepository.save(u);
    }

    @Override
    public void DeleteById(long id) {
        userRepository.deleteById(id);
    }

    @Override
    public User update( User u) {
        return userRepository.save(u);
    }

    @Override
    public User GetById(long id) {
        return userRepository.findById(id).get();
    }

    @Override
    public List<User> GetAll() {
        return (List<User>) userRepository.findAll();
    }

    @Override
    public void affectRoleAtUser(long idRole, long idUser) {
        Role r=roleRepository.findById(idRole).get();
        User u=userRepository.findById(idUser).get();
        u.setRole(r);
        userRepository.save(u);
    }

    public User findByEmail(String email) {
        return userRepository.findUserByEmail(email).orElseThrow(()->new EntityNotFoundException((
                "user not found with email =" +email)
        ));
    }

}
