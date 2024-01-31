package com.prgrms.wadiz.domain.order;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order,Long> {

//    @Query("SELECT o FROM Order o WHERE o.supporter.supporterId = :supporterId")
    Optional<List<Order>> findAllBySupporter_SupporterId(Long supporterId);



//    @Query("SELECT o FROM Order o WHERE o.project.projectId = :projectId")
    Optional<List<Order>> findAllByProject_ProjectId(Long projectId); //TODO : 변경사항 체크
}
