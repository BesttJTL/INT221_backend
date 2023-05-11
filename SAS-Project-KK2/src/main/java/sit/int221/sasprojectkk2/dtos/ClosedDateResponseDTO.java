package sit.int221.sasprojectkk2.dtos;

import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;

@Getter
@Setter
public class ClosedDateResponseDTO {
    private String announcementTitle;
    private String announcementCategory;
    private ZonedDateTime closeDate;
}
