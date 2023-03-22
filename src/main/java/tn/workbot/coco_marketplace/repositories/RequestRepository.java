package tn.workbot.coco_marketplace.repositories;

import org.apache.logging.log4j.message.StringFormattedMessage;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tn.workbot.coco_marketplace.entities.Request;

import java.util.List;

@Repository
public interface RequestRepository extends CrudRepository<Request,Long> {
    @Query("select count(r) from Request r,AgencyDeliveryMan adm where r.agencyDeliveryMan.id=:value and r.pickup.id=:v2")
    public int countrequestwithsomedeliverymen(@Param("value")Long deliveryManId,@Param("v2")Long pickupId);
    @Query("select count(r) from Request r where r.requestStatus='APPROVED' and r.pickup.id=:v2")
    public int  verifier(@Param("v2")Long pickupId);
    @Query("select r from Request r where r.requestStatus='PENDING' and r.pickup.id=:v2")
    public List<Request>  verifier2(@Param("v2")Long pickupId);
    @Query("select r from Request r,AgencyDeliveryMan ab,AgencyBranch aa where r.agencyDeliveryMan.id=:v1  ")
    public List<Request> ByDeliveryMen(@Param("v1")Long id);
    @Query("select count(r) from Request r,AgencyDeliveryMan ab,AgencyBranch aa where r.agencyDeliveryMan.agencyBranch.id=:v1 and r.requestStatus='APPROVED'")
    public int countApproved(@Param("v1")Long id);
    @Query("select count(r) from Request r,AgencyDeliveryMan ab,AgencyBranch aa where r.agencyDeliveryMan.id=:v1 and r.requestStatus='APPROVED'")
    public int countApprovedDeliveryAgence(@Param("v1")Long id);
    @Query("select r from Request r,Pickup p,Store s,User u  where r.pickup.id=p.id and p.store.id=s.id and s.seller.id=u.id and u.id=:v6")
    public List<Request> retrieveRequestBystore(@Param("v6")Long id);

    @Query("select r from Request r,Pickup p where r.pickup.id=p.id and p.id=:v7")
    public List<Request> retrieveRequestByPickup(@Param("v7")Long id);

    @Query("select distinct r from Request r where r.deliveryman.id=:v1")
    public List<Request> RetrieveRequestByFreelancer(@Param("v1") Long IdFreelancer);
    @Query("select distinct r from Request r where r.Agency.id=:v1")
    public List<Request> RetrieveRequestByAgency(@Param("v1") Long idAgency);




}
