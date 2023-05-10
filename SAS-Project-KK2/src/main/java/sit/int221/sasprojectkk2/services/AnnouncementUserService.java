package sit.int221.sasprojectkk2.services;

import org.aspectj.weaver.ast.Instanceof;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.CrossOrigin;
import sit.int221.sasprojectkk2.entities.Announcement;
import sit.int221.sasprojectkk2.repositories.AnnouncementRepository;

import java.time.ZoneId;
import java.time.ZonedDateTime;


@Service
@CrossOrigin
@ControllerAdvice
public class AnnouncementUserService {
    @Autowired
    private AnnouncementRepository announcementRepository;

    public Page<Announcement> getAllUserView(int page, int size){
        Pageable pageable = PageRequest.of(page, size);
        return announcementRepository.findAll(pageable);
    }

    public String testValidateDate() {
        ZonedDateTime zonedDateTime = ZonedDateTime.of(2023, 5, 9, 10, 30, 0, 0, ZoneId.of("Asia/Bangkok"));
        ZonedDateTime currentZonedDateTime = ZonedDateTime.now();
        if(currentZonedDateTime.isBefore(zonedDateTime)){
            System.out.println("Date/Time cannot greater than current time !");
        }
        if(currentZonedDateTime.isAfter(zonedDateTime)){
            System.out.println("Date/Time cannot less than current time !");
        }


        return null;
    }

}
