package com.Digital_Content.Digital_Content_Rights.Repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.Digital_Content.Digital_Content_Rights.Entity.UsageTransaction;
import com.Digital_Content.Digital_Content_Rights.Enum.TransactionStatus;

public interface UsageTransactionRepository extends JpaRepository<UsageTransaction, Integer> {
    List<UsageTransaction> findByDigitalContent_IdAndTransactionStatus(Integer contentId, TransactionStatus status);
    List<UsageTransaction> findByTransactionStatus(TransactionStatus status);
}
