package com.Digital_Content.Digital_Content_Rights.Controller;

import com.Digital_Content.Digital_Content_Rights.Entity.ContentRights;
import com.Digital_Content.Digital_Content_Rights.Entity.RightsTransferHistory;
import com.Digital_Content.Digital_Content_Rights.Service.RightsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rights")
public class RightsController {

    @Autowired
    private RightsService rightsService;

    @GetMapping("/content/{contentId}")
    public ResponseEntity<List<ContentRights>> getRightsByContent(@PathVariable Integer contentId) {
        return ResponseEntity.ok(rightsService.getRightsByContent(contentId));
    }

    @PostMapping("/assign")
    public ResponseEntity<ContentRights> assignRights(@RequestBody ContentRights rights) {
        return ResponseEntity.status(HttpStatus.CREATED).body(rightsService.assignRights(rights));
    }

    @PostMapping("/transfer")
    public ResponseEntity<RightsTransferHistory> transferRights(@RequestBody RightsTransferHistory history) {
        return ResponseEntity.status(HttpStatus.CREATED).body(rightsService.transferRights(history));
    }
}
