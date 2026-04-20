package com.Digital_Content.Digital_Content_Rights.Service;

import com.Digital_Content.Digital_Content_Rights.DTO.RoyaltyPaymentRequestDTO;
import com.Digital_Content.Digital_Content_Rights.DTO.RoyaltyPaymentResponseDTO;
import com.Digital_Content.Digital_Content_Rights.Entity.RoyaltyCalculation;
import com.Digital_Content.Digital_Content_Rights.Entity.RoyaltyPayment;
import com.Digital_Content.Digital_Content_Rights.Enum.CalculationStatus;
import com.Digital_Content.Digital_Content_Rights.Enum.PaymentStatus;
import com.Digital_Content.Digital_Content_Rights.Repository.RoyaltyCalculationRepository;
import com.Digital_Content.Digital_Content_Rights.Repository.RoyaltyPaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PaymentService {

    @Autowired
    private RoyaltyPaymentRepository paymentRepository;

    @Autowired
    private RoyaltyCalculationRepository calculationRepository;

    public List<RoyaltyPaymentResponseDTO> getAllPayments() {
        return paymentRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public RoyaltyPaymentResponseDTO initiatePayment(RoyaltyPaymentRequestDTO request) {
        RoyaltyCalculation calculation = calculationRepository.findById(request.getRoyaltyCalculationId())
                .orElseThrow(() -> new RuntimeException("Calculation not found"));

        if (calculation.getCalculationStatus() != CalculationStatus.APPROVED) {
            throw new RuntimeException("Calculation must be approved before payment");
        }

        List<RoyaltyPayment> previousPayments = paymentRepository
                .findByRoyaltyCalculation_IdAndPaymentStatus(request.getRoyaltyCalculationId(), PaymentStatus.SUCCESS);

        BigDecimal totalPaid = previousPayments.stream()
                .map(RoyaltyPayment::getPaidAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        if (totalPaid.add(request.getPaidAmount()).compareTo(calculation.getCalculatedAmount()) > 0) {
            throw new RuntimeException("Total paid amount cannot exceed calculated amount");
        }

        RoyaltyPayment payment = new RoyaltyPayment();
        payment.setRoyaltyCalculation(calculation);
        payment.setPaidAmount(request.getPaidAmount());
        payment.setPaymentReference(request.getPaymentReference());
        payment.setPaymentDate(LocalDateTime.now());
        payment.setPaymentStatus(PaymentStatus.SUCCESS);

        return convertToDTO(paymentRepository.save(payment));
    }

    private RoyaltyPaymentResponseDTO convertToDTO(RoyaltyPayment payment) {
        RoyaltyPaymentResponseDTO dto = new RoyaltyPaymentResponseDTO();
        dto.setId(payment.getId());
        dto.setRoyaltyCalculationId(payment.getRoyaltyCalculation().getId());
        dto.setPaidAmount(payment.getPaidAmount());
        dto.setPaymentDate(payment.getPaymentDate());
        dto.setPaymentReference(payment.getPaymentReference());
        dto.setPaymentStatus(payment.getPaymentStatus());
        return dto;
    }
}
