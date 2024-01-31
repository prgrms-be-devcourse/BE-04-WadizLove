package com.prgrms.wadiz.domain.reward;

import com.prgrms.wadiz.domain.reward.entity.Reward;
import com.prgrms.wadiz.domain.reward.repository.RewardRepository;
import com.prgrms.wadiz.global.util.exception.BaseException;
import com.prgrms.wadiz.global.util.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class RewardReader {

    private final RewardRepository rewardRepository;

    public Reward read(Long rewardId) {
        return rewardRepository.findById(rewardId)
                .orElseThrow(() -> {
                    log.error(
                            "reward {} is not found",
                            rewardId
                    );
                    throw new BaseException(ErrorCode.UNKNOWN);
                });
    }
}
