package tn.workbot.coco_marketplace.services.interfaces;

import tn.workbot.coco_marketplace.entities.User;

import java.util.List;

public interface UserInterface {
    User ajouter(User u);

    void supprimerById(long id);

    public User modifier(Long id, User u);

    User recupererAvecId(long id);

    public List<User> recupererAll();
}
