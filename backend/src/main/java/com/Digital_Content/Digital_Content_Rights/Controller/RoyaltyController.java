package com.Digital_Content.Digital_Content_Rights.Controller;

import com.Digital_Content.Digital_Content_Rights.Entity.RoyaltyCalculation;
import com.Digital_Content.Digital_Content_Rights.Service.RoyaltyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/royalty")
public class RoyaltyController {

    @Autowired
    private RoyaltyService royaltyService;

    @PostMapping("/calculate/{contentId}")
    public ResponseEntity<RoyaltyCalculation> calculateRoyalty(@PathVariable Integer contentId) {
        return ResponseEntity.ok(royaltyService.calculateRoyalty(contentId));
    }

    @PutMapping("/{id}/approve")
    public ResponseEntity<RoyaltyCalculation> approveCalculation(@PathVariable Integer id) {
        return ResponseEntity.ok(royaltyService.approveCalculation(id));
    }

    @GetMapping("/creator/{id}")
    public ResponseEntity<?> getCreatorEarningsReport(@PathVariable Integer id) {
        // Fetch earnings report for a specific creator
        return ResponseEntity.ok().build();
    }
}
