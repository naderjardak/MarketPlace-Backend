package tn.workbot.coco_marketplace.services.interfaces;

import tn.workbot.coco_marketplace.entities.User;

import java.util.List;

public interface UserInterface {
    User Create(User u);

    void  DeleteById(long id);

    public User update(User u);

    User GetById(long id);

    public List<User> GetAll();
    public void affectRoleAtUser(long idRole,long idUser);

    public User findByEmail(String email);



}
