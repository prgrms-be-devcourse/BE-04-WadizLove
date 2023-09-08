package com.prgrms.wadiz.domain.order.repository;

import com.prgrms.wadiz.domain.order.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order,Long> {
    Optional<List<Order>> findAllBySupporterId(Long supporterId);

    Optional<List<Order>> findAllByProjectId(Long projectId);
}
