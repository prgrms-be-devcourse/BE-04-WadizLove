package com.prgrms.wadiz.domain.order.service;

import com.prgrms.wadiz.domain.order.dto.request.OrderCreateRequestDTO;
import com.prgrms.wadiz.domain.order.entity.Order;
import com.prgrms.wadiz.domain.order.repository.OrderRepository;
import com.prgrms.wadiz.domain.orderReward.entity.OrderReward;
import com.prgrms.wadiz.domain.reward.RewardRepository;
import com.prgrms.wadiz.domain.reward.entity.Reward;
import com.prgrms.wadiz.domain.supporter.SupporterRepository;
import com.prgrms.wadiz.domain.supporter.entity.Supporter;
import com.prgrms.wadiz.global.util.exception.BaseException;
import com.prgrms.wadiz.global.util.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final SupporterRepository supporterRepository;
    private final RewardRepository rewardRepository;

    // 주문 생성
    @Transactional
    public Long createOrder(Long supporterId, OrderCreateRequestDTO orderCreateRequestDto) {
        Supporter supporter = supporterRepository.findById(supporterId)
                .orElseThrow(() -> {
                    log.error("Supporter {} is not found", supporterId);

                    return new BaseException(ErrorCode.UNKNOWN);
                });

        List<OrderReward> orderRewards = orderCreateRequestDto.orderRewards().stream()
                .map(orderRewardRequest -> {
                    Reward reward = rewardRepository.findById(orderRewardRequest.rewardId())
                            .orElseThrow(IllegalArgumentException::new);
                    Integer orderQuantity = orderRewardRequest.orderQuantity();

                    return OrderReward.createOrderReward(reward, reward.getRewardPrice(), orderQuantity);
                })
                .collect(Collectors.toList());

        Order order = Order.createOrder(supporter, orderRewards);

        return orderRepository.save(order).getOrderId();
    }

}
