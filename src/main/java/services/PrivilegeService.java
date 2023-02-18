package services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repositories.PrivilegeRepository;
import services.interfaces.PrivilegeInterface;

@Service
public class PrivilegeService implements PrivilegeInterface {
    @Autowired
    PrivilegeRepository privilegeRepository;
}
