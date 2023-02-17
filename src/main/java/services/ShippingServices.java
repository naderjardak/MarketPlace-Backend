package services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repositories.ShippingRepository;

@Service
@Slf4j
public class ShippingServices implements ShippingInterface{
    @Autowired
    ShippingRepository shippingRepository;

}
