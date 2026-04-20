package com.Digital_Content.Digital_Content_Rights.Entity;

import com.Digital_Content.Digital_Content_Rights.Enum.TransactionStatus;
import com.Digital_Content.Digital_Content_Rights.Enum.UsageType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "usage_transactions")
@Data
public class UsageTransaction {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "digital_content_id")
    private DigitalContent digitalContent;

    @ManyToOne
    @JoinColumn(name = "distributor_id")
    private User distributor;

    @Enumerated(EnumType.STRING)
    private UsageType usageType;

    private Integer usageCount;

    private BigDecimal revenueGenerated;

    private LocalDateTime transactionDate;

    @Enumerated(EnumType.STRING)
    private TransactionStatus transactionStatus;

    @CreationTimestamp
    private LocalDateTime createdAt;
}
