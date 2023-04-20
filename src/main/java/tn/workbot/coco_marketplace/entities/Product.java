package tn.workbot.coco_marketplace.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.Hibernate;
import tn.workbot.coco_marketplace.entities.enmus.ProductStatus;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Objects;

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
    private ProductCategory productCategory;

    private String videoLink;

    @OneToMany(mappedBy = "product")
    @JsonIgnore
    private List<PromotionCode> promotionCodes;
    @JsonIgnore
    @ManyToOne
    private Store store;

    @OneToMany(mappedBy = "product")
    @JsonIgnore
    private List<Review> reviews;

    @OneToMany(mappedBy = "product")
    @JsonIgnore
    private List<SupplierRequest> supplierRequests;
    @OneToMany(mappedBy = "product")
    @JsonIgnore
    private List<Ads> adsList;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Float.compare(product.productPrice, productPrice) == 0 && Float.compare(product.productPriceBeforeDiscount, productPriceBeforeDiscount) == 0 && Float.compare(product.deliveryPrice, deliveryPrice) == 0 && Float.compare(product.unityPriceFromSupplier, unityPriceFromSupplier) == 0 && Float.compare(product.rating, rating) == 0 && automaticRequestAcceptance == product.automaticRequestAcceptance && numberOfRatings == product.numberOfRatings && quantity == product.quantity && Float.compare(product.productWeight, productWeight) == 0 && deliveryQuantity == product.deliveryQuantity && enabled == product.enabled && numberOfPurchase == product.numberOfPurchase && Objects.equals(id, product.id) && reference.equals(product.reference) && Objects.equals(name, product.name) && Objects.equals(description, product.description) && Objects.equals(Image, product.Image) && Objects.equals(creationDate, product.creationDate) && Objects.equals(AdditionalDeliveryInstructions, product.AdditionalDeliveryInstructions) && productStatus == product.productStatus && Objects.equals(productQuantities, product.productQuantities) && Objects.equals(productCategory, product.productCategory) && Objects.equals(promotionCodes, product.promotionCodes) && Objects.equals(store, product.store) && Objects.equals(reviews, product.reviews) && Objects.equals(supplierRequests, product.supplierRequests) && Objects.equals(image1, product.image1) && Objects.equals(image2, product.image2) && Objects.equals(image3, product.image3);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, reference, name, description, Image, productPrice, productPriceBeforeDiscount, deliveryPrice, unityPriceFromSupplier, rating, automaticRequestAcceptance, numberOfRatings, quantity, productWeight, deliveryQuantity, enabled, creationDate, AdditionalDeliveryInstructions, numberOfPurchase, productStatus, productQuantities, productCategory, promotionCodes, store, reviews, supplierRequests, image1, image2, image3);
    }

    private String image1;
    private String image2;
    private String image3;
}