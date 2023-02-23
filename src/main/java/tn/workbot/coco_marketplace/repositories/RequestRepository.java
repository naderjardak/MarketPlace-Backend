package tn.workbot.coco_marketplace.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import tn.workbot.coco_marketplace.entities.Request;

@Repository
public interface RequestRepository extends CrudRepository<Request,Long> {
    @Query("select count(r) from Request r,AgencyDeliveryMan adm where r.agencyDeliveryMan.id=adm.id")
    public int countrequestwithsomedeliverymen();
}
