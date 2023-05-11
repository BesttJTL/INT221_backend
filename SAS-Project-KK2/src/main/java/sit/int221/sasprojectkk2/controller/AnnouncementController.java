package sit.int221.sasprojectkk2.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import sit.int221.sasprojectkk2.dtos.*;
import sit.int221.sasprojectkk2.entities.Announcement;
import sit.int221.sasprojectkk2.exceptions.NotFoundException;
import sit.int221.sasprojectkk2.repositories.AnnouncementRepository;
import sit.int221.sasprojectkk2.services.AnnouncementService;
import sit.int221.sasprojectkk2.services.AnnouncementUserService;

import java.security.InvalidParameterException;
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
    public List<?> getAll(@RequestParam(defaultValue = "admin") String mode) {
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
    public Object getAnnouncementById(@PathVariable Integer announcementId, @RequestParam(defaultValue = "admin") String mode) {
                if (Objects.equals(mode,"admin")) {
                    Announcement announcement = service.findAnnouncementById(announcementId);
                    String categoryName = announcement.getCategories_categoryId().getCategoryName();
                    AnnouncementDetailsDTO announcementDetailsDTO = modelMapper.map(announcement, AnnouncementDetailsDTO.class);
                    announcementDetailsDTO.setCategory(categoryName);
                    return announcementDetailsDTO;
                } else if (Objects.equals(mode,"active")) {
                    Announcement announcement = service.findAnnouncementByIdViewPage(announcementId);
                    ActivePageDetailDTO activePageDetailDTO = modelMapper.map(announcement, ActivePageDetailDTO.class);
                    activePageDetailDTO.setAnnouncementCategory(announcement.getCategories_categoryId().getCategoryName());
                    return activePageDetailDTO;
                } else if (Objects.equals(mode,"close")) {
                    Announcement announcement = service.findAnnouncementByIdViewPage(announcementId);
                    ClosePageDetailDTO closePageDetailDTO = modelMapper.map(announcement, ClosePageDetailDTO.class);
                    closePageDetailDTO.setAnnouncementCategory(announcement.getCategories_categoryId().getCategoryName());
                    return closePageDetailDTO;
                }
        return null;
    }

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


    @GetMapping("/page")
    public Page<?> getSortAnnouncement
                                         (@RequestParam String mode,
                                          @RequestParam int category,
                                          @RequestParam Integer size,
                                          @RequestParam Integer page) {

        return userService.sortByCategory(mode,category, size, page);
    }




}

