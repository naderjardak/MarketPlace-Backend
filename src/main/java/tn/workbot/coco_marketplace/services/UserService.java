package tn.workbot.coco_marketplace.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.workbot.coco_marketplace.entities.User;
import tn.workbot.coco_marketplace.repositories.UserrRepository;
import tn.workbot.coco_marketplace.services.interfaces.UserInterface;

import java.util.List;

@Service
public class UserService implements UserInterface {

    @Autowired
    UserrRepository userRepository;

    @Override
    public User ajouter(User u) {
        return userRepository.save(u);
    }

    @Override
    public void supprimerById(long id) {
        userRepository.deleteById(id);
    }

    @Override
    public User modifier(Long id, User u) {
        u.setId(id);
        return userRepository.save(u);
    }

    @Override
    public User recupererAvecId(long id) {
        return userRepository.findById(id).get();
    }

    @Override
    public List<User> recupererAll() {
        return (List<User>) userRepository.findAll();
    }


}
