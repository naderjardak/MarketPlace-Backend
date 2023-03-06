package tn.workbot.coco_marketplace.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tn.workbot.coco_marketplace.entities.*;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import tn.workbot.coco_marketplace.entities.enmus.StatusPickupSeller;

import java.util.List;

@Repository
public interface PickupRepository extends CrudRepository<Pickup,Long> {
    List<Pickup> findByGovernorate(String governorat);
    @Query("select s from User u , Store s where u.id=s.seller.id and u.id=:v1")
    List<Store> storesofuser(@Param("v1") Long id);
    @Query("select p from Pickup p,Request r where p.id=r.pickup.id and p.id=:v1  and r.requestStatus='APPROVED'")
    Pickup pickupprettolivred(@Param("v1") Long id);
    @Query("select count(distinct s) from Store s,Product p,ProductQuantity pq,Order o where s.id=p.store.id and p.id=pq.product.id and o.id=pq.order.id and o.id=:v2 ")
    public  int countstoreorder(@Param("v2") Long id);
    @Query("select s from Store s,Product p,ProductQuantity pq,Order o,User u where s.id=p.store.id and p.id=pq.product.id and o.id=pq.order.id and o.id=:v3 and s.seller.id=u.id and u.id=:v4")
    public  Store storeoforder(@Param("v3") Long id,@Param("v4") Long idSeller);
    @Query("select distinct p from Product p,Store s,ProductQuantity pq,Order o,Pickup pi,User u where s.id=p.store.id and p.id=pq.product.id and o.id=pq.order.id and o.id=:v3 and s.seller.id=u.id and u.id=:v4")
    public  List<Product> productoforder(@Param("v3") Long id, @Param("v4") Long idSeller);
    @Query("select p from Pickup  p ,Request r ,User u,Order o where p.codePickup=:v1 and p.order.buyer.id=:v3 ")
    public Pickup trakingB(@Param("v1") String codeP,@Param("v3") Long idBuyer);

    @Query("select p from Pickup  p where p.codePickup=:v1 and p.store.seller.id=:v3")
    public Pickup trakingS(@Param("v1") String codeP,@Param("v3") Long idSeller);
    @Query("select distinct p from Pickup p,Request r ,User u where r.pickup.id=p.id and r.deliveryman.id=u.id and u.id=:v4 ")
    public List<Pickup> pickupOfDeliveryMenFreelancer(@Param("v4") Long idFreelancer);

    @Query("select distinct p from Pickup p,Request r ,User u where r.pickup.id=p.id and r.Agency.id=u.id and u.id=:v4 ")
    public List<Pickup> pickupOfAgency(@Param("v4") Long idAgency);

    @Query("select distinct p from Pickup p,Request r ,User u ,AgencyBranch ab where r.pickup.id=p.id and r.Agency.id=u.id and u.id=:v4 and r.agencyDeliveryMan.agencyBranch.id=:v5 ")
    public List<Pickup> pickupOfBranch(@Param("v4") Long idAgency,@Param("v5")Long idBranch);

