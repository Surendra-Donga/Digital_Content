package com.Digital_Content.Digital_Content_Rights.Controller;

import com.Digital_Content.Digital_Content_Rights.DTO.UsageTransactionDTO;
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
    public ResponseEntity<UsageTransactionDTO> submitUsage(@Valid @RequestBody UsageTransactionDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(usageService.recordUsage(dto));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<UsageTransactionDTO>> getTransactionsByStatus(@PathVariable TransactionStatus status) {
        return ResponseEntity.ok(usageService.getTransactionsByStatus(status));
    }

    @PutMapping("/{id}/verify")
    public ResponseEntity<UsageTransactionDTO> verifyTransaction(@PathVariable Integer id) {
        return ResponseEntity.ok(usageService.verifyTransaction(id));
    }
}
