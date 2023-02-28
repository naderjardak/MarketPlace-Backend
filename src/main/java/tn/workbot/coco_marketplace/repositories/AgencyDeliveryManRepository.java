package tn.workbot.coco_marketplace.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tn.workbot.coco_marketplace.entities.AgencyDeliveryMan;

import java.util.List;

@Repository
public interface AgencyDeliveryManRepository extends CrudRepository<AgencyDeliveryMan,Long> {
@Query("select distinct dm from AgencyDeliveryMan dm ,AgencyBranch ab,User u,Store s,Pickup p where dm.agencyBranch.deliveryAgency.id=u.id and u.id=:v1 and  p.store.id=s.id and p.id=:v2 and dm.governorate=s.governorate")
    public List<AgencyDeliveryMan> deliveryMenByAgency(@Param("v1") Long idAgency,@Param("v2") Long idPickup);
}
