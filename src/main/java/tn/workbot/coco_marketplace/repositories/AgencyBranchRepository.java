package tn.workbot.coco_marketplace.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tn.workbot.coco_marketplace.entities.AgencyBranch;
import tn.workbot.coco_marketplace.entities.Store;

import java.util.List;

@Repository
public interface AgencyBranchRepository extends CrudRepository<AgencyBranch,Long> {
    List<AgencyBranch> findAll();
    List<AgencyBranch> findByDeliveryAgency(String id);
    @Query("select  count(distinct a) from AgencyBranch  a,User u where a.deliveryAgency.id=:v1 ")
    public int countAgencyBranch(@Param("v1") Long idAgency);
    @Query("select count(distinct adm) from AgencyDeliveryMan adm,AgencyBranch a,User u where adm.agencyBranch.deliveryAgency.id=:v1")
    public int countDeliveryMenInAllBranchtoAgency(@Param("v1") Long idAgency);
    @Query("select distinct ag from AgencyBranch ag,User u where ag.deliveryAgency.id=:v1")
    public List<AgencyBranch> retrieveAgency(@Param("v1")Long idAgency);

    @Query("select count(distinct adm) from AgencyDeliveryMan adm,AgencyBranch a,User  where adm.agencyBranch.id=:v1")
    public int countDeliveryMenInBranchByIdBranch(@Param("v1") Long idAgency);
}
