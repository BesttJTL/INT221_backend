package sit.int221.sasprojectkk2.services;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.CrossOrigin;
import sit.int221.sasprojectkk2.dtos.*;
import sit.int221.sasprojectkk2.entities.Announcement;
import sit.int221.sasprojectkk2.repositories.AnnouncementRepository;
import sit.int221.sasprojectkk2.utils.ListMapper;

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

    @Autowired
    private ListMapper listMapper;


    public List<Announcement> getAllUserView() {
        List<Announcement> announcementList = announcementRepository.findAll();
        return announcementList;
    }
    public Page<?> getAllUserViewPageable(int size, int page) {
        Pageable pageable = PageRequest.of(page,size);
        Page<Announcement> announcementList = announcementRepository.findAll(pageable);
        System.out.println("ALL: "+announcementList.getTotalElements());
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

    public Page<AnnouncementDTO> closeMethod(int page, int size, Integer category) {
        List<Announcement> findAll = announcementRepository.findAll();
        ZonedDateTime currentTime = ZonedDateTime.now();

        List<AnnouncementDTO> filteredClose = findAll.stream()
                .filter(announcement -> 'Y' == announcement.getAnnouncementDisplay())
                .filter(announcement -> {
                    ZonedDateTime closeDate = announcement.getCloseDate();
                    if (closeDate != null && (closeDate.isBefore(currentTime) || closeDate.isEqual(currentTime))) {
                        return true;
                    }
                    return false;
                })
                .filter(announcement -> category == null || Objects.equals(announcement.getCategories_categoryId().getCategoryId(),category))
                .map(c -> {
                    AnnouncementDTO activeDTO = modelMapper.map(c, AnnouncementDTO.class);
                    activeDTO.setAnnouncementCategory(c.getCategories_categoryId().getCategoryName());
                    return activeDTO;
                })
                .collect(Collectors.toList());

        Collections.reverse(filteredClose);

        int start = page * size;
        int end = Math.min(start + size, filteredClose.size());
        int total = filteredClose.size();

        if (start >= total) {
            return new PageImpl<>(Collections.emptyList());
        }

        List<AnnouncementDTO> pageContent = filteredClose.subList(start, end);
        return new PageImpl<>(pageContent, PageRequest.of(page, size), total);
    }


    public Page<AnnouncementDTO> activeMethod(int page, int size, Integer category) {
        List<Announcement> findALl = announcementRepository.findAll();
        Pageable pageable = PageRequest.of(page, size);
        ZonedDateTime currentTime = ZonedDateTime.now();
        List<AnnouncementDTO> filteredActive = new java.util.ArrayList<>(findALl.stream()
                .filter(announcement -> category == null || Objects.equals(announcement.getCategories_categoryId().getCategoryId(),category))
                .filter(announcement -> 'Y' == announcement.getAnnouncementDisplay())
                .filter(announcement -> {
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
                    AnnouncementDTO activeDTO = modelMapper.map(c, AnnouncementDTO.class);
                    activeDTO.setAnnouncementCategory(c.getCategories_categoryId().getCategoryName());
                    return activeDTO;
                }).toList());
        System.out.println("Active Size = "+filteredActive.size());
        Collections.reverse(filteredActive);
        int start = (int) pageable.getOffset();
        int end = Math.min(start + pageable.getPageSize(), filteredActive.size());
        int total = filteredActive.size();
        if (start >= total) {
            return new PageImpl<>(Collections.emptyList(), pageable, 0);
        }
        List<AnnouncementDTO> pageContent = filteredActive.subList(start, end);
        Page<AnnouncementDTO> pageable1 = new PageImpl<>(pageContent, pageable, total);
        return pageable1;
    }



    public Page<?> userViewPage(String mode, int size, Integer category, int page) {
         if (Objects.equals(mode, "active")) {
            System.out.println("Input Category Number: " + category);
            if(category == null){
                Page<AnnouncementDTO> pageAnnouncement = activeMethod(page, size,category);
                return (pageAnnouncement);
            }else{
                return sortByCategories(category, size, page,mode);
            }
        }
        if(Objects.equals(mode,"close")){
            if(category == null){
                Page<AnnouncementDTO> pageAnnouncementClose = closeMethod(page, size,category);
                return (pageAnnouncementClose);
            }
            return sortByCategories(category, size, page, mode);
        }
        return (getAllUserViewPageable(page, size));
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

    public Page<?> sortByCategories(Integer category, int size, int page,String mode)
    {
        ZonedDateTime currentTime = ZonedDateTime.now();
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        Page<Announcement> announcementList = announcementRepository.findAnnouncementByCategoryId(category, pageable);
        List<AnnouncementDTO> sortByCategoryDTOList = announcementList.stream().map(announcement -> {
            AnnouncementDTO sortByCategoryDTO = modelMapper.map(announcement, AnnouncementDTO.class);
            sortByCategoryDTO.setAnnouncementCategory(announcement.getCategories_categoryId().getCategoryName());
            return sortByCategoryDTO;
        }).collect(Collectors.toList());

        if (Objects.equals(mode, "active")) {
            List<AnnouncementDTO> allActiveFiltered = activeMethod(page, size,category).getContent();
            System.out.println(allActiveFiltered.size());
            return new PageImpl<>(allActiveFiltered,pageable,announcementList.getTotalElements());
        }
        if (Objects.equals(mode, "close")) {
            List<AnnouncementDTO> allCloseFiltered = closeMethod(page, size,category).getContent();
            allCloseFiltered.stream()
                    .filter(announcement -> 'Y' == announcement.getAnnouncementDisplay())
                    .filter(announcement -> {
                        ZonedDateTime closeDate = announcement.getCloseDate();
                        if (closeDate != null && (closeDate.isBefore(currentTime) || closeDate.isEqual(currentTime))) {
                            return true;
                        }
                        return false;
                    }).map(c -> {
                        AnnouncementDTO activeDTO = modelMapper.map(c, AnnouncementDTO.class);
                        return activeDTO;
                    }).toList();
            return new PageImpl<>(allCloseFiltered, pageable, allCloseFiltered.size());
        }
        return new PageImpl<>(sortByCategoryDTOList, pageable, announcementList.getTotalElements());
    }
}



// If the mapper cannot find ".getCategories_categoryId().getCategoryName()" in the Announcement entity, better check the return type of the repository's method that returns a proper entity (in this context, it needs to return Announcement to use .getCategories_categoryId().getCategoryName in mapper). //

// "PageImpl<>" implements "Page" interface to display paginated list of objects. //

// "List" cannot be converted to "Page" datatype. //

// .getContent() method on "Page" is use for fetch the content of page from data source then return as 'List'. //

// "Collections.singletonList()" is use for wrap an immutable object (In this case is 'Page') so that it can be returned as 'List' type. Using Collections.singletonList() ensures that the returned object is always of type List whether it is 'Page' or 'List'. //
