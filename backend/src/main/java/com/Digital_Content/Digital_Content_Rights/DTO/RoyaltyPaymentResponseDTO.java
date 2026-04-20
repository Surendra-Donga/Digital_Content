package com.Digital_Content.Digital_Content_Rights.DTO;

import com.Digital_Content.Digital_Content_Rights.Enum.PaymentStatus;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class RoyaltyPaymentResponseDTO {
    private Integer id;
    private Integer royaltyCalculationId;
    private BigDecimal paidAmount;
    private LocalDateTime paymentDate;
    private String paymentReference;
    private PaymentStatus paymentStatus;
}
