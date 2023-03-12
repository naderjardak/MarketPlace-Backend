package tn.workbot.coco_marketplace.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tn.workbot.coco_marketplace.entities.enmus.ProductStatus;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String reference;
    // Define a field to store the name of the product
    private String name;

    // Define a field to store a brief description of the product
    private String description;

    // Define a field to store the URL of the image for the product
    private String Image;

    // Define a field to store the current price of the product. This price may change during a promotion.
    private float productPrice;

    // Define a field to store the price of the product before any discounts are applied
    private float productPriceBeforeDiscount;

    // Define a field to store the price of shipping the product
    private float deliveryPrice;
    private float unityPriceFromSupplier;
    // Define a field to store the average rating of the product
    private float rating;
    private boolean automaticRequestAcceptance;
    // Define a field to store the number of ratings that have been given for the product
    private int numberOfRatings;

    // Define a field to store the quantity of the product available for purchase
    private int quantity;
    //kg
    private float productWeight;

    private int deliveryQuantity;

    // Define a field to indicate whether the product is currently available for purchase
    private boolean enabled;
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate;
    // Define a field to store any additional delivery instructions provided by the customer
    private String AdditionalDeliveryInstructions;
    private int numberOfPurchase;
    // Define a field to store the current status of the product
    @Enumerated(EnumType.STRING)
    private ProductStatus productStatus;

    @OneToMany(mappedBy = "product")
    @JsonIgnore
    private List<ProductQuantity> productQuantities;

    @ManyToOne
    @JsonIgnore
    private ProductCategory productCategory;

    @OneToMany(mappedBy = "product")
    @JsonIgnore
    private List<PromotionCode> promotionCodes;

    @ManyToOne
    @JsonIgnore
    private Store store;

    @OneToMany(mappedBy = "product")
    @JsonIgnore
    private List<Review> reviews;

    @OneToMany(mappedBy = "product")
    @JsonIgnore
    private List<SupplierRequest> supplierRequests;

}