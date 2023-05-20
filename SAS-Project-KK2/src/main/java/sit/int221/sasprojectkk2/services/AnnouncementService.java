package sit.int221.sasprojectkk2.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.server.ResponseStatusException;
import sit.int221.sasprojectkk2.dtos.PostAnnouncementDTO;
import sit.int221.sasprojectkk2.entities.Announcement;
import sit.int221.sasprojectkk2.entities.Category;
import sit.int221.sasprojectkk2.exceptions.InvalidDateTimeException;
import sit.int221.sasprojectkk2.exceptions.NotFoundException;
import sit.int221.sasprojectkk2.repositories.AnnouncementRepository;
import sit.int221.sasprojectkk2.repositories.CategoryRepository;

import java.util.List;

@Service
@CrossOrigin
@ControllerAdvice
@Validated
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

    public Announcement createAnnouncement(PostAnnouncementDTO dto)  {
//        if(dto.getPublishDate() !=null && dto.getPublishDate().isBefore(currentDateTime)) {
//            throw new InvalidDateTimeException("must be a date in the present or in the future");
//        }
//        if(dto.getPublishDate() !=null && dto.getCloseDate().isBefore(dto.getPublishDate())) {
//            throw new InvalidDateTimeException("must be later than publish date");
//        }
//        if(dto.getCloseDate().isBefore(currentDateTime)){
//            throw new InvalidDateTimeException("must be a future date");
//        }
        Category category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("must not be null "));

        Announcement announcement = new Announcement();
        announcement.setAnnouncementTitle(dto.getAnnouncementTitle());
        announcement.setAnnouncementDescription(dto.getAnnouncementDescription());
        announcement.setPublishDate(dto.getPublishDate());
        announcement.setCloseDate(dto.getCloseDate());
        if (dto.getAnnouncementDisplay() == null ) {
            dto.setAnnouncementDisplay("N");
        }
//        if (dto.getAnnouncementDisplay().equals("")){
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"ddd");
//        }
        announcement.setAnnouncementDisplay(dto.getAnnouncementDisplay());
        announcement.setCategories_categoryId(category);
        return repository.saveAndFlush(announcement);
    }

    public Announcement findAnnouncementById(Integer announcementId){
        return repository.findById(announcementId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                "Announcement id" +  announcementId + " " + "does not exist"));
    }

    public Announcement findAnnouncementByIdViewPage(Integer announcementId){
        return repository.findById(announcementId).orElseThrow(() -> new NotFoundException("The requested page is not available"));
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