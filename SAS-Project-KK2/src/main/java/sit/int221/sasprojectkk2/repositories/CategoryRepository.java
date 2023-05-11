package sit.int221.sasprojectkk2.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import sit.int221.sasprojectkk2.entities.Category;

import java.util.List;


public interface CategoryRepository extends JpaRepository<Category,Integer> {


}
