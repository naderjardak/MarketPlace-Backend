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


}
