package com.prgrms.wadiz.domain.reward.repository;

import com.prgrms.wadiz.domain.reward.entity.Reward;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface RewardRepository extends JpaRepository<Reward,Long> {

    @Query("SELECT r FROM Reward r WHERE r.project.projectId = :projectId")
    Optional<List<Reward>> findAllByProjectId(Long projectId);

}
