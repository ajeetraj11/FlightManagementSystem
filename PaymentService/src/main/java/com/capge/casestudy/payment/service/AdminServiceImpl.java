package com.capge.casestudy.payment.service;

import com.capge.casestudy.payment.entity.Payment;
import com.capge.casestudy.payment.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Override
    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }

    @Override
    public Payment getPaymentById(Long id) {
        return paymentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Payment not found"));
    }

    @Override
    public Payment updatePayment(Long id, Payment updatedPayment) {
        Payment existing = getPaymentById(id);

        // Only allow updates to status and amount
        existing.setAmount(updatedPayment.getAmount());
        existing.setStatus(updatedPayment.getStatus());

        return paymentRepository.save(existing);
    }

    @Override
    public void deletePayment(Long id) {
        if (!paymentRepository.existsById(id)) {
            throw new RuntimeException("Payment not found");
        }
        paymentRepository.deleteById(id);
    }
}