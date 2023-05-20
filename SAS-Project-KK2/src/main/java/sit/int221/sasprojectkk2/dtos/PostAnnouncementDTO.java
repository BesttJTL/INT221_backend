package sit.int221.sasprojectkk2.dtos;
import lombok.Getter;
import lombok.Setter;
import sit.int221.sasprojectkk2.exceptions.*;

import java.time.ZonedDateTime;

@Getter
@Setter
public class PostAnnouncementDTO {
    @ValidTitle
    private String announcementTitle;
    @ValidDesc
    private String announcementDescription;
    @ValidPublishDate
    private ZonedDateTime publishDate;
    @ValidCloseDate
    private ZonedDateTime closeDate;
    @ValidDisplay
    private String announcementDisplay;
    @ValidCategory
    private Integer categoryId;
}


