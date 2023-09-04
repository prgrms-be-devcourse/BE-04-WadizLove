package com.prgrms.wadiz.domain.reward.repository;

import com.prgrms.wadiz.domain.reward.entity.Reward;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RewardRepository extends JpaRepository<Reward,Long> {

    public Optional<List<Reward>> findAllByProjectId(Long projectId);

    Reward findByProjectId(Long projectId);
}
