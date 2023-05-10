package sit.int221.sasprojectkk2.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.web.bind.annotation.*;
import sit.int221.sasprojectkk2.dtos.AnnouncementDTO;
import sit.int221.sasprojectkk2.dtos.AnnouncementDetailsDTO;
import sit.int221.sasprojectkk2.dtos.PostAnnouncementDTO;
import sit.int221.sasprojectkk2.dtos.ResponsePostAnnouncementDTO;
import sit.int221.sasprojectkk2.entities.Announcement;
import sit.int221.sasprojectkk2.repositories.AnnouncementRepository;
import sit.int221.sasprojectkk2.services.AnnouncementService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/announcements")
@CrossOrigin
public class AnnouncementController {
    @Autowired
    private AnnouncementService service;
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AnnouncementRepository repo;

    @GetMapping()
    public List<AnnouncementDTO> getAll(){
        List<Announcement> announcementList = service.getAllAnnouncements();
        return announcementList.stream().map(c -> {
            AnnouncementDTO announcementDTO = modelMapper.map(c, AnnouncementDTO.class);
            announcementDTO.setCategoryName(c.getCategories_categoryId().getCategoryName());
            return announcementDTO;
        }).collect(Collectors.toList());
    }
    //pass

    @GetMapping("/{announcementId}")
    public AnnouncementDetailsDTO getAnnouncementById(@PathVariable Integer announcementId){
        Announcement announcement = service.findAnnouncementById(announcementId);
        String categoryName = announcement.getCategories_categoryId().getCategoryName();
        AnnouncementDetailsDTO announcementDetailsDTO = modelMapper.map(announcement, AnnouncementDetailsDTO.class);
        announcementDetailsDTO.setCategory(categoryName);
        return announcementDetailsDTO;
    }
    //pass

    @PostMapping()
    public ResponsePostAnnouncementDTO createAnnouncement(@RequestBody PostAnnouncementDTO dto) {
//        if (dto.getAnnouncementTitle() == null || dto.getAnnouncementTitle().length() == 0 || dto.getAnnouncementTitle().isBlank()) {
//            throw new ResourceNotFoundException("Title Cannot be Null!");
//        }
//        if (dto.getAnnouncementDescription() == null || dto.getAnnouncementDescription().length() == 0 || dto.getAnnouncementDescription().isBlank()) {
//            throw new ResourceNotFoundException("Description Cannot be Null!");
//        }
//        if(dto.getCategoryId() == null){
//            throw new ResourceNotFoundException("Category Cannot be Null !");
//        }
//        if(dto.getAnnouncementDescription().length() > 10000){
//            throw new RuntimeException("Description is Over-length !");
//        }
        Announcement announcement = service.createAnnouncement(dto);
        ResponsePostAnnouncementDTO responsePostAnnouncementDTO = modelMapper.map(dto, ResponsePostAnnouncementDTO.class);
        responsePostAnnouncementDTO.setId(announcement.getId());
        responsePostAnnouncementDTO.setCategory(announcement.getCategories_categoryId().getCategoryName());
        return responsePostAnnouncementDTO;
    }

    @PutMapping("/{announcementId}")
    public ResponsePostAnnouncementDTO updateAnnouncement(@PathVariable Integer announcementId, @RequestBody PostAnnouncementDTO dto) {
        Announcement announcement = service.updateAnnouncement(announcementId, dto);
        String categoryName = announcement.getCategories_categoryId().getCategoryName();
        ResponsePostAnnouncementDTO responsePostAnnouncementDTO = modelMapper.map(dto, ResponsePostAnnouncementDTO.class);
        responsePostAnnouncementDTO.setCategory(categoryName);
        return responsePostAnnouncementDTO;
    }


    @DeleteMapping("/{announcementId}")
    public void deleteEmployees(@PathVariable Integer announcementId){
         service.deleteAnnouncement(announcementId);
    }

}
