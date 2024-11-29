package com.matops.vsv_security.repository;

import com.matops.vsv_security.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
