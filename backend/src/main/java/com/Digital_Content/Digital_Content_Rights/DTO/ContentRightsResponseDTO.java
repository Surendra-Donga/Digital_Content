package com.Digital_Content.Digital_Content_Rights.DTO;

import com.Digital_Content.Digital_Content_Rights.Enum.RightsStatus;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class ContentRightsResponseDTO {
    private Integer id;
    private Integer digitalContentId;
    private Integer rightsOwnerId;
    private BigDecimal ownershipPercentage;
    private LocalDate rightsStartDate;
    private LocalDate rightsEndDate;
    private RightsStatus rightsStatus;
    private LocalDateTime createdAt;
}
