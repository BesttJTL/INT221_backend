package sit.int221.sasprojectkk2.controller;

import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import sit.int221.sasprojectkk2.dtos.*;
import sit.int221.sasprojectkk2.entities.Announcement;
import sit.int221.sasprojectkk2.exceptions.ErrorResponse;
import sit.int221.sasprojectkk2.exceptions.NotFoundException;
import sit.int221.sasprojectkk2.repositories.AnnouncementRepository;
import sit.int221.sasprojectkk2.services.AnnouncementService;
import sit.int221.sasprojectkk2.services.AnnouncementUserService;
import sit.int221.sasprojectkk2.utils.ListMapper;

import java.util.*;
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
    public ResponseEntity<?> createAnnouncement(@Valid @RequestBody PostAnnouncementDTO dto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            ErrorResponse errorResponse = new ErrorResponse();
            List<ErrorResponse.DetailError> detailErrors = new ArrayList<>();
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            for (FieldError fieldError : fieldErrors) {
                ErrorResponse.DetailError detailError = new ErrorResponse.DetailError();
                detailError.setField(fieldError.getField());
                detailError.setErrorMessage(fieldError.getDefaultMessage());
                detailErrors.add(detailError);
            }
            errorResponse.setDetail(detailErrors);
            return ResponseEntity.badRequest().body(errorResponse);
        }
        try {
            Announcement announcement = service.createAnnouncement(dto);
            ResponsePostAnnouncementDTO responsePostAnnouncementDTO = modelMapper.map(dto, ResponsePostAnnouncementDTO.class);
            responsePostAnnouncementDTO.setId(announcement.getId()); // Announcement Id
            responsePostAnnouncementDTO.setAnnouncementCategory(announcement.getCategories_categoryId().getCategoryName()); // Announcement Category Name
            return ResponseEntity.ok(responsePostAnnouncementDTO);
        } catch (ResourceNotFoundException e) {
            ErrorResponse.DetailError errorResponse = new ErrorResponse.DetailError();
            errorResponse.setField("categoryId");
            errorResponse.setErrorMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }


    @PutMapping("/{announcementId}")
    public ResponsePostAnnouncementDTO updateAnnouncement(@PathVariable Integer announcementId, @RequestBody PostAnnouncementDTO dto, BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            ErrorResponse errorResponse = new ErrorResponse();
            List<ErrorResponse.DetailError> detailErrors = new ArrayList<>();
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            for (FieldError fieldError : fieldErrors) {
                ErrorResponse.DetailError detailError = new ErrorResponse.DetailError();
                detailError.setField(fieldError.getField());
                detailError.setErrorMessage(fieldError.getDefaultMessage());
                detailErrors.add(detailError);
            }
            errorResponse.setDetail(detailErrors);
        }
        Announcement announcement = service.updateAnnouncement(announcementId, dto);
        String categoryName = announcement.getCategories_categoryId().getCategoryName();
        ResponsePostAnnouncementDTO responsePostAnnouncementDTO = modelMapper.map(dto, ResponsePostAnnouncementDTO.class);
        responsePostAnnouncementDTO.setAnnouncementCategory(categoryName);
        return responsePostAnnouncementDTO;
    }


    @DeleteMapping("/{announcementId}")
    public void deleteEmployees(@PathVariable Integer announcementId){
         service.deleteAnnouncement(announcementId);
    }


    @GetMapping("/pages")
    public PageDto<AnnouncementDTO> getSortAnnouncement(@RequestParam(defaultValue = "active") String mode,
                                          @RequestParam(defaultValue = "5") Integer size,
                                          @RequestParam(required = false) Integer category,
                                          @RequestParam(defaultValue = "0") Integer page) {
        Page<?> x = userService.userViewPage(mode, size,category, page);
        return listMapper.toPageDTO(x,AnnouncementDTO.class,modelMapper);
    }

}

