package sit.int221.sasprojectkk2.dtos;

import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;

@Getter
@Setter
public class ClosePageDetailDTO {
    private Integer announcementId;
    private String announcementTitle;
    private String announcementDescription;
    private String announcementCategory;
    private ZonedDateTime closeDate;
}
