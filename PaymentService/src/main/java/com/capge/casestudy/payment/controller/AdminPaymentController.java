package com.capge.casestudy.payment.controller;

import com.capge.casestudy.payment.entity.Payment;
import com.capge.casestudy.payment.service.AdminService;
import com.capge.casestudy.payment.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/payment")
public class AdminPaymentController {

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private AdminService adminService;

    @GetMapping
    public ResponseEntity<List<Payment>> getAllPayments() {
        return ResponseEntity.ok(paymentService.getAllPayments());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Payment> getPaymentById(@PathVariable Long id) {
        return ResponseEntity.ok(adminService.getPaymentById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Payment> updatePayment(@PathVariable Long id, @RequestBody Payment updatedPayment) {
        return ResponseEntity.ok(adminService.updatePayment(id, updatedPayment));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePayment(@PathVariable Long id) {
        adminService.deletePayment(id);
        return ResponseEntity.noContent().build();
    }
}