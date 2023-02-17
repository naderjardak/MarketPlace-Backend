package repositories;

import entities.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserrRepository extends CrudRepository<User,Long> {
}
