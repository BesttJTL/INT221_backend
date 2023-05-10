package sit.int221.sasprojectkk2.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.ZonedDateTime;
@Getter
@Setter
@Entity
@Table(name = "announcement")
@NoArgsConstructor
@AllArgsConstructor
public class Announcement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "announcementId")
    private Integer id;
    private String announcementTitle;
    private String announcementDescription;
    private ZonedDateTime publishDate;
    private ZonedDateTime closeDate;
    private Character announcementDisplay;
    @ManyToOne
    @JoinColumn(name = "Categories_categoryId")
    private Category Categories_categoryId;

//    public String getCategories_categoryIdName(String categoryName){
//        this.announcementCategory = Categories_categoryId.getCategoryName();
//        return announcementCategory;
//    }
}