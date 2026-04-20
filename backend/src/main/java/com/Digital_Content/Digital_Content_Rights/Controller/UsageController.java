package com.Digital_Content.Digital_Content_Rights.Controller;

import com.Digital_Content.Digital_Content_Rights.Entity.UsageTransaction;
import com.Digital_Content.Digital_Content_Rights.Enum.TransactionStatus;
import com.Digital_Content.Digital_Content_Rights.Service.UsageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usage")
public class UsageController {

    @Autowired
    private UsageService usageService;

    @PostMapping
    public ResponseEntity<UsageTransaction> submitUsage(@RequestBody UsageTransaction transaction) {
        return ResponseEntity.status(HttpStatus.CREATED).body(usageService.recordUsage(transaction));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<UsageTransaction>> getTransactionsByStatus(@PathVariable TransactionStatus status) {
        return ResponseEntity.ok(usageService.getTransactionsByStatus(status));
    }

    @PutMapping("/{id}/verify")
    public ResponseEntity<UsageTransaction> verifyTransaction(@PathVariable Integer id) {
        return ResponseEntity.ok(usageService.verifyTransaction(id));
    }
}
