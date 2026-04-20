package com.Digital_Content.Digital_Content_Rights.Controller;

import com.Digital_Content.Digital_Content_Rights.DTO.UsageTransactionRequestDTO;
import com.Digital_Content.Digital_Content_Rights.DTO.UsageTransactionResponseDTO;
import com.Digital_Content.Digital_Content_Rights.Enum.TransactionStatus;
import com.Digital_Content.Digital_Content_Rights.Service.UsageService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usage")
@CrossOrigin(origins = "*")
public class UsageController {

    @Autowired
    private UsageService usageService;

    @PostMapping
    public ResponseEntity<UsageTransactionResponseDTO> recordUsage(@Valid @RequestBody UsageTransactionRequestDTO usageDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(usageService.recordUsage(usageDTO));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<UsageTransactionResponseDTO>> getTransactionsByStatus(@PathVariable TransactionStatus status) {
        return ResponseEntity.ok(usageService.getTransactionsByStatus(status));
    }

    @PutMapping("/{id}/verify")
    public ResponseEntity<UsageTransactionResponseDTO> verifyTransaction(@PathVariable Integer id) {
        return ResponseEntity.ok(usageService.verifyTransaction(id));
    }
}
