package sit.int221.sasprojectkk2.dtos;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import sit.int221.sasprojectkk2.exceptions.ValidCategory;
import sit.int221.sasprojectkk2.exceptions.ValidDesc;
import sit.int221.sasprojectkk2.exceptions.ValidDisplay;
import sit.int221.sasprojectkk2.exceptions.ValidTitle;

import java.time.ZonedDateTime;

@Getter
@Setter
public class PostAnnouncementDTO {
    @ValidTitle
    private String announcementTitle;
    @ValidDesc
    private String announcementDescription;
    private ZonedDateTime publishDate;
    private ZonedDateTime closeDate;
    @ValidDisplay
    private Character announcementDisplay;

    @ValidCategory
    private Integer categoryId;
}


