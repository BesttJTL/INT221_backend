package sit.int221.sasprojectkk2.services;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.CrossOrigin;
import sit.int221.sasprojectkk2.dtos.SortByCategoryDTO;
import sit.int221.sasprojectkk2.dtos.UserViewDTO;
import sit.int221.sasprojectkk2.entities.Announcement;
import sit.int221.sasprojectkk2.repositories.AnnouncementRepository;

import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@CrossOrigin
@ControllerAdvice
public class AnnouncementUserService {
    @Autowired
    private AnnouncementRepository announcementRepository;

    @Autowired
    private ModelMapper modelMapper;


    public List<Announcement> getAllUserView() {
        List<Announcement> announcementList = announcementRepository.findAll();
        return announcementList;
    }

    public List<UserViewDTO> returnActiveAnnouncement() {
        List<Announcement> announcementList = announcementRepository.findAll();
        List<UserViewDTO> validDisplay = announcementList.stream()
                .filter(announcement -> 'Y' == announcement.getAnnouncementDisplay())
                .filter(announcement -> {
                    ZonedDateTime currentTime = ZonedDateTime.now();
                    ZonedDateTime publishDate = announcement.getPublishDate();
                    ZonedDateTime closeDate = announcement.getCloseDate();

                    if (publishDate == null && closeDate == null) {
                        return true;
                    }

                    if (publishDate != null && closeDate == null && (publishDate.isBefore(currentTime) || publishDate.isEqual(currentTime))) {
                        return true;
                    }

                    if (publishDate != null && closeDate != null && (closeDate.isAfter(currentTime) && publishDate.isBefore(currentTime) || publishDate.isEqual(currentTime))) {
                        return true;
                    }

                    if (publishDate == null && closeDate.isAfter(currentTime)) {
                        return true;
                    }

                    return false;
                })
                .map(c -> {
                    UserViewDTO userViewDTO = modelMapper.map(c, UserViewDTO.class);
                    userViewDTO.setAnnouncementCategory(c.getCategories_categoryId().getCategoryName());
                    return userViewDTO;
                }).collect(Collectors.toList());

        List<UserViewDTO> result = validDisplay.stream()
                .map(announcement -> modelMapper.map(announcement, UserViewDTO.class))
                .collect(Collectors.toList());
        result.sort((a, b) -> b.getAnnouncementId() - a.getAnnouncementId());
        return result;

    }

    public List<UserViewDTO> returnClosedAnnouncement() {
        List<Announcement> announcementList = announcementRepository.findAll();
        List<UserViewDTO> validDisplay = announcementList.stream()
                .filter(announcement -> 'Y' == announcement.getAnnouncementDisplay())
                .filter(announcement -> {
                    ZonedDateTime currentTime = ZonedDateTime.now();
                    ZonedDateTime closeDate = announcement.getCloseDate();
                    if (closeDate != null && (closeDate.isBefore(currentTime) || closeDate.isEqual(currentTime))) {
                        return true;
                    }
                    return false;
                }).map(c -> {
                    UserViewDTO userViewDTO = modelMapper.map(c, UserViewDTO.class);
                    userViewDTO.setAnnouncementCategory(c.getCategories_categoryId().getCategoryName());
                    return userViewDTO;
                }).collect(Collectors.toList());

        List<UserViewDTO> userViewDTOS = validDisplay.stream()
                .map(ann -> modelMapper.map(ann, UserViewDTO.class))
                .collect(Collectors.toList());
        userViewDTOS.sort((a, b) -> b.getAnnouncementId() - a.getAnnouncementId());
        return userViewDTOS;
    }

