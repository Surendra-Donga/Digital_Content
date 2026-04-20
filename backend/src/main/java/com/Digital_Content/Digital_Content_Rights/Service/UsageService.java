package com.Digital_Content.Digital_Content_Rights.Service;

import com.Digital_Content.Digital_Content_Rights.Entity.UsageTransaction;
import com.Digital_Content.Digital_Content_Rights.Enum.TransactionStatus;
import com.Digital_Content.Digital_Content_Rights.Repository.UsageTransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UsageService {

    @Autowired
    private UsageTransactionRepository transactionRepository;

    @Transactional
    public UsageTransaction recordUsage(UsageTransaction transaction) {
        transaction.setTransactionStatus(TransactionStatus.RECORDED);
        return transactionRepository.save(transaction);
    }

    public List<UsageTransaction> getTransactionsByStatus(TransactionStatus status) {
        return transactionRepository.findByTransactionStatus(status);
    }

    @Transactional
    public UsageTransaction verifyTransaction(Integer id) {
        UsageTransaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Transaction not found"));
        transaction.setTransactionStatus(TransactionStatus.VERIFIED);
        return transactionRepository.save(transaction);
    }
}
