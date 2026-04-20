package com.Digital_Content.Digital_Content_Rights.Entity;

import com.Digital_Content.Digital_Content_Rights.Enum.CalculationStatus;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "royalty_calculations")
@Data
public class RoyaltyCalculation {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "digital_content_id")
    private DigitalContent digitalContent;

    @ManyToOne
    @JoinColumn(name = "rights_owner_id")
    private User rightsOwner;

    private BigDecimal totalRevenue;

    private BigDecimal royaltyPercentage;

    private BigDecimal calculatedAmount;

    private LocalDateTime calculationDate;

    @Enumerated(EnumType.STRING)
    private CalculationStatus calculationStatus;
}
