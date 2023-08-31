package com.prgrms.wadiz.domain.reward;

import com.prgrms.wadiz.domain.reward.entity.Reward;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RewardRepository extends JpaRepository<Reward,Long> {
}
