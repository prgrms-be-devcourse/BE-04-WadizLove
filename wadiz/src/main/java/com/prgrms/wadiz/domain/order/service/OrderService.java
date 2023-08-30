package com.prgrms.wadiz.domain.order.service;

import com.prgrms.wadiz.domain.order.dto.request.OrderCreateRequest;
import com.prgrms.wadiz.domain.order.entity.Order;
import com.prgrms.wadiz.domain.order.repository.OrderRepository;
import com.prgrms.wadiz.domain.orderitem.entity.OrderItem;
import com.prgrms.wadiz.domain.supporter.entity.Supporter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {

    private OrderRepository orderRepository;
    private SupporterService supporterService;
    private RewardService rewardService;

    // 주문 생성
    @Transactional
    public Long createOrder(Long supporterId, OrderCreateRequest orderCreateRequest){
        Supporter supporter = supporterService.getSupporter(supporterId);

        List<OrderReward> orderRewards = orderCreateRequest.orderRewards().stream()
                .map(rewardRequest -> {
                    Reward reward = rewardService.getReward(rewardRequest.getId());
                    return OrderReward.createOrderReward(reward, reward.getPrice(),reward.getCount());
                })
                .collect(Collectors.toList());

        Order order = Order.createOrder(orderRewards);

        return orderRepository.save(order).getId();
    }

}
