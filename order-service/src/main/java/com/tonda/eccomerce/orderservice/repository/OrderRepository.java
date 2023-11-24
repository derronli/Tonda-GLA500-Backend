package com.tonda.eccomerce.orderservice.repository;

import com.tonda.eccomerce.orderservice.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
