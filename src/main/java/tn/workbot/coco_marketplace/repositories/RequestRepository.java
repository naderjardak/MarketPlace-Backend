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
}