    public List<?> userViewPage(String mode, int size, int page) {
        if (Objects.equals(mode, "active")) {
            Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
            Page<Announcement> announcementPage = announcementRepository.findAllAnnouncements(pageable);
            System.out.println(announcementPage.getContent().get(0).getAnnouncementTitle());
//            System.out.println("Total Element in Category" + categoryId + ": " + announcementPage.getTotalElements());
            if (announcementPage.getTotalElements() > 5) {
                List<SortByCategoryDTO> sortByCategoryDTOS = announcementPage.getContent().stream()
                        .map(announcement -> {
                            SortByCategoryDTO sortByCategoryDTO = modelMapper.map(announcement, SortByCategoryDTO.class);
                            sortByCategoryDTO.setAnnouncementCategory(announcement.getCategories_categoryId().getCategoryName());
                            return sortByCategoryDTO;
                        })
                        .collect(Collectors.toList());
                return Collections.singletonList(new PageImpl<>(sortByCategoryDTOS, pageable, announcementPage.getTotalElements()));
            }
        }

        if(Objects.equals(mode,"close")){
            Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
            Page<Announcement> announcementPage = announcementRepository.findAllAnnouncements(pageable);
            List<UserViewDTO> validDisplay = announcementPage.stream()
                    .filter(announcement -> 'Y' == announcement.getAnnouncementDisplay())
                    .filter(announcement -> {
                        ZonedDateTime currentTime = ZonedDateTime.now();
                        ZonedDateTime closeDate = announcement.getCloseDate();
                        if (closeDate != null && (closeDate.isBefore(currentTime) || closeDate.isEqual(currentTime))) {
                            return true;
                        }
                        return false;
                    }).map(c -> {
                        UserViewDTO userViewDTO = modelMapper.map(c, UserViewDTO.class);
                        userViewDTO.setAnnouncementCategory(c.getCategories_categoryId().getCategoryName());
                        return userViewDTO;
                    }).collect(Collectors.toList());
            return Collections.singletonList(new PageImpl<>(validDisplay,pageable, announcementPage.getTotalElements()));
        }
        return getAnnouncementNoPageable();
    }


    public List<UserViewDTO> getAnnouncementNoPageable() {
        List<Announcement> announcementList = announcementRepository.findAll();
        List<UserViewDTO> userViewDTOList = announcementList.stream()
                .map(announcement -> {
                    UserViewDTO userViewDTO = modelMapper.map(announcement, UserViewDTO.class);
                    userViewDTO.setAnnouncementCategory(announcement.getCategories_categoryId().getCategoryName());
                    return userViewDTO;
                })
                .collect(Collectors.toList());
        Collections.reverse(userViewDTOList);
        return userViewDTOList;
    }

    public Page<?> sortByCategories(Integer category, int size, int page){
        Pageable pageable = PageRequest.of(size,page,Sort.by("id").descending());
        Page<Announcement> sortByCategoryDTOS = announcementRepository.findAnnouncementByCategoryId(category,pageable);
        sortByCategoryDTOS.stream().map(announcement -> {
            SortByCategoryDTO sortByCategoryDTO = modelMapper.map(announcement, SortByCategoryDTO.class);
            sortByCategoryDTO.setAnnouncementCategory(announcement.getCategories_categoryId().getCategoryName());
            return sortByCategoryDTO;
        }).collect(Collectors.toList());
        Collections.reverse((List<?>) sortByCategoryDTOS);
        return sortByCategoryDTOS;
    }
}



// If the mapper cannot find ".getCategories_categoryId().getCategoryName()" in the Announcement entity, better check the return type of the repository's method that returns a proper entity (in this context, it needs to return Announcement to use .getCategories_categoryId().getCategoryName in mapper). //

// "PageImpl<>" implements "Page" interface to display paginated list of objects. //

// "List" cannot be converted to "Page" datatype. //

// .getContent() method on "Page" is use for fetch the content of page from data source then return as 'List'. //

// "Collections.singletonList()" is use for wrap an immutable object (In this case is 'Page') so that it can be returned as 'List' type. Using Collections.singletonList() ensures that the returned object is always of type List whether it is 'Page' or 'List'. //
