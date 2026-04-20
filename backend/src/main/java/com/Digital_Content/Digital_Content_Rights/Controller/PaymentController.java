package com.Digital_Content.Digital_Content_Rights.Controller;

import com.Digital_Content.Digital_Content_Rights.DTO.RoyaltyPaymentRequestDTO;
import com.Digital_Content.Digital_Content_Rights.DTO.RoyaltyPaymentResponseDTO;
import com.Digital_Content.Digital_Content_Rights.Service.PaymentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payments")
@CrossOrigin(origins = "*")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @GetMapping
    public ResponseEntity<List<RoyaltyPaymentResponseDTO>> getAllPayments() {
        return ResponseEntity.ok(paymentService.getAllPayments());
    }

    @PostMapping
    public ResponseEntity<RoyaltyPaymentResponseDTO> initiatePayment(@Valid @RequestBody RoyaltyPaymentRequestDTO paymentDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(paymentService.initiatePayment(paymentDTO));
    }
}
