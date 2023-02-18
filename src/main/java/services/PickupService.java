package services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import services.interfaces.PickupIService;

@Service
public class PickupService {
    @Autowired
    PickupIService pi;
}
