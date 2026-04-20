package com.Digital_Content.Digital_Content_Rights.Controller;

import com.Digital_Content.Digital_Content_Rights.DTO.RoyaltyCalculationResponseDTO;
import com.Digital_Content.Digital_Content_Rights.Service.RoyaltyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/royalty")
@CrossOrigin(origins = "*")
public class RoyaltyController {

    @Autowired
    private RoyaltyService royaltyService;

    @PostMapping("/calculate/{contentId}")
    public ResponseEntity<List<RoyaltyCalculationResponseDTO>> calculateRoyalty(@PathVariable Integer contentId) {
        return ResponseEntity.ok(royaltyService.calculateRoyalty(contentId));
    }

    @PutMapping("/approve/{id}")
    public ResponseEntity<RoyaltyCalculationResponseDTO> approveCalculation(@PathVariable Integer id) {
        return ResponseEntity.ok(royaltyService.approveCalculation(id));
    }
}
