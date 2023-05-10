package sit.int221.sasprojectkk2.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import sit.int221.sasprojectkk2.entities.Announcement;

public interface AnnouncementRepository extends JpaRepository<Announcement,Integer> {
}
