package sit.int221.sasprojectkk2.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryDTO {
    private Integer categoryId;
    private String categoryName;
    public CategoryDTO(int categoryId){
        this.categoryId = categoryId;
    }
}
