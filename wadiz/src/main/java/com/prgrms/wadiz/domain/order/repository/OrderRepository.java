package com.prgrms.wadiz.domain.order.repository;

import com.prgrms.wadiz.domain.order.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order,Long> {
}
