package com.Digital_Content.Digital_Content_Rights.Service;

import com.Digital_Content.Digital_Content_Rights.DTO.ContentRightsRequestDTO;
import com.Digital_Content.Digital_Content_Rights.DTO.ContentRightsResponseDTO;
import com.Digital_Content.Digital_Content_Rights.Entity.ContentRights;
import com.Digital_Content.Digital_Content_Rights.Entity.DigitalContent;
import com.Digital_Content.Digital_Content_Rights.Entity.User;
import com.Digital_Content.Digital_Content_Rights.Enum.RightsStatus;
import com.Digital_Content.Digital_Content_Rights.Repository.ContentRightsRepository;
import com.Digital_Content.Digital_Content_Rights.Repository.DigitalContentRepository;
import com.Digital_Content.Digital_Content_Rights.Repository.UserRepository;
import com.Digital_Content.Digital_Content_Rights.Repository.RightsTransferHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RightsService {

    @Autowired
    private ContentRightsRepository rightsRepository;

    @Autowired
    private DigitalContentRepository contentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RightsTransferHistoryRepository transferHistoryRepository;

    public List<ContentRightsResponseDTO> getRightsByContent(Integer contentId) {
        return rightsRepository.findByDigitalContent_Id(contentId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public ContentRightsResponseDTO assignRights(ContentRightsRequestDTO dto) {
        DigitalContent content = contentRepository.findById(dto.getDigitalContentId())
                .orElseThrow(() -> new RuntimeException("Content not found"));
        User owner = userRepository.findById(dto.getRightsOwnerId())
                .orElseThrow(() -> new RuntimeException("Owner not found"));

        ContentRights rights = new ContentRights();
        rights.setDigitalContent(content);
        rights.setRightsOwner(owner);
        rights.setOwnershipPercentage(dto.getOwnershipPercentage());
        rights.setRightsStartDate(dto.getRightsStartDate());
        rights.setRightsEndDate(dto.getRightsEndDate());
        rights.setRightsStatus(dto.getRightsStatus() != null ? dto.getRightsStatus() : RightsStatus.ACTIVE);

        return convertToDTO(rightsRepository.save(rights));
    }

    private ContentRightsResponseDTO convertToDTO(ContentRights rights) {
        ContentRightsResponseDTO dto = new ContentRightsResponseDTO();
        dto.setId(rights.getId());
        dto.setDigitalContentId(rights.getDigitalContent().getId());
        dto.setRightsOwnerId(rights.getRightsOwner().getId());
        dto.setOwnershipPercentage(rights.getOwnershipPercentage());
        dto.setRightsStartDate(rights.getRightsStartDate());
        dto.setRightsEndDate(rights.getRightsEndDate());
        dto.setRightsStatus(rights.getRightsStatus());
        dto.setCreatedAt(rights.getCreatedAt());
        return dto;
    }
}
