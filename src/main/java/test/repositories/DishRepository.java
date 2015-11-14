package test.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import test.model.Dish;

@Repository
public interface DishRepository extends CrudRepository<Dish, Long>{
}
