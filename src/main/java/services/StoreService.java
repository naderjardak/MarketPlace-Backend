package services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repositories.StoreRepository;

@Service
public class StoreService {

    @Autowired
    StoreRepository storeRepository;


}
