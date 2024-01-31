package com.prgrms.wadiz.domain.order;

import com.prgrms.wadiz.domain.orderReward.OrderReward;
import com.prgrms.wadiz.domain.project.Project;
import com.prgrms.wadiz.domain.reward.RewardReader;
import com.prgrms.wadiz.domain.reward.entity.Reward;
import com.prgrms.wadiz.domain.supporter.entity.Supporter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderAppender {

    private final OrderRepository orderRepository;
    private final RewardReader rewardReader;

    public List<OrderReward> createOrderRewards(Set<Map.Entry<Long, Integer>> entrySet) {
        return entrySet.stream()
                .map(entry -> {
                    Reward reward = rewardReader.read(entry.getKey());

                    Integer orderQuantity = entry.getValue();
                    OrderReward orderReward = OrderReward.builder()
                            .reward(reward)
                            .orderRewardPrice(reward.getRewardPrice())
                            .orderRewardQuantity(orderQuantity)
                            .build();

                    reward.removeStock(orderQuantity);

                    return orderReward;
                })
                .toList();
    }

    public Order createOrder(Supporter supporter, List<OrderReward> orderRewards, Project project) {
        Order order = Order.builder()
                .supporter(supporter)
                .project(project)
                .build();

        orderRewards.forEach(order::addOrderReward);
        orderRewards.forEach(order::calculateTotalOrderPrice);

        return order;
    }

    public Long append(Order order) {
        return orderRepository.save(order).getOrderId();
    }
}
