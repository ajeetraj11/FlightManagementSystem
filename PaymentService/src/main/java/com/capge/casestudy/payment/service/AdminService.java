package com.capge.casestudy.payment.service;

import com.capge.casestudy.payment.entity.Payment;

import java.util.List;

public interface AdminService {
    List<Payment> getAllPayments();
    Payment getPaymentById(Long id);
    Payment updatePayment(Long id, Payment updatedPayment);
    void deletePayment(Long id);
}
