package com.Digital_Content.Digital_Content_Rights.Controller;

import com.Digital_Content.Digital_Content_Rights.DTO.ContentRightsRequestDTO;
import com.Digital_Content.Digital_Content_Rights.DTO.ContentRightsResponseDTO;
import com.Digital_Content.Digital_Content_Rights.Service.RightsService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rights")
@CrossOrigin(origins = "*")
public class RightsController {

    @Autowired
    private RightsService rightsService;

    @GetMapping("/content/{contentId}")
    public ResponseEntity<List<ContentRightsResponseDTO>> getRightsByContent(@PathVariable Integer contentId) {
        return ResponseEntity.ok(rightsService.getRightsByContent(contentId));
    }

    @PostMapping
    public ResponseEntity<ContentRightsResponseDTO> assignRights(@Valid @RequestBody ContentRightsRequestDTO rightsDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(rightsService.assignRights(rightsDTO));
    }
}
