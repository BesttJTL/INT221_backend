package sit.int221.sasprojectkk2.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sit.int221.sasprojectkk2.entities.Category;
import sit.int221.sasprojectkk2.services.CategoryService;

import java.util.List;

@RestController
@RequestMapping("api/category")
@CrossOrigin
public class CategoryController {
    @Autowired
    private CategoryService service;

    @Autowired
    private ModelMapper modelMapper;
    @GetMapping()
    public List<Category> getAllCategory() {
        List<Category> categories = service.getAllCategories();
//        List<CategoryNameDTO> categoryNameDTOS = categories.stream().map(c -> {
//            CategoryNameDTO categoryNameDTO = modelMapper.map(c,CategoryNameDTO.class);
//            return categoryNameDTO;
//        }).collect(Collectors.toList());
        return categories;
    }

}

