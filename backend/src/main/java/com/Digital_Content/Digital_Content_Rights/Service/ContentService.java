package com.Digital_Content.Digital_Content_Rights.Service;

import com.Digital_Content.Digital_Content_Rights.DTO.DigitalContentDTO;
import com.Digital_Content.Digital_Content_Rights.Entity.DigitalContent;
import com.Digital_Content.Digital_Content_Rights.Entity.User;
import com.Digital_Content.Digital_Content_Rights.Enum.ContentStatus;
import com.Digital_Content.Digital_Content_Rights.Repository.DigitalContentRepository;
import com.Digital_Content.Digital_Content_Rights.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ContentService {

    @Autowired
    private DigitalContentRepository contentRepository;

    @Autowired
    private UserRepository userRepository;

    public List<DigitalContentDTO> getAllContent() {
        return contentRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public Optional<DigitalContentDTO> getContentById(Integer id) {
        return contentRepository.findById(id).map(this::convertToDTO);
    }

    @Transactional
    public DigitalContentDTO registerContent(DigitalContentDTO contentDTO) {
        DigitalContent content = new DigitalContent();
        content.setTitle(contentDTO.getTitle());
        content.setContentType(contentDTO.getContentType());
        content.setDescription(contentDTO.getDescription());
        content.setPublishedDate(contentDTO.getPublishedDate());
        content.setContentStatus(ContentStatus.REGISTERED);
        
        User creator = userRepository.findById(contentDTO.getCreatedById())
                .orElseThrow(() -> new RuntimeException("Creator not found"));
        content.setCreatedBy(creator);
        
        DigitalContent savedContent = contentRepository.save(content);
        return convertToDTO(savedContent);
    }

    @Transactional
    public DigitalContentDTO approveContent(Integer id) {
        DigitalContent content = contentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Content not found"));
        content.setContentStatus(ContentStatus.ACTIVE);
        DigitalContent savedContent = contentRepository.save(content);
        return convertToDTO(savedContent);
    }

    private DigitalContentDTO convertToDTO(DigitalContent content) {
        DigitalContentDTO dto = new DigitalContentDTO();
        dto.setId(content.getId());
        dto.setTitle(content.getTitle());
        dto.setContentType(content.getContentType());
        dto.setDescription(content.getDescription());
        dto.setPublishedDate(content.getPublishedDate());
        dto.setContentStatus(content.getContentStatus());
        if (content.getCreatedBy() != null) {
            dto.setCreatedById(content.getCreatedBy().getId());
        }
        return dto;
    }
}
