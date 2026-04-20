package com.Digital_Content.Digital_Content_Rights.DTO;

import com.Digital_Content.Digital_Content_Rights.Enum.TransactionStatus;
import com.Digital_Content.Digital_Content_Rights.Enum.UsageType;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class UsageTransactionResponseDTO {
    private Integer id;
    private Integer digitalContentId;
    private Integer distributorId;
    private UsageType usageType;
    private Integer usageCount;
    private BigDecimal revenueGenerated;
    private LocalDateTime transactionDate;
    private TransactionStatus transactionStatus;
    private LocalDateTime createdAt;
}
