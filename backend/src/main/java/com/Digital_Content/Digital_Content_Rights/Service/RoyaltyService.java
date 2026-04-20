package com.Digital_Content.Digital_Content_Rights.Service;

import com.Digital_Content.Digital_Content_Rights.Entity.RoyaltyCalculation;
import com.Digital_Content.Digital_Content_Rights.Enum.CalculationStatus;
import com.Digital_Content.Digital_Content_Rights.Repository.RoyaltyCalculationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RoyaltyService {

    @Autowired
    private RoyaltyCalculationRepository royaltyCalculationRepository;

    @Transactional
    public RoyaltyCalculation calculateRoyalty(Integer contentId) {
        return null;
    }

    @Transactional
    public RoyaltyCalculation approveCalculation(Integer id) {
        RoyaltyCalculation calculation = royaltyCalculationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Calculation not found"));
        calculation.setCalculationStatus(CalculationStatus.APPROVED);
        return royaltyCalculationRepository.save(calculation);
    }
}
