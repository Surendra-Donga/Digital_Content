package com.Digital_Content.Digital_Content_Rights.Controller;

import com.Digital_Content.Digital_Content_Rights.Entity.DigitalContent;
import com.Digital_Content.Digital_Content_Rights.Service.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/content")
public class ContentController {

    @Autowired
    private ContentService contentService;

    @GetMapping
    public ResponseEntity<List<DigitalContent>> getAllContent() {
        return ResponseEntity.ok(contentService.getAllContent());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DigitalContent> getContentById(@PathVariable Integer id) {
        return contentService.getContentById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<DigitalContent> registerContent(@RequestBody DigitalContent content) {
        return ResponseEntity.status(HttpStatus.CREATED).body(contentService.registerContent(content));
    }

    @PutMapping("/{id}/approve")
    public ResponseEntity<DigitalContent> approveContent(@PathVariable Integer id) {
        return ResponseEntity.ok(contentService.approveContent(id));
    }
}
