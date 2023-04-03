package tn.workbot.coco_marketplace.Dto.ProductForm;

import lombok.Getter;
import lombok.Setter;
import tn.workbot.coco_marketplace.entities.ProductCategory;

import java.util.Set;

@Getter
@Setter
public class ProductFormDTO {
    private Long id;

    private String name;


    private String description;

    private String Image;

    private float productPrice;

    private int quantity;
    private float productWeight;
    private String AdditionalDeliveryInstructions;

    private String image1;
    private String image2;
    private String image3;

    private ProductCategory productCategory;

    Set<String> storesNames;
}
