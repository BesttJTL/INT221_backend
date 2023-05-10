package sit.int221.sasprojectkk2.dtos;

import jakarta.annotation.Nullable;
import lombok.Getter;
import lombok.Setter;
import java.time.ZonedDateTime;

@Getter
@Setter
public class PostAnnouncementDTO {
    private String announcementTitle;
    private String announcementDescription;
    private ZonedDateTime publishDate;
    private ZonedDateTime closeDate;
    private Character announcementDisplay;
    private Integer categoryId;
}


