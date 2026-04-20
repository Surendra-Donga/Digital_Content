package com.Digital_Content.Digital_Content_Rights.Service;

import com.Digital_Content.Digital_Content_Rights.DTO.RoyaltyCalculationResponseDTO;
import com.Digital_Content.Digital_Content_Rights.Entity.*;
import com.Digital_Content.Digital_Content_Rights.Enum.CalculationStatus;
import com.Digital_Content.Digital_Content_Rights.Enum.TransactionStatus;
import com.Digital_Content.Digital_Content_Rights.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoyaltyService {

    @Autowired
    private RoyaltyCalculationRepository royaltyCalculationRepository;

    @Autowired
    private UsageTransactionRepository usageTransactionRepository;

    @Autowired
    private ContentRightsRepository contentRightsRepository;

    @Autowired
    private DigitalContentRepository digitalContentRepository;

    public List<RoyaltyCalculationResponseDTO> getAllCalculations() {
        return royaltyCalculationRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public List<RoyaltyCalculationResponseDTO> calculateRoyalty(Integer contentId) {
        DigitalContent content = digitalContentRepository.findById(contentId)
                .orElseThrow(() -> new RuntimeException("Content not found"));

        List<UsageTransaction> transactions = usageTransactionRepository
                .findByDigitalContent_IdAndTransactionStatus(contentId, TransactionStatus.VERIFIED);

        BigDecimal totalRevenue = transactions.stream()
                .map(UsageTransaction::getRevenueGenerated)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        List<ContentRights> allRights = contentRightsRepository.findByDigitalContent_Id(contentId);
        List<RoyaltyCalculation> savedCalculations = new ArrayList<>();

        for (ContentRights rights : allRights) {
            RoyaltyCalculation calculation = new RoyaltyCalculation();
            calculation.setDigitalContent(content);
            calculation.setRightsOwner(rights.getRightsOwner());
            calculation.setTotalRevenue(totalRevenue);
            calculation.setRoyaltyPercentage(rights.getOwnershipPercentage());
            
            BigDecimal amount = totalRevenue.multiply(rights.getOwnershipPercentage())
                    .divide(new BigDecimal("100"), 2, RoundingMode.HALF_UP);
            calculation.setCalculatedAmount(amount);
            
            calculation.setCalculationDate(LocalDateTime.now());
            calculation.setCalculationStatus(CalculationStatus.PENDING);
            
            savedCalculations.add(royaltyCalculationRepository.save(calculation));
        }

        return savedCalculations.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public RoyaltyCalculationResponseDTO approveCalculation(Integer id) {
        RoyaltyCalculation calculation = royaltyCalculationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Calculation not found"));
        calculation.setCalculationStatus(CalculationStatus.APPROVED);
        return convertToDTO(royaltyCalculationRepository.save(calculation));
    }

    private RoyaltyCalculationResponseDTO convertToDTO(RoyaltyCalculation calculation) {
        RoyaltyCalculationResponseDTO dto = new RoyaltyCalculationResponseDTO();
        dto.setId(calculation.getId());
        dto.setDigitalContentId(calculation.getDigitalContent().getId());
        dto.setRightsOwnerId(calculation.getRightsOwner().getId());
        dto.setTotalRevenue(calculation.getTotalRevenue());
        dto.setRoyaltyPercentage(calculation.getRoyaltyPercentage());
        dto.setCalculatedAmount(calculation.getCalculatedAmount());
        dto.setCalculationDate(calculation.getCalculationDate());
        dto.setCalculationStatus(calculation.getCalculationStatus());
        return dto;
    }
}
