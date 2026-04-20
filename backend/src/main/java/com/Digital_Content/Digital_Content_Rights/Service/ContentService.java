package com.Digital_Content.Digital_Content_Rights.Service;

import com.Digital_Content.Digital_Content_Rights.DTO.DigitalContentRequestDTO;
import com.Digital_Content.Digital_Content_Rights.DTO.DigitalContentResponseDTO;
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

    public List<DigitalContentResponseDTO> getAllContent() {
        return contentRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public Optional<DigitalContentResponseDTO> getContentById(Integer id) {
        return contentRepository.findById(id).map(this::convertToDTO);
    }

    @Transactional
    public DigitalContentResponseDTO createDraft(DigitalContentRequestDTO contentDTO) {
        DigitalContent content = convertToEntity(contentDTO);
        content.setContentStatus(ContentStatus.DRAFT);
        return convertToDTO(contentRepository.save(content));
    }

    @Transactional
    public DigitalContentResponseDTO registerContent(Integer id) {
        DigitalContent content = contentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Content not found"));
        if (content.getContentStatus() != ContentStatus.DRAFT) {
            throw new RuntimeException("Only draft content can be registered");
        }
        content.setContentStatus(ContentStatus.REGISTERED);
        return convertToDTO(contentRepository.save(content));
    }

    @Transactional
    public DigitalContentResponseDTO approveContent(Integer id) {
        DigitalContent content = contentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Content not found"));
        if (content.getContentStatus() != ContentStatus.REGISTERED) {
            throw new RuntimeException("Only registered content can be approved");
        }
        content.setContentStatus(ContentStatus.ACTIVE);
        return convertToDTO(contentRepository.save(content));
    }

    private DigitalContent convertToEntity(DigitalContentRequestDTO dto) {
        DigitalContent content = new DigitalContent();
        content.setTitle(dto.getTitle());
        content.setContentType(dto.getContentType());
        content.setDescription(dto.getDescription());
        content.setPublishedDate(dto.getPublishedDate());
        
        User creator = userRepository.findById(dto.getCreatedById())
                .orElseThrow(() -> new RuntimeException("Creator not found"));
        content.setCreatedBy(creator);
        return content;
    }

    private DigitalContentResponseDTO convertToDTO(DigitalContent content) {
        DigitalContentResponseDTO dto = new DigitalContentResponseDTO();
        dto.setId(content.getId());
        dto.setTitle(content.getTitle());
        dto.setContentType(content.getContentType());
        dto.setDescription(content.getDescription());
        dto.setPublishedDate(content.getPublishedDate());
        dto.setContentStatus(content.getContentStatus());
        dto.setCreatedAt(content.getCreatedAt());
        if (content.getCreatedBy() != null) {
            dto.setCreatedById(content.getCreatedBy().getId());
        }
        return dto;
    }
}
