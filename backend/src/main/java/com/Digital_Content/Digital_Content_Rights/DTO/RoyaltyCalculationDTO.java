package com.Digital_Content.Digital_Content_Rights.DTO;

import com.Digital_Content.Digital_Content_Rights.Enum.CalculationStatus;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class RoyaltyCalculationDTO {
    private Integer id;

    @NotNull(message = "Digital Content ID is required")
    private Integer digitalContentId;

    @NotNull(message = "Total revenue is required")
    @DecimalMin(value = "0.0", message = "Revenue cannot be negative")
    private BigDecimal totalRevenue;

    @NotNull(message = "Royalty percentage is required")
    @DecimalMin(value = "0.0", message = "Percentage cannot be negative")
    @DecimalMax(value = "100.0", message = "Percentage cannot exceed 100")
    private BigDecimal royaltyPercentage;

    @NotNull(message = "Calculated amount is required")
    @DecimalMin(value = "0.0", message = "Amount cannot be negative")
    private BigDecimal calculatedAmount;

    @PastOrPresent(message = "Calculation date cannot be in the future")
    private LocalDateTime calculationDate;

    private CalculationStatus calculationStatus;
}
