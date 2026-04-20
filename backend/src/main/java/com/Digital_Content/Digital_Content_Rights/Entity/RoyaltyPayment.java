package com.Digital_Content.Digital_Content_Rights.Entity;

import com.Digital_Content.Digital_Content_Rights.Enum.PaymentStatus;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "royalty_payments")
@Data
public class RoyaltyPayment {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "royalty_calculation_id")
    private RoyaltyCalculation royaltyCalculation;

    private BigDecimal paidAmount;

    private LocalDateTime paymentDate;

    private String paymentReference;

    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;
}
