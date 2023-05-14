package sit.int221.sasprojectkk2.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import sit.int221.sasprojectkk2.entities.Category;
import sit.int221.sasprojectkk2.services.AnnouncementUserService;
import sit.int221.sasprojectkk2.services.CategoryService;

import java.util.List;

@RestController
@RequestMapping("api/categories")
@CrossOrigin
public class CategoryController {
    @Autowired
    private CategoryService service;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AnnouncementUserService userService;
    @GetMapping("")
    public Page<?> getSortByCategory(@RequestParam int category ,
                                     @RequestParam(defaultValue = "5") int size,
                                     @RequestParam(defaultValue = "0") int page){
        return userService.sortByCategories(category,size,page);
    }


}

