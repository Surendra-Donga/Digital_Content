package com.Digital_Content.Digital_Content_Rights.Service;

import com.Digital_Content.Digital_Content_Rights.Entity.RoyaltyPayment;
import com.Digital_Content.Digital_Content_Rights.Repository.RoyaltyPaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PaymentService {

    @Autowired
    private RoyaltyPaymentRepository paymentRepository;

    public List<RoyaltyPayment> getAllPayments() {
        return paymentRepository.findAll();
    }

    @Transactional
    public RoyaltyPayment initiatePayment(RoyaltyPayment payment) {
        return paymentRepository.save(payment);
    }
}
