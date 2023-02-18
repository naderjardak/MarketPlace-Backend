package services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repositories.UserrRepository;
import services.interfaces.UserInterface;

@Service
public class UserService implements UserInterface {

    @Autowired
    UserrRepository userRepository;
}
