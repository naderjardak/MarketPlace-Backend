package services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PickupService {
    @Autowired
    PickupIService pi;
}
