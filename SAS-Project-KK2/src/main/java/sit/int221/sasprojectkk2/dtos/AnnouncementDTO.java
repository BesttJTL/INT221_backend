package sit.int221.sasprojectkk2.dtos;

import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;
import java.util.Date;

@Getter
@Setter
public class AnnouncementDTO {
    private Integer Id;
    private String announcementTitle;
    private ZonedDateTime publishDate;
    private ZonedDateTime closeDate;
    private Character announcementDisplay;
    private String announcementCategory;

    public void setCategoryName(String categoryName) {
        this.announcementCategory = categoryName;
    }
}
