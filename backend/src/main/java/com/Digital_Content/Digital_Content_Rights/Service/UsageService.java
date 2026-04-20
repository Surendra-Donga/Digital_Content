package com.Digital_Content.Digital_Content_Rights.Service;

import com.Digital_Content.Digital_Content_Rights.DTO.UsageTransactionRequestDTO;
import com.Digital_Content.Digital_Content_Rights.DTO.UsageTransactionResponseDTO;
import com.Digital_Content.Digital_Content_Rights.Entity.DigitalContent;
import com.Digital_Content.Digital_Content_Rights.Entity.User;
import com.Digital_Content.Digital_Content_Rights.Entity.UsageTransaction;
import com.Digital_Content.Digital_Content_Rights.Enum.TransactionStatus;
import com.Digital_Content.Digital_Content_Rights.Repository.DigitalContentRepository;
import com.Digital_Content.Digital_Content_Rights.Repository.UserRepository;
import com.Digital_Content.Digital_Content_Rights.Repository.UsageTransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsageService {

    @Autowired
    private UsageTransactionRepository transactionRepository;

    @Autowired
    private DigitalContentRepository contentRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public UsageTransactionResponseDTO recordUsage(UsageTransactionRequestDTO dto) {
        UsageTransaction transaction = new UsageTransaction();
        
        DigitalContent content = contentRepository.findById(dto.getDigitalContentId())
                .orElseThrow(() -> new RuntimeException("Content not found"));
        User distributor = userRepository.findById(dto.getDistributorId())
                .orElseThrow(() -> new RuntimeException("Distributor not found"));
        
        transaction.setDigitalContent(content);
        transaction.setDistributor(distributor);
        transaction.setUsageType(dto.getUsageType());
        transaction.setUsageCount(dto.getUsageCount());
        transaction.setRevenueGenerated(dto.getRevenueGenerated());
        transaction.setTransactionDate(dto.getTransactionDate() != null ? dto.getTransactionDate() : LocalDateTime.now());
        transaction.setTransactionStatus(TransactionStatus.RECORDED);
        
        UsageTransaction saved = transactionRepository.save(transaction);
        return convertToDTO(saved);
    }

    public List<UsageTransactionResponseDTO> getTransactionsByStatus(TransactionStatus status) {
        return transactionRepository.findByTransactionStatus(status).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public UsageTransactionResponseDTO verifyTransaction(Integer id) {
        UsageTransaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Transaction not found"));
        transaction.setTransactionStatus(TransactionStatus.VERIFIED);
        UsageTransaction saved = transactionRepository.save(transaction);
        return convertToDTO(saved);
    }

    private UsageTransactionResponseDTO convertToDTO(UsageTransaction t) {
        UsageTransactionResponseDTO dto = new UsageTransactionResponseDTO();
        dto.setId(t.getId());
        dto.setDigitalContentId(t.getDigitalContent().getId());
        dto.setDistributorId(t.getDistributor().getId());
        dto.setUsageType(t.getUsageType());
        dto.setUsageCount(t.getUsageCount());
        dto.setRevenueGenerated(t.getRevenueGenerated());
        dto.setTransactionDate(t.getTransactionDate());
        dto.setTransactionStatus(t.getTransactionStatus());
        dto.setCreatedAt(t.getCreatedAt());
        return dto;
    }
}
