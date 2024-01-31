package com.prgrms.wadiz.domain.funding;

import com.prgrms.wadiz.domain.order.Order;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class FundingAppender {

    private final FundingReader fundingReader;

    public void applyOrderInfo(Long projectId, Order order) {
        Funding funding = fundingReader.read(projectId);
        funding.addOrderInfo(order.getTotalOrderPrice());
    }
}
