package com.Digital_Content.Digital_Content_Rights.Repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.Digital_Content.Digital_Content_Rights.Entity.UsageTransaction;
import com.Digital_Content.Digital_Content_Rights.Enum.TransactionStatus;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.math.BigDecimal;

public interface UsageTransactionRepository extends JpaRepository<UsageTransaction, Integer> {
    List<UsageTransaction> findByDigitalContent_IdAndTransactionStatus(Integer contentId, TransactionStatus status);
    List<UsageTransaction> findByTransactionStatus(TransactionStatus status);

    @Query("SELECT SUM(t.revenueGenerated) FROM UsageTransaction t WHERE t.digitalContent.id = :contentId AND t.transactionStatus = 'VERIFIED'")
    BigDecimal getTotalVerifiedRevenueByContentId(@Param("contentId") Integer contentId);

    @Query("SELECT t.digitalContent.id FROM UsageTransaction t GROUP BY t.digitalContent.id ORDER BY SUM(t.usageCount) DESC")
    List<Integer> findMostUsedContentIds();
}
