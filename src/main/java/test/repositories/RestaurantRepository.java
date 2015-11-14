package test.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import test.model.Restaurant;

@Repository
public interface RestaurantRepository extends CrudRepository<Restaurant, Long> {
}
