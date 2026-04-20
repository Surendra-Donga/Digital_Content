package com.Digital_Content.Digital_Content_Rights.Controller;

import com.Digital_Content.Digital_Content_Rights.Entity.RoyaltyPayment;
import com.Digital_Content.Digital_Content_Rights.Service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @GetMapping
    public ResponseEntity<List<RoyaltyPayment>> getAllPayments() {
        return ResponseEntity.ok(paymentService.getAllPayments());
    }

    @PostMapping
    public ResponseEntity<RoyaltyPayment> initiatePayment(@RequestBody RoyaltyPayment payment) {
        return ResponseEntity.status(HttpStatus.CREATED).body(paymentService.initiatePayment(payment));
    }
}
