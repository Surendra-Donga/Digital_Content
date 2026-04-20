package com.Digital_Content.Digital_Content_Rights.Entity;

import com.Digital_Content.Digital_Content_Rights.Enum.RightsStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
public class ContentRights {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "digital_content_id")
    private DigitalContent digitalContent;

    @ManyToOne
    @JoinColumn(name = "rights_owner_id")
    private User rightsOwner;

    private BigDecimal ownershipPercentage;

    private LocalDate rightsStartDate;

    private LocalDate rightsEndDate;

    @Enumerated(EnumType.STRING)
    private RightsStatus rightsStatus;

    @CreationTimestamp
    private LocalDateTime createdAt;
}
