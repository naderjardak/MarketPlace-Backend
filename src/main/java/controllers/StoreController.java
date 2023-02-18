package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import services.interfaces.StoreInterface;

@Controller
public class StoreController {

    @Autowired
    StoreInterface storeInterface;
}
