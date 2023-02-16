package repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AgencyBranchRepository extends CrudRepository<AgencyBranchRepository,Long> {

}
