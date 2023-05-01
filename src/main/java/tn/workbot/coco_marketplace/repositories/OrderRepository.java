package tn.workbot.coco_marketplace.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tn.workbot.coco_marketplace.entities.*;
import tn.workbot.coco_marketplace.entities.enmus.StatusOrderType;

import javax.persistence.OrderBy;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
  @Query("SELECT concat(o.buyer.FirstName,' ',o.buyer.LastName) as name ,count(o) as nb from Order o where o.status='ACCEPTED_PAYMENT' group by o.buyer order by count (o) desc ")
  List<Map<String,Integer>> RankUsersByOrdersAcceptedPayement();

  @Query("SELECT concat(o.buyer.FirstName,' ',o.buyer.LastName,' ',count(o)) from Order o where o.status='ACCEPTED_PAYMENT' group by o.buyer order by count (o) desc ")
  List<String> PDFRankUsersByOrdersAcceptedPayement();

  //int findByBuyerId(Long id);

  @Query("SELECT o from Order o where o.buyer.id=:id and o.status='BASKET'")
  Order BasketExistance(@Param("id") Long id);


  @Query("SELECT o.shipping.governorate as Governorat,COUNT(o) as NB FROM Order o where o.status='ACCEPTED_PAYMENT' group by o.shipping.governorate order by count(o) desc ")
  List<Map<String,Integer>> RankGouvernoratByNbOrders();

  @Query("SELECT concat(o.shipping.governorate,' ',COUNT(o))  FROM Order o where o.status='ACCEPTED_PAYMENT' group by o.shipping.governorate order by count(o) desc ")
  List<String> PDF_RankGouvernoratByNbOrders();

  @Query("SELECT o from Order o where o.status='BASKET' and o.creationDate<:date" )
  List<Order> deleteOrderByStatusAndCreationDate(@Param("date") Date date);

  @Query("SELECT p from Product p where p.productPriceBeforeDiscount>=:minPrix and p.productPriceBeforeDiscount<=:maxPrix and p.name LIKE :nameProduct and p.store.seller.BrandName LIKE :mark and (p.productCategory.name LIKE :categorie) order by p.numberOfPurchase desc ")
  List<Product> researchProductMOSTREQUESTED(@Param("maxPrix") float maxPrix ,@Param("minPrix") float minPrix ,@Param("nameProduct") String nameProduct,@Param("categorie") String categorie,@Param("mark") String mark);

  @Query("SELECT p from Product p where p.productPriceBeforeDiscount>=:minPrix and p.productPriceBeforeDiscount<=:maxPrix and p.name LIKE :nameProduct and p.store.seller.BrandName LIKE :mark and (p.productCategory.name LIKE :categorie ) order by p.productPrice asc ")
  List<Product> researchProductASCENDINGPRICE(@Param("maxPrix") float maxPrix ,@Param("minPrix") float minPrix ,@Param("nameProduct") String nameProduct,@Param("categorie") String categorie,@Param("mark") String mark);

  @Query("SELECT p from Product p where p.productPriceBeforeDiscount>=:minPrix and p.productPrice<=:maxPrix and p.name LIKE :nameProduct and p.store.seller.BrandName LIKE :mark and (p.productCategory.name LIKE :categorie) order by p.productPrice desc ")
  List<Product> researchProductDECREASINGPRICE(@Param("maxPrix") float maxPrix ,@Param("minPrix") float minPrix ,@Param("nameProduct") String nameProduct,@Param("categorie") String categorie,@Param("mark") String mark);

  @Query("SELECT p from Product p where p.productPriceBeforeDiscount>=:minPrix and p.productPriceBeforeDiscount<=:maxPrix and p.name LIKE :nameProduct and p.store.seller.BrandName LIKE :mark and (p.productCategory.name LIKE :categorie) order by p.rating desc ")
  List<Product> researchProductTOPRATED(@Param("maxPrix") float maxPrix ,@Param("minPrix") float minPrix ,@Param("nameProduct") String nameProduct,@Param("categorie") String categorie,@Param("mark") String mark);

  @OrderBy("creationDate desc")
  @Query("SELECT p from Product p where p.productPrice>=:minPrix and p.productPrice<=:maxPrix and p.name LIKE :nameProduct and p.store.seller.BrandName LIKE :mark and (p.productCategory.name LIKE :categorie)")
  List<Product> researchProductNEWARRIVAL(@Param("maxPrix") float maxPrix ,@Param("minPrix") float minPrix ,@Param("nameProduct") String nameProduct,@Param("categorie") String categorie,@Param("mark") String mark);

  @Query("SELECT max(p.productPriceBeforeDiscount) from Product p")
  float maxProductPrice();

  @Query("select count(u) from User u where u.email=:email")
  int findUserByEmail(@Param("email") String email);

  @Query("select o.ref from Order o ")
  List<String> reflist();

  @Query("SELECT p from Product p where p.name LIKE :nameProduct OR p.description LIKE :nameProduct")
  List<Product> productsByNameLike(@Param("nameProduct") String nameProduct);

  @Query("select pq from Order o,ProductQuantity pq where o.id=:id and pq.order=o ")
  List<ProductQuantity> orderProductList(@Param("id") long id);


  List<Order> findByShipping(Shipping shipping);


  List<Order> findOrderByBuyerIdAndStatusIsNotLike(Long id, StatusOrderType st);

  @Query("select o from Order o where not o.status='BASKET' Order By (o.sum+o.deliveryPrice) desc  ")
  List<Order> usersBestOrders();


}

