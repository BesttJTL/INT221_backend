package sit.int221.sasprojectkk2.dtos;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.stat.internal.CategorizedStatistics;

import java.time.ZonedDateTime;

@Getter
@Setter
public class SortByCategoryDTO {
    private Integer id;
    private String announcementTitle;
//    private ZonedDateTime publishDate;
//    private ZonedDateTime closeDate;
//    private Character announcementDisplay;
    private String announcementCategory;

}
