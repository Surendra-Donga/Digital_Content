package com.Digital_Content.Digital_Content_Rights.Service;

import com.Digital_Content.Digital_Content_Rights.Entity.DigitalContent;
import com.Digital_Content.Digital_Content_Rights.Enum.ContentStatus;
import com.Digital_Content.Digital_Content_Rights.Repository.DigitalContentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ContentService {

    @Autowired
    private DigitalContentRepository contentRepository;

    public List<DigitalContent> getAllContent() {
        return contentRepository.findAll();
    }

    public Optional<DigitalContent> getContentById(Integer id) {
        return contentRepository.findById(id);
    }

    @Transactional
    public DigitalContent registerContent(DigitalContent content) {
        content.setContentStatus(ContentStatus.REGISTERED);
        return contentRepository.save(content);
    }

    @Transactional
    public DigitalContent approveContent(Integer id) {
        DigitalContent content = contentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Content not found"));
        content.setContentStatus(ContentStatus.ACTIVE);
        return contentRepository.save(content);
    }
}