    @Query("select o from User u,Store s,ProductQuantity pq,Product p , Order o where s.seller.id =u.id and u.id=:v4 and s.id=p.store.id and p.id=pq.product.id and o.id=pq.order.id")
    public  List<Order> orderOfstore(@Param("v4") Long idSeller);
    @Query("select distinct p from Pickup p , Store s ,User u,Request r where p.store.seller.id=:id and r.pickup.id=r.id and r.requestStatus='PENDING'")
    public  List<Pickup> PickupBySeller(@Param("id") Long idSeller);
    @Query("select count(distinct r) from Request r,Pickup p where r.pickup.id=p.id and p.store.seller.id=:id1")
    public int countrequest(@Param("id1") Long idSellerr);
    @Query("select u from User u,Pickup p,Store s where p.store.seller.id=u.id and p.id=:v3")
    public User UserOfPickup(@Param("v3") Long idPickup);
    //Stat
    @Query("select count(distinct p) from Pickup p ,Store  s,User u where p.statusPickupSeller='PICKED' and p.store.seller.id=u.id and u.id=:v1 and DATE(p.dateCreationPickup) = CURRENT_DATE ")
    public int countPickupSellerPendingToday(@Param("v1") Long idSeller);
    @Query("select count(distinct p) from Pickup p ,Store  s,User u where p.statusPickupSeller='ONTHEWAY' and p.store.seller.id=u.id and u.id=:v1 and DATE(p.dateCreationPickup) = CURRENT_DATE")
    public int countPickupSelleronTheWayToday(@Param("v1") Long idSeller);
    @Query("select count(distinct p) from Pickup p ,Store  s,User u where p.statusPickupSeller='DELIVERED' and p.store.seller.id=u.id and u.id=:v1 and DATE(p.dateCreationPickup) = CURRENT_DATE")
    public int countPickupSellerDeliveredToday(@Param("v1") Long idSeller);
    @Query("select count(distinct p) from Pickup p ,Store  s,User u where p.statusPickupSeller='RETURN' and p.store.seller.id=u.id and u.id=:v1 and DATE(p.dateCreationPickup) = CURRENT_DATE")
    public int countPickupSellerReturnToday(@Param("v1") Long idSeller);
    @Query("select count(distinct p) from Pickup p ,Store  s,User u where p.statusPickupSeller='REFUNDED' and p.store.seller.id=u.id and u.id=:v1 and DATE(p.dateCreationPickup) = CURRENT_DATE")
    public int countPickupSellerRefundedToday(@Param("v1") Long idSeller);
    @Query("select  count(distinct p) from Pickup p,Request r,User u where r.pickup.id=p.id and p.statusPickupSeller='PICKED' and r.requestStatus='APPROVED' and r.deliveryman.id=:v1 and DATE(p.dateCreationPickup) = CURRENT_DATE")
    public int countPickupDeliveryManFreelancerPendingToday(@Param("v1") Long iDFreelancer);

    @Query("select  count(distinct p) from Pickup p,Request r,User u where r.pickup.id=p.id and p.statusPickupSeller='PICKED' and r.requestStatus='APPROVED' and r.Agency.id=:v1 and DATE(p.dateCreationPickup) = CURRENT_DATE")
    public int countPickupAgencyToday(@Param("v1") Long idAgency);
    @Query("select count(distinct r) from Request r,User u where r.requestStatus='REJECTED' and r.deliveryman.id=:v1 and DATE(r.RequestDate) = CURRENT_DATE")
    public int countRequestRejectedDeliveryManFreelancerToday(@Param("v1") Long idAFreelancer);
    @Query("select count(distinct r) from Request r,User u where r.requestStatus='APPROVED' and r.deliveryman.id=:v1 and DATE(r.RequestDate) = CURRENT_DATE")
    public int countRequestApprovedDeliveryManFreelancerToday(@Param("v1") Long idAFreelancer);
    @Query("select count(distinct r) from Request r,User u where r.requestStatus='APPROVED' and r.Agency.id=:v1 and DATE(r.RequestDate) = CURRENT_DATE")
    public int countRequestApprovedAgencyToday(@Param("v1") Long idAgency);

    @Query("select count(distinct r) from Request r,User u where r.requestStatus='REJECTED' and r.Agency.id=:v1 and DATE(r.RequestDate) = CURRENT_DATE")
    public int countRequestRejectedAgencyToday(@Param("v1") Long idAgency);

    @Query("select  distinct(p) from Pickup p,Request r,User u where  r.pickup.id=p.id and p.statusPickupSeller='DELIVERED' and r.deliveryman.id=:v1   and DATE(r.RequestDate) = CURRENT_DATE ")
    public List<Pickup> SumPricePickupDeliveredByFreelancerToday(@Param("v1") Long idAFreelancer);

    @Query("select  distinct(p) from Pickup p,Request r,User u where  r.pickup.id=p.id and p.statusPickupSeller='DELIVERED' and r.Agency.id=:v1   and DATE(r.RequestDate) = CURRENT_DATE ")
    public List<Pickup> SumPricePickupDeliveredByAgencyToday(@Param("v1") Long idAFreelancer);

    @Query("select distinct p from Product p,ProductQuantity pq,Order o,Pickup pi ,Store s,User u where s.id=p.store.id and p.id=pq.product.id and o.id=pq.order.id and o.id=pi.order.id and pi.id=:v1 and s.seller.id=u.id and s.seller.id=:v2")
    public  List<Product> productofpickup(@Param("v1") Long idPickup,@Param("v2") Long idSeller);
    @Query("select distinct p from Product p,ProductQuantity pq,Order o,Pickup pi ,Store s,User u where  p.id=pq.product.id and o.id=pq.order.id and o.id=pi.order.id and pi.id=:v1 and s.seller.id=u.id and s.seller.id=:v2")
    public List<Product> productOfOrder(@Param("v1") Long idPickup,@Param("v2") Long idSeller);

