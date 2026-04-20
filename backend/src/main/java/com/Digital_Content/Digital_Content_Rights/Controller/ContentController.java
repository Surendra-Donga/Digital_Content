package com.Digital_Content.Digital_Content_Rights.Controller;

import com.Digital_Content.Digital_Content_Rights.DTO.DigitalContentRequestDTO;
import com.Digital_Content.Digital_Content_Rights.DTO.DigitalContentResponseDTO;
import com.Digital_Content.Digital_Content_Rights.Service.ContentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/content")
@CrossOrigin(origins = "*")
public class ContentController {

    @Autowired
    private ContentService contentService;

    @GetMapping
    public ResponseEntity<List<DigitalContentResponseDTO>> getAllContent() {
        return ResponseEntity.ok(contentService.getAllContent());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DigitalContentResponseDTO> getContentById(@PathVariable Integer id) {
        return contentService.getContentById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/draft")
    public ResponseEntity<DigitalContentResponseDTO> createDraft(@Valid @RequestBody DigitalContentRequestDTO contentDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(contentService.createDraft(contentDTO));
    }

    @PutMapping("/{id}/register")
    public ResponseEntity<DigitalContentResponseDTO> registerContent(@PathVariable Integer id) {
        return ResponseEntity.ok(contentService.registerContent(id));
    }

    @PutMapping("/{id}/approve")
    public ResponseEntity<DigitalContentResponseDTO> approveContent(@PathVariable Integer id) {
        return ResponseEntity.ok(contentService.approveContent(id));
    }
}
