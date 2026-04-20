package com.Digital_Content.Digital_Content_Rights.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "rights_transfer_history")
@Data
public class RightsTransferHistory {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "digital_content_id")
    private DigitalContent digitalContent;

    @ManyToOne
    @JoinColumn(name = "previous_owner_id")
    private User previousOwner;

    @ManyToOne
    @JoinColumn(name = "new_owner_id")
    private User newOwner;

    private LocalDateTime transferDate;

    private BigDecimal transferPercentage;
}
