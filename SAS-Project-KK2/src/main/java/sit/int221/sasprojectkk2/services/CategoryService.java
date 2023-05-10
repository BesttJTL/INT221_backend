package sit.int221.sasprojectkk2.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;
import sit.int221.sasprojectkk2.entities.Category;
import sit.int221.sasprojectkk2.repositories.CategoryRepository;

import java.util.List;

@Service
@CrossOrigin
public class CategoryService {

    @Autowired
    private CategoryRepository repo;

    public List<Category> getAllCategories(){
        List<Category> categories = repo.findAll();
        return categories;
    }

}
