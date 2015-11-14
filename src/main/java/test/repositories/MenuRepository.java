package test.repositories;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import test.model.Menu;

import java.util.Date;

@Repository
public interface MenuRepository extends CrudRepository<Menu, Long>, JpaSpecificationExecutor<Menu> {

    Iterable<Menu> findByDate(Date date);
}
