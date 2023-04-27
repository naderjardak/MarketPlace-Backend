package tn.workbot.coco_marketplace.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import tn.workbot.coco_marketplace.Dto.auth.AccountResponse;
import tn.workbot.coco_marketplace.entities.Role;
import tn.workbot.coco_marketplace.entities.User;
import tn.workbot.coco_marketplace.repositories.RoleRepository;
import tn.workbot.coco_marketplace.repositories.UserrRepository;
import tn.workbot.coco_marketplace.services.interfaces.UserInterface;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
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
        u.setPassword(this.passwordEncoder.encode(u.getPassword()));
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



    private static final String FILE_DIRECTORY1 = "C:/xampp/htdocs/MarketPlace-Frontend/src/assets/uploads";
    private static final String FILE_DIRECTORY2 = "C:/xampp/htdocs/MarketPlace-Frontend/projects/front-office/src/assets/uploads";
    @Override
    public void storeFile(MultipartFile file) throws IOException {
        Path filePath1 = Paths.get(FILE_DIRECTORY1 + "/" + file.getOriginalFilename());
        Path filePath2 = Paths.get(FILE_DIRECTORY2 + "/" + file.getOriginalFilename());
        Files.copy(file.getInputStream(), filePath1, StandardCopyOption.REPLACE_EXISTING);
        Files.copy(file.getInputStream(), filePath2, StandardCopyOption.REPLACE_EXISTING);
    }

    @Override
    public List<Map<String, Integer>> statsByRole()
    {
        return userRepository.statsUsersByRole();
    }


    public  List<Role> getAllRolesd()
    {
        return (List<Role>) roleRepository.findAll();
    }


}
