package sit.int221.sasprojectkk2.dtos;

import lombok.Getter;
import lombok.Setter;
import net.minidev.json.annotate.JsonIgnore;

@Getter
@Setter
public class UserViewDTO {
    @JsonIgnore
    private Integer announcementId;
    private String announcementTitle;
    private String announcementCategory;
}
