package com.saqib.Payment_Service.repo;


import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import com.saqib.Payment_Service.model.Payment;
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    Optional<Payment> findByOrderId(String orderId);
}
