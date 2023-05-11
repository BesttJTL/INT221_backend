package sit.int221.sasprojectkk2.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import sit.int221.sasprojectkk2.dtos.AnnouncementDTO;
import sit.int221.sasprojectkk2.dtos.UserViewDTO;
import sit.int221.sasprojectkk2.entities.Announcement;
import sit.int221.sasprojectkk2.services.AnnouncementUserService;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/announcements")

public class AnnouncementUserController  {
    @Autowired
    private AnnouncementUserService service;
    @Autowired
    private ModelMapper modelMapper;




    @GetMapping("/sort")
    public Page<Announcement> getSortAnnouncement(@RequestParam int categoryName,
                                                  @RequestParam Integer size,
                                                  @RequestParam Integer page) {
        return service.sortByCategory(categoryName, size, page);
    }

}

