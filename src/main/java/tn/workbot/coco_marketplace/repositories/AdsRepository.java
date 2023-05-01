package tn.workbot.coco_marketplace.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tn.workbot.coco_marketplace.entities.Ads;
import tn.workbot.coco_marketplace.entities.Product;
import tn.workbot.coco_marketplace.entities.User;

import java.util.List;

@Repository
public interface AdsRepository extends CrudRepository<Ads,Long> {
    @Query("select distinct u from User u , Store s , Product p where s.seller.id=u.id and s.id=p.store.id and p.id=:v1")
    public User getSellerOfProduct(@Param("v1") Long idProduct);
    @Query("select distinct p from Product p,Store s ,User u where s.seller.id=:v1 and p.store.id=s.id")
    public List<Product> getProductByUser(@Param("v1") Long idUser);
    @Query("select  distinct  a from Ads a,Product p,Store  s ,User u where s.id=p.store.id and s.seller.id=:v1 and a.product.id=p.id")
    public List<Ads> getAdsByUser(@Param("v1")Long iduser);
}
