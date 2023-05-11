package sit.int221.sasprojectkk2.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sit.int221.sasprojectkk2.dtos.*;
import sit.int221.sasprojectkk2.entities.Announcement;
import sit.int221.sasprojectkk2.repositories.AnnouncementRepository;
import sit.int221.sasprojectkk2.services.AnnouncementService;
import sit.int221.sasprojectkk2.services.AnnouncementUserService;

import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/announcements")
@CrossOrigin
public class AnnouncementController {
    @Autowired
    private AnnouncementService service;
    @Autowired
    private AnnouncementUserService userService;
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AnnouncementRepository repo;

    @GetMapping("")
    public List<?> getAll(@RequestParam String mode) {
        if (Objects.equals(mode,"admin")) {
            List<Announcement> announcementList = service.getAllAnnouncements();
            return announcementList.stream().map(c -> {
                AnnouncementDTO announcementDTO = modelMapper.map(c, AnnouncementDTO.class);
                announcementDTO.setCategoryName(c.getCategories_categoryId().getCategoryName());
                return announcementDTO;
            }).collect(Collectors.toList());
        }

        if(Objects.equals(mode,"active")) {
            return userService.returnActiveAnnouncement();
        }
        if(Objects.equals(mode,"close")){
            return userService.returnClosedAnnouncement();
        }
        return Collections.emptyList();
    }
    // admin,active mode Passed !

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

//    @GetMapping("")
//    public List<UserViewDTO> getAllAnnUserView(@RequestParam String mode) {
//        if (Objects.equals(mode, "active")) {
//            List<Announcement> announcementList = userService.getAllUserView();
//            return announcementList.stream().map(c -> {
//                UserViewDTO userViewDTO = modelMapper.map(c, UserViewDTO.class);
//                userViewDTO.setAnnouncementCategory(c.getCategories_categoryId().getCategoryName());
//                return userViewDTO;
//            }).collect(Collectors.toList());
//        }
//        if(Objects.equals(mode,"close")){
//
//        }
//
//        return null;
//    }
}

