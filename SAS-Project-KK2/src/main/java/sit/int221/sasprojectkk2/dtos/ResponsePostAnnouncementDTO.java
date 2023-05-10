package sit.int221.sasprojectkk2.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import sit.int221.sasprojectkk2.entities.Category;
import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;

@Getter
@Setter
public class ResponsePostAnnouncementDTO {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String announcementTitle;
    private String announcementDescription;
    private ZonedDateTime publishDate;
    private ZonedDateTime closeDate;
    private Character announcementDisplay;
    private String announcementCategory;
    private Integer categoryId;



    public void setCategory(String categoryName) {
        this.announcementCategory = categoryName;
    }

}
