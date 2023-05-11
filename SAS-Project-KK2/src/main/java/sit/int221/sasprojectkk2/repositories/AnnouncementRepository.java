package sit.int221.sasprojectkk2.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import sit.int221.sasprojectkk2.dtos.AnnouncementDTO;
import sit.int221.sasprojectkk2.dtos.SortByCategoryDTO;
import sit.int221.sasprojectkk2.dtos.UserViewDTO;
import sit.int221.sasprojectkk2.entities.Announcement;

import java.util.List;

import static org.hibernate.FetchMode.SELECT;

public interface AnnouncementRepository extends JpaRepository<Announcement, Integer> {
    @Query(value = "SELECT a FROM Announcement a JOIN FETCH a.Categories_categoryId c WHERE c.categoryId = :categoryId",
            countQuery = "SELECT COUNT(a) FROM Announcement a JOIN a.Categories_categoryId c WHERE c.categoryId = :categoryId")
    Page<Announcement> findAnnouncementByCategoryId(int categoryId, Pageable pageable);

    @Query(value = "SELECT a FROM Announcement a JOIN a.Categories_categoryId c WHERE c.categoryId = :categoryId")
    List<Announcement> findAnnouncementByCategoryLessThan5(int categoryId);
}
