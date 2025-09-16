package org.example.awrms.service.impl;

import org.example.awrms.dto.PaymentDTO;
import org.example.awrms.entity.Payment;
import org.example.awrms.repository.PaymentRepository;
import org.example.awrms.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class paymentServiceImpl implements PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Override
    public boolean savePayment(PaymentDTO paymentDTO) {
        try {
            Payment payment = new Payment();
            payment.setUserEmail(paymentDTO.getUserEmail());
            payment.setCardHolderName(paymentDTO.getCardHolderName());
            payment.setCardNumber(paymentDTO.getCardNumber());
            payment.setExpiryDate(paymentDTO.getExpiryDate());
            payment.setCvv(paymentDTO.getCvv());
            payment.setAmount(paymentDTO.getAmount());
            payment.setPaymentDate(LocalDateTime.now());

            paymentRepository.save(payment);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    @Override
    public List<Map<String, Object>> getAllPayments() {
        List<Payment> payments = paymentRepository.findAll();
        System.out.println(payments);
        return payments.stream().map(payment -> {
            Map<String, Object> paymentDetails = new HashMap<>();
            paymentDetails.put("id", payment.getId());
            paymentDetails.put("userEmail", payment.getUserEmail());
            paymentDetails.put("amount", payment.getAmount());
            paymentDetails.put("paymentDate", payment.getPaymentDate());
            return paymentDetails;
        }).collect(Collectors.toList());
    }
}
