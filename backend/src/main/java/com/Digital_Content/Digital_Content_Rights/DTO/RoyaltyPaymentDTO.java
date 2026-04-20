package com.Digital_Content.Digital_Content_Rights.DTO;

import com.Digital_Content.Digital_Content_Rights.Enum.PaymentStatus;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class RoyaltyPaymentDTO {
    private Integer id;

    @NotNull(message = "Royalty Calculation ID is required")
    private Integer royaltyCalculationId;

    @NotNull(message = "Paid amount is required")
    @DecimalMin(value = "0.0", message = "Paid amount cannot be negative")
    private BigDecimal paidAmount;

    @PastOrPresent(message = "Payment date cannot be in the future")
    private LocalDateTime paymentDate;

    @Size(max = 255, message = "Payment reference cannot exceed 255 characters")
    private String paymentReference;

    private PaymentStatus paymentStatus;
}
