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
    public User Create(User u,Long idRole) {
         Role role= roleRepository.findById(idRole).get();
        u.setPassword(this.passwordEncoder.encode(u.getPassword()));
        u.setRole(role);
        userRepository.save(u);
        return  u;

    }

    @Override
    public User Create1(User u) {
        u.setPassword(this.passwordEncoder.encode(u.getPassword()));
        userRepository.save(u);
        return  u;
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
