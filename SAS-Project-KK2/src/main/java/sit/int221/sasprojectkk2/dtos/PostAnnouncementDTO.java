package sit.int221.sasprojectkk2.dtos;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;

@Getter
@Setter
public class PostAnnouncementDTO {
    @NotBlank(message = "Announcement Title cannot be null !")
    @Size(min=1, max=200, message = "size must be between 1 and 200 ")
    private String announcementTitle;
    @Size(min=1, max=10000, message = "size must be between 1 and 10000 ")
    @NotBlank(message = "Announcement Description cannot be null !")
    private String announcementDescription;
//    @ValidPublishDate
    private ZonedDateTime publishDate;
    private ZonedDateTime closeDate;
    private Character announcementDisplay;
    @NotNull(message = "Category cannot be blank !")
    private Integer categoryId;
}


