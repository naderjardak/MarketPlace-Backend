package services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repositories.PrivilegeRepository;

@Service
public class PrivilegeService {
    @Autowired
    PrivilegeRepository privilegeRepository;
}
