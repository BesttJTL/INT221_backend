package sit.int221.sasprojectkk2.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.Getter;
import lombok.Setter;
import sit.int221.sasprojectkk2.entities.Category;

import java.time.ZonedDateTime;
import java.util.Date;

@Getter
@Setter
public class AnnouncementDetailsDTO {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String announcementTitle;
    private String announcementCategory;
    private String announcementDescription;
    private ZonedDateTime publishDate;
    private ZonedDateTime closeDate;

    @JsonIgnore
    private Category categoryId;

    private Character announcementDisplay;

    public void setCategory(String categoryName) {
        this.announcementCategory = categoryName;
    }

}