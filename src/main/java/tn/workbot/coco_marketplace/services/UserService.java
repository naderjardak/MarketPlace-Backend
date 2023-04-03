package tn.workbot.coco_marketplace.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tn.workbot.coco_marketplace.Dto.auth.AccountResponse;
import tn.workbot.coco_marketplace.entities.Role;
import tn.workbot.coco_marketplace.entities.User;
import tn.workbot.coco_marketplace.repositories.RoleRepository;
import tn.workbot.coco_marketplace.repositories.UserrRepository;
import tn.workbot.coco_marketplace.services.interfaces.UserInterface;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Map;

@Service
public class UserService implements UserInterface {

    @Autowired
    UserrRepository userRepository;

    @Autowired
    RoleRepository roleRepository;


    PasswordEncoder passwordEncoder;


    public UserService() {
        this.passwordEncoder = new BCryptPasswordEncoder();
    }


    @Override
    public User Create(User u) {

        u.setPassword(this.passwordEncoder.encode(u.getPassword()));
        User user=   userRepository.save(u);
        return  user;

    }

    @Override
    public void DeleteById(long id) {
        userRepository.deleteById(id);
    }

    @Override
    public User update(User u) {
        return userRepository.save(u);
    }

    @Override
    public User updateUserByID(long id,User u) {
        User user1 = userRepository.findById(id).get();
//        user1.setEmail(u.getEmail());
//        user1.setFirstName(u.getFirstName());
//        user1.setPhoneNumber(u.getPhoneNumber());
//        user1.setPassword(u.getPassword());
        return userRepository.save(user1);
    }

    @Override
    public User getUserById(long id) {
        return userRepository.findById(id).get();
    }

    @Override
    public List<User> GetAll() {
        return (List<User>) userRepository.findAll();
    }

    @Override
    public void affectRoleAtUser(long idRole, long idUser) {
        Role r = roleRepository.findById(idRole).get();
        User u = userRepository.findById(idUser).get();
        u.setRole(r);
        userRepository.save(u);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }

    public User findByResetToken(String resetToken) {
        return userRepository.findByResetToken(resetToken);
    }






}
