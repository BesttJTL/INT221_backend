package sit.int221.sasprojectkk2.dtos;

import lombok.Getter;
import lombok.Setter;
import sit.int221.sasprojectkk2.exceptions.ValidCloseDate;
import sit.int221.sasprojectkk2.exceptions.ValidPublishDate;

import java.time.ZonedDateTime;

@Getter
@Setter
public class AnnouncementDTO {
    private Integer Id;
    private String announcementTitle;
    @ValidPublishDate
    private ZonedDateTime publishDate;
    @ValidCloseDate
    private ZonedDateTime closeDate;
    private String announcementDisplay;
    private String announcementCategory;

    public void setCategoryName(String categoryName) {
        this.announcementCategory = categoryName;
    }
}
