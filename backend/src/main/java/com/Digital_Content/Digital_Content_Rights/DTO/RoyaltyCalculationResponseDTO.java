package com.Digital_Content.Digital_Content_Rights.DTO;

import com.Digital_Content.Digital_Content_Rights.Enum.CalculationStatus;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class RoyaltyCalculationResponseDTO {
    private Integer id;
    private Integer digitalContentId;
    private Integer rightsOwnerId;
    private BigDecimal totalRevenue;
    private BigDecimal royaltyPercentage;
    private BigDecimal calculatedAmount;
    private LocalDateTime calculationDate;
    private CalculationStatus calculationStatus;
}
