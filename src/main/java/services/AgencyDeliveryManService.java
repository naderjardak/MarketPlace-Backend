package services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import services.interfaces.AgencyDeliveryManIService;

@Service
public class AgencyDeliveryManService {
    @Autowired
    AgencyDeliveryManIService admi;
}
