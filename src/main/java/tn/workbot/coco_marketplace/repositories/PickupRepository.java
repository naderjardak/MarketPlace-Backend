package tn.workbot.coco_marketplace.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tn.workbot.coco_marketplace.entities.Pickup;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import tn.workbot.coco_marketplace.entities.Product;
import tn.workbot.coco_marketplace.entities.Store;
import tn.workbot.coco_marketplace.entities.enmus.StatusPickupSeller;

import java.util.List;

@Repository
public interface PickupRepository extends CrudRepository<Pickup,Long> {
    List<Pickup> findByGovernorate(String governorat);
    @Query("select s from User u , Store s where u.id=s.seller.id and u.id=:v1")
    List<Store> storesofuser(@Param("v1") Long id);
    @Query("select p from Pickup p,Request r where p.id=r.pickup.id and p.id=:v1  and r.requestStatus='APPROVED'")
    Pickup pickupprettolivred(@Param("v1") Long id);
    @Query("select count(distinct s) from Store s,Product p,ProductQuantity pq,Order o where s.id=p.store.id and p.reference=pq.product.reference and o.id=pq.order.id and o.id=:v2 ")
    public  int countstoreorder(@Param("v2") Long id);
    @Query("select s from Store s,Product p,ProductQuantity pq,Order o,User u where s.id=p.store.id and p.reference=pq.product.reference and o.id=pq.order.id and o.id=:v3 and s.seller.id=u.id and u.id=:v4")
    public  Store storeoforder(@Param("v3") Long id,@Param("v4") Long idSeller);
    @Query("select distinct p from Product p,Store s,ProductQuantity pq,Order o,Pickup pi,User u where s.id=p.store.id and p.reference=pq.product.reference and o.id=pq.order.id and o.id=:v3 and s.seller.id=u.id and u.id=:v4")
    public  List<Product> productoforder(@Param("v3") Long id, @Param("v4") Long idSeller);
    @Query("select p from Pickup  p ,Request r ,User u,Order o where p.codePickup=:v1 and p.order.buyer.id=:v3 ")
    public Pickup trakingB(@Param("v1") String codeP,@Param("v3") Long idBuyer);

    @Query("select p from Pickup  p,Request r where p.codePickup=:v1 and p.store.seller.id=:v3")
    public Pickup trakingS(@Param("v1") String codeP,@Param("v3") Long idSeller);

}
