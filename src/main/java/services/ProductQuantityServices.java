package services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repositories.ProductQuantityRepository;

@Service
@Slf4j
public class ProductQuantityServices implements ProductQuantityInterface {
    @Autowired
    ProductQuantityRepository productQuantityRepository;

}
