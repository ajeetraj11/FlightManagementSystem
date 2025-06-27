package com.capge.casestudy.payment.repository;

import com.capge.casestudy.payment.entity.Payment;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    List<Payment> findByUserId(String userId);

    Payment findBySessionId(String sessionId);

    List<Payment> findByStatus(String pending);
    @Modifying
    @Transactional
    @Query("DELETE FROM Payment p WHERE p.bookingId = :bookingId")
    void deleteByBookingId(@Param("bookingId") Long bookingId);
}
