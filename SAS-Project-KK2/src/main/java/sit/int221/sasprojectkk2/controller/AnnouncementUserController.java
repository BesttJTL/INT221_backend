package sit.int221.sasprojectkk2.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import sit.int221.sasprojectkk2.dtos.AnnouncementDTO;
import sit.int221.sasprojectkk2.entities.Announcement;
import sit.int221.sasprojectkk2.services.AnnouncementUserService;

import java.util.List;
import java.util.stream.Collectors;
import java.util.zip.DataFormatException;

@RestController
@RequestMapping("/announcements")

public class AnnouncementUserController  {
    @Autowired
    private AnnouncementUserService service;
    @Autowired
    private ModelMapper modelMapper;

    @GetMapping("")
    public Page<AnnouncementDTO> getAllAnnUserView(@RequestParam(defaultValue = "0") Integer page,
                                                   @RequestParam(defaultValue = "5") Integer size) {
        Page<Announcement> announcementList = service.getAllUserView(page, size);
        Page<AnnouncementDTO> announcementDTOPage = announcementList.map(c -> {
            AnnouncementDTO announcementDTO = modelMapper.map(c, AnnouncementDTO.class);
            announcementDTO.setCategoryName(c.getCategories_categoryId().getCategoryName());
            return announcementDTO;
        });
        return announcementDTOPage;
    }


    @GetMapping("/date")
    public String testValidateDate() throws DataFormatException {
        return service.testValidateDate();
    }


}
