package com.Digital_Content.Digital_Content_Rights.Repository;

import java.util.List;
import java.math.BigDecimal;
import org.springframework.data.jpa.repository.JpaRepository;
import com.Digital_Content.Digital_Content_Rights.Entity.RoyaltyPayment;
import com.Digital_Content.Digital_Content_Rights.Enum.PaymentStatus;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RoyaltyPaymentRepository extends JpaRepository<RoyaltyPayment, Integer> {
    List<RoyaltyPayment> findByRoyaltyCalculation_IdAndPaymentStatus(Integer calculationId, PaymentStatus status);

    @Query("SELECT p FROM RoyaltyPayment p JOIN p.royaltyCalculation rc JOIN rc.digitalContent c WHERE c.id = :contentId")
    List<RoyaltyPayment> findByContentId(@Param("contentId") Integer contentId);

    @Query("SELECT SUM(p.paidAmount) FROM RoyaltyPayment p WHERE p.royaltyCalculation.rightsOwner.id = :creatorId AND p.paymentStatus = 'SUCCESS'")
    BigDecimal getTotalPaidRoyaltyByCreatorId(@Param("creatorId") Integer creatorId);
}