    ///////////stat Administrator
    @Query("select count(distinct u) from User u,Role r where u.role.type='DELIVERYAGENCY'")
    public int countAgencyAdministrator();
    @Query("select count(distinct u) from User u,Role r where u.role.type='DELIVERYMEN'")
    public int countDeliveryFreelancerAdministrator();
    @Query("select count(distinct p) from Pickup p where p.statusPickupSeller='DELIVERED' and DATE(p.dateCreationPickup) = CURRENT_DATE")
    public int countPickupDeliveredTodayAdministrator();

    @Query("select count(distinct p) from Pickup p where p.statusPickupSeller='ONTHEWAY' and DATE(p.dateCreationPickup) = CURRENT_DATE")
    public int countOfPickupOnTheWayTodayAdministrator();
    @Query("select count(distinct p) from Pickup p where p.statusPickupSeller='RETURN' and DATE(p.dateCreationPickup) = CURRENT_DATE")
    public int countOfPickupReturnedTodayAdministrator();

    @Query("select count(distinct p) from Pickup p where p.statusPickupSeller='DELIVERED' and WEEK(p.dateCreationPickup) = WEEK(CURRENT_DATE)")
    public int countOfPickupDeliveredweekAdministrator();
    @Query("select count(distinct p) from Pickup p where p.statusPickupSeller='ONTHEWAY' and WEEK(p.dateCreationPickup) = WEEK(CURRENT_DATE)")
    public int countOfPickupOnTheWayweekAdministrator();
    @Query("select count(distinct p) from Pickup p where p.statusPickupSeller='RETURN' and WEEK(p.dateCreationPickup) = WEEK(CURRENT_DATE)")
    public int countOfPickupReturnedweekAdministrator();

    @Query("select distinct p from Pickup p where p.statusPickupSeller='DELIVERED' and DATE(p.dateCreationPickup) = CURRENT_DATE")
    public List<Pickup> sumOfPickupDeliveredTodayAdministrator();
    @Query("select distinct p from Pickup p where p.statusPickupSeller='ONTHEWAY' and DATE(p.dateCreationPickup) = CURRENT_DATE")
    public List<Pickup> sumOfPickupOnTheWayTodayAdministrator();

    @Query("select distinct p from Pickup p where p.statusPickupSeller='RETURN' and DATE(p.dateCreationPickup) = CURRENT_DATE")
    public List<Pickup> sumOfPickupReturnedTodayAdministrator();

    @Query("select distinct p from Pickup p where p.statusPickupSeller='DELIVERED' and WEEK(p.dateCreationPickup) = WEEK(CURRENT_DATE)")
    public List<Pickup> sumOfPickupDeliveredweekAdministrator();
    @Query("select distinct p from Pickup p where p.statusPickupSeller='ONTHEWAY' and WEEK(p.dateCreationPickup) = WEEK(CURRENT_DATE)")
    public List<Pickup> sumOfPickupOnTheWayweekAdministrator();

    @Query("select distinct p from Pickup p where p.statusPickupSeller='RETURN' and WEEK(p.dateCreationPickup) = WEEK(CURRENT_DATE)")
    public List<Pickup> sumOfPickupReturnedweekAdministrator();

    //DeliveryAlertCar
    @Query("select distinct p from Pickup p ,Request r,User u where r.deliveryman.id=u.id and r.pickup.id=p.id and u.id=:d5 ")
    public List<Pickup> SumKilometreINCar(@Param("d5") Long idFreelancer);

    @Query("select  p from Pickup p where p.nbRequest>1")
    public List<Pickup> ListePickup();

    @Query("select  distinct p from Pickup p ,Request r,User u where r.pickup.id=p.id and r.requestStatus='APPROVED' and r.Agency.id=:d1")
    public List<Pickup> ListePickupByStatusAPPROVEDRequest(@Param("d1") Long idAgency);

    @Query("select  distinct p from Pickup p ,Request r,User u where r.pickup.id=p.id and r.requestStatus='APPROVED' and r.deliveryman.id=:d1")
    public List<Pickup> ListePickupByStatusAPPROVEDRequestFreelancer(@Param("d1") Long idFreelancer);





}
