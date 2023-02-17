package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import services.PromotionCodeInterface;

@Controller

public class PromortionCodeController {

    @Autowired
    PromotionCodeInterface promotionCodeInterface;
}
