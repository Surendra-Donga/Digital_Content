package com.Digital_Content.Digital_Content_Rights.DTO;

import com.Digital_Content.Digital_Content_Rights.Enum.UsageType;
import jakarta.validation.constraints.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class UsageTransactionRequestDTO {
    @NotNull(message = "Digital Content ID is required")
    private Integer digitalContentId;

    @NotNull(message = "Distributor ID is required")
    private Integer distributorId;

    @NotNull(message = "Usage type is required")
    private UsageType usageType;

    @NotNull(message = "Usage count is required")
    @Min(value = 0, message = "Usage count cannot be negative")
    private Integer usageCount;

    @NotNull(message = "Revenue generated is required")
    @DecimalMin(value = "0.0", message = "Revenue cannot be negative")
    private BigDecimal revenueGenerated;

    private LocalDateTime transactionDate;
}
