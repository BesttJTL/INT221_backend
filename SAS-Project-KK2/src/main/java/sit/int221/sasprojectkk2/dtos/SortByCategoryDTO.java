package sit.int221.sasprojectkk2.dtos;

import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;

@Getter
@Setter
public class SortByCategoryDTO {
    private Integer Id;
    private String announcementTitle;
    private ZonedDateTime publishDate;
    private ZonedDateTime closeDate;
    private Character announcementDisplay;
    private String announcementCategory;
    private Integer categoryId;

    public void setCategoryId(Integer categoryId){
        this.categoryId = categoryId;
    }

}
