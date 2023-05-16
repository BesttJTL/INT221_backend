package sit.int221.sasprojectkk2.controller;

import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import sit.int221.sasprojectkk2.dtos.*;
import sit.int221.sasprojectkk2.entities.Announcement;
import sit.int221.sasprojectkk2.exceptions.InvalidDateTimeException;
import sit.int221.sasprojectkk2.repositories.AnnouncementRepository;
import sit.int221.sasprojectkk2.services.AnnouncementService;
import sit.int221.sasprojectkk2.services.AnnouncementUserService;
import sit.int221.sasprojectkk2.utils.ListMapper;

import java.util.ArrayList;
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
    private ListMapper listMapper;
    @Autowired
    private AnnouncementRepository repo;

    @GetMapping("")
    public List<?> getAll(@RequestParam(defaultValue = "admin") String mode) {
        if (Objects.equals(mode,"admin")) {
            List<Announcement> announcementList = service.getAllAnnouncements();
            System.out.println("Ann length: "+announcementList.size());
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
    public ResponsePostAnnouncementDTO createAnnouncement(@Valid @RequestBody PostAnnouncementDTO dto,BindingResult result) {
        if(result.hasErrors()){
            List<ObjectError> errors = result.getAllErrors();
            List<String> errorMessages = new ArrayList<>();
            for (ObjectError error : errors) {
                errorMessages.add(error.getDefaultMessage());
            }
            throw new InvalidDateTimeException(errorMessages.toString());
        }
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


    // URI: /page with pagination (PBI10)
    @GetMapping("/pages")
    public PageDto<AnnouncementDTO> getSortAnnouncement(@RequestParam(defaultValue = "active") String mode,
                                          @RequestParam(defaultValue = "5") Integer size,
                                          @RequestParam(required = false) Integer category,
                                          @RequestParam(defaultValue = "0") Integer page) {
        Page<?> x = userService.userViewPage(mode, size,category, page);
        return listMapper.toPageDTO(x,AnnouncementDTO.class,modelMapper);
    }

//    @GetMapping("/test")
//    public Page<?> test(@RequestParam int size, @RequestParam int page){
//        return userService.getAllUserViewPageable(size,page);
//    }




}

