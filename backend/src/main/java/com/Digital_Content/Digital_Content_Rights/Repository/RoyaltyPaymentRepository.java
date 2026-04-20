package com.Digital_Content.Digital_Content_Rights.Repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.Digital_Content.Digital_Content_Rights.Entity.RoyaltyPayment;
import com.Digital_Content.Digital_Content_Rights.Enum.PaymentStatus;

public interface RoyaltyPaymentRepository extends JpaRepository<RoyaltyPayment, Integer> {
    List<RoyaltyPayment> findByRoyaltyCalculation_IdAndPaymentStatus(Integer calculationId, PaymentStatus status);
}
