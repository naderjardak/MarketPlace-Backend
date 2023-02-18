package services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import services.interfaces.AgencyBranchIService;

@Service
public class AgencyBranchService {
    @Autowired
    AgencyBranchIService abi;

}
