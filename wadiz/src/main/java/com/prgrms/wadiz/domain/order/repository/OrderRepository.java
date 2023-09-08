package com.prgrms.wadiz.domain.order.repository;

import com.prgrms.wadiz.domain.order.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order,Long> {

    @Query("SELECT o FROM Order o WHERE o.supporter.supporterId = :supporterId")
    Optional<List<Order>> findAllBySupporterId(Long supporterId);

    @Query("SELECT o FROM Order o WHERE o.project.projectId = :projectId")
    Optional<List<Order>> findAllByProjectId(Long projectId);
}
