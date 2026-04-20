package com.Digital_Content.Digital_Content_Rights.DTO;

import com.Digital_Content.Digital_Content_Rights.Enum.RightsStatus;
import jakarta.validation.constraints.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class ContentRightsRequestDTO {
    @NotNull(message = "Digital Content ID is required")
    private Integer digitalContentId;

    @NotNull(message = "Rights Owner ID is required")
    private Integer rightsOwnerId;

    @NotNull(message = "Ownership percentage is required")
    @DecimalMin(value = "0.0", message = "Percentage cannot be negative")
    @DecimalMax(value = "100.0", message = "Percentage cannot exceed 100")
    private BigDecimal ownershipPercentage;

    @NotNull(message = "Rights start date is required")
    private LocalDate rightsStartDate;

    private LocalDate rightsEndDate;

    private RightsStatus rightsStatus;
}
