package sit.int221.sasprojectkk2.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import sit.int221.sasprojectkk2.entities.Announcement;
import sit.int221.sasprojectkk2.entities.Category;
import lombok.Getter;
import lombok.Setter;
import sit.int221.sasprojectkk2.exceptions.ValidCloseDate;
import sit.int221.sasprojectkk2.exceptions.ValidPublishDate;

import java.time.ZonedDateTime;

@Getter
@Setter
public class ResponsePostAnnouncementDTO {
    private Integer id;
    private String announcementTitle;
    private String announcementDescription;
    @ValidPublishDate
    private ZonedDateTime publishDate;
    @ValidCloseDate
    private ZonedDateTime closeDate;
    private String announcementDisplay;
    private String announcementCategory;
    private Integer category;

    public void setAnnouncementCategory(String categoryName) {
        this.announcementCategory = categoryName;
    }
    public void setCategoryId(Integer categoryId) {
        this.category = categoryId;
    }
}

