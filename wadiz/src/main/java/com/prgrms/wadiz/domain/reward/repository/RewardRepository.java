package com.prgrms.wadiz.domain.reward.repository;

import com.prgrms.wadiz.domain.reward.entity.Reward;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RewardRepository extends JpaRepository<Reward,Long> {
//    @Query("SELECT r FROM Reward r WHERE r.project.projectId = :projectId") //TODO : 체크 필요
    Optional<List<Reward>> findAllByProjectId(Long projectId);

    void deleteAllByProjectId(Long projectId);
}
