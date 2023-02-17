package services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repositories.PromotionCodeRepository;

@Service
public class PromotionCodeService {

    @Autowired
    PromotionCodeRepository promotionCodeRepository;

}
