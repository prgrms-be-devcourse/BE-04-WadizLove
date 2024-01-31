package com.prgrms.wadiz.domain.supporter;

import com.prgrms.wadiz.domain.supporter.entity.Supporter;
import com.prgrms.wadiz.domain.supporter.repository.SupporterRepository;
import com.prgrms.wadiz.global.util.exception.BaseException;
import com.prgrms.wadiz.global.util.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class SupporterReader {

    private final SupporterRepository supporterRepository;

    public Supporter read(Long supporterId) {
        return supporterRepository.findById(supporterId)
                .orElseThrow(() -> {
                    log.error(
                            "Supporter {} is not found",
                            supporterId
                    );
                    throw new BaseException(ErrorCode.UNKNOWN);
                });
    }
}
