package com.Digital_Content.Digital_Content_Rights.Controller;

import com.Digital_Content.Digital_Content_Rights.DTO.DigitalContentDTO;
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
    public ResponseEntity<List<DigitalContentDTO>> getAllContent() {
        return ResponseEntity.ok(contentService.getAllContent());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DigitalContentDTO> getContentById(@PathVariable Integer id) {
        return contentService.getContentById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<DigitalContentDTO> registerContent(@Valid @RequestBody DigitalContentDTO contentDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(contentService.registerContent(contentDTO));
    }

    @PutMapping("/{id}/approve")
    public ResponseEntity<DigitalContentDTO> approveContent(@PathVariable Integer id) {
        return ResponseEntity.ok(contentService.approveContent(id));
    }
}
