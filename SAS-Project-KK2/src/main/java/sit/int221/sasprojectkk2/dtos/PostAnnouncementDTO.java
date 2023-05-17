package sit.int221.sasprojectkk2.dtos;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import sit.int221.sasprojectkk2.exceptions.ValidAnnDisplay;
import sit.int221.sasprojectkk2.exceptions.ValidCategory;
import sit.int221.sasprojectkk2.exceptions.ValidDesc;
import sit.int221.sasprojectkk2.exceptions.ValidTitle;

import java.time.ZonedDateTime;

@Getter
@Setter
public class PostAnnouncementDTO {
//    @Size(min=1, max=200, message = "size must be between 1 and 200 ")
    @ValidTitle
    private String announcementTitle;
    @ValidDesc
//    @Size(min=1, max=10000, message = "size must be between 1 and 10000 ")
    private String announcementDescription;
    private ZonedDateTime publishDate;
    private ZonedDateTime closeDate;
    @ValidAnnDisplay
    private Character announcementDisplay;

    @ValidCategory
    @NotNull(message = "must not be null")
    private Integer categoryId;
}


