package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import services.interfaces.ProductCategoryInterface;

@Controller
public class ProductCategoryController {

    @Autowired
    ProductCategoryInterface categoryInterface;
}
