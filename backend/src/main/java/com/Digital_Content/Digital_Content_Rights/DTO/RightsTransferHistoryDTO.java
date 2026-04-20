package com.Digital_Content.Digital_Content_Rights.DTO;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class RightsTransferHistoryDTO {
    private Integer id;

    @NotNull(message = "Digital Content ID is required")
    private Integer digitalContentId;

    @NotNull(message = "Previous owner ID is required")
    private Integer previousOwnerId;

    @NotNull(message = "New owner ID is required")
    private Integer newOwnerId;

    @PastOrPresent(message = "Transfer date cannot be in the future")
    private LocalDateTime transferDate;

    @NotNull(message = "Transfer percentage is required")
    @DecimalMin(value = "0.0", message = "Percentage cannot be negative")
    @DecimalMax(value = "100.0", message = "Percentage cannot exceed 100")
    private BigDecimal transferPercentage;
}
