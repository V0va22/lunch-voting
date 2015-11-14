package test.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import test.model.User;

@Repository
public interface UserRepository extends CrudRepository<User, String> {
}
