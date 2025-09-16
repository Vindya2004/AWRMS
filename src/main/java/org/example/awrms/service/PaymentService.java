package org.example.awrms.service;

import org.example.awrms.dto.PaymentDTO;

import java.util.List;
import java.util.Map;

public interface PaymentService {
    boolean savePayment(PaymentDTO paymentDTO);

    List<Map<String, Object>> getAllPayments();
}
