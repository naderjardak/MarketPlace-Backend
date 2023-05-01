package tn.workbot.coco_marketplace.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tn.workbot.coco_marketplace.entities.AgencyBranch;
import tn.workbot.coco_marketplace.entities.AgencyDeliveryMan;

import java.util.List;

@Repository
public interface AgencyDeliveryManRepository extends CrudRepository<AgencyDeliveryMan, Long> {
    @Query("select distinct dm from AgencyDeliveryMan dm ,AgencyBranch ab,User u,Store s,Pickup p where dm.agencyBranch.deliveryAgency.id=u.id and u.id=:v1 and  p.store.id=s.id and p.id=:v2 and dm.governorate=p.governorate")
    public List<AgencyDeliveryMan> deliveryMenByAgency(@Param("v1") Long idAgency, @Param("v2") Long idPickup);

    @Query("select distinct dm from AgencyDeliveryMan dm,AgencyBranch  where dm.agencyBranch.id=:v1")
    public List<AgencyDeliveryMan> retrieveDMbYbRANCHE(@Param("v1") Long idBranch);
    @Query("select count(distinct dm) from AgencyDeliveryMan dm ,AgencyBranch ab where dm.agencyBranch.id=:v1")
    public int countDmInBranch(@Param("v1") Long idBranch);
    @Query("select  distinct dm from AgencyDeliveryMan  dm,Request r,User u,Pickup p where r.Agency.id=:v1 and r.agencyDeliveryMan.id=dm.id and r.pickup.id=p.id group by dm order by COUNT (p) desc ")
    public List<AgencyDeliveryMan> getATopDeliveryMen(@Param("v1")Long idUser);

    @Query("select  distinct ab from AgencyBranch ab,AgencyDeliveryMan  dm,Request r,User u,Pickup p where r.Agency.id=:v1 and r.agencyDeliveryMan.id=dm.id and r.agencyDeliveryMan.agencyBranch.id=ab.id and r.pickup.id=p.id group by ab order by COUNT (p) desc ")
    public List<AgencyBranch> TopDeliveryAgencyByPickupDelivered(@Param("v1")Long idUser);
}
