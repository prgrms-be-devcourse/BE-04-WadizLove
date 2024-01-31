package com.prgrms.wadiz.domain.funding;

import com.prgrms.wadiz.global.util.exception.BaseException;
import com.prgrms.wadiz.global.util.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class FundingReader {

    private final FundingRepository fundingRepository;

    public Funding read(Long projectId) {
        return fundingRepository.findByProject_ProjectId(projectId)
                .orElseThrow(() -> {
                    log.warn(
                            "Funding cannot found as projectId : {} is not found",
                            projectId
                    );
                    throw new BaseException(ErrorCode.PROJECT_NOT_FOUND);
                });

    }
}
