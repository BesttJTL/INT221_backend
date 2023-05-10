package sit.int221.sasprojectkk2.services;

import org.aspectj.weaver.ast.Not;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;
import sit.int221.sasprojectkk2.dtos.PostAnnouncementDTO;
import sit.int221.sasprojectkk2.entities.Announcement;
import sit.int221.sasprojectkk2.entities.Category;
import sit.int221.sasprojectkk2.exceptions.InvalidDateTimeException;
import sit.int221.sasprojectkk2.exceptions.NoAnnException;
import sit.int221.sasprojectkk2.exceptions.NotFoundException;
import sit.int221.sasprojectkk2.repositories.AnnouncementRepository;
import sit.int221.sasprojectkk2.repositories.CategoryRepository;

import java.time.DateTimeException;
import java.time.ZonedDateTime;
import java.util.List;
@Service
@CrossOrigin
@ControllerAdvice
public class AnnouncementService {
    @Autowired
    private AnnouncementRepository repository;
    @Autowired
    private CategoryRepository categoryRepository;

    public List<Announcement> getAllAnnouncements(){
        List<Announcement> list = repository.findAll();
        list.sort((a,b) -> b.getId() - a.getId());
        return list;
    }

    public Announcement createAnnouncement(PostAnnouncementDTO dto) {
        ZonedDateTime currentDateTime = ZonedDateTime.now();
        if (dto.getAnnouncementTitle() == null) {
            throw new ResourceNotFoundException("Title Cannot be Null!");
        }
        if (dto.getAnnouncementDescription() == null) {
            throw new ResourceNotFoundException("Description Cannot be Null!");
        }
        if(dto.getAnnouncementDescription().length() > 10000){
            throw new RuntimeException("Description is Over-length !");
        }
        if (dto.getPublishDate() != null) {
            if (dto.getPublishDate().isBefore(currentDateTime)) {
                throw new DateTimeException("Publish Date must be greater than or equal current Date/Time !");
            }
            if(dto.getCloseDate().isBefore(currentDateTime)){
                throw new InvalidDateTimeException("Close date must be greater than Current Date/Time !");
            }
            if(dto.getCloseDate().isBefore(dto.getPublishDate())){
                throw new InvalidDateTimeException("Close Date must be greater than Publish Date !");
            }
        }

        Category category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));
        Announcement announcement = new Announcement();
        announcement.setAnnouncementTitle(dto.getAnnouncementTitle());
        announcement.setAnnouncementDescription(dto.getAnnouncementDescription());
        announcement.setPublishDate(dto.getPublishDate());
        announcement.setCloseDate(dto.getCloseDate());
        announcement.setAnnouncementDisplay(dto.getAnnouncementDisplay());
        announcement.setCategories_categoryId(category);
        return repository.saveAndFlush(announcement);
    }

    public Announcement findAnnouncementById(Integer announcementId){
        return repository.findById(announcementId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                "Announcement id" +  announcementId + "" + "does not exist…"));
    }

    public void deleteAnnouncement(Integer announcementId){
        repository.findById(announcementId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"The announcement is not found"));
        repository.deleteById(announcementId);
    }

    public Announcement updateAnnouncement(Integer announcementId, PostAnnouncementDTO dto) {
        Category category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));

        Announcement announcement = repository.findById(announcementId)
                .orElseThrow(() -> new ResourceNotFoundException("Announcement not found"));

        announcement.setAnnouncementTitle(dto.getAnnouncementTitle());
        announcement.setAnnouncementDescription(dto.getAnnouncementDescription());
        announcement.setPublishDate(dto.getPublishDate());
        announcement.setCloseDate(dto.getCloseDate());
        announcement.setAnnouncementDisplay(dto.getAnnouncementDisplay());
        announcement.setCategories_categoryId(category);

        return repository.saveAndFlush(announcement);
    }

}