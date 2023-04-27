package tn.workbot.coco_marketplace.services.interfaces;

import org.springframework.web.multipart.MultipartFile;
import tn.workbot.coco_marketplace.entities.Role;
import tn.workbot.coco_marketplace.entities.User;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface UserInterface {
    User Create(User u,Long idRole);

    User Create1(User u);

    void  DeleteById(long id);

   public User update(User u);

   public User updateUserByID(long id,User u);

    User getUserById(long id);

    public List<User> GetAll();
    public void affectRoleAtUser(long idRole,long idUser);

    public User findByEmail(String email);

    public void storeFile(MultipartFile file) throws IOException;

    List<Map<String, Integer>> statsByRole();
    public  List<Role> getAllRolesd();
}
