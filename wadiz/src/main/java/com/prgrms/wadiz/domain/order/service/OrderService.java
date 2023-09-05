package com.prgrms.wadiz.domain.order.service;

import com.prgrms.wadiz.domain.order.dto.request.OrderCreateRequestDTO;
import com.prgrms.wadiz.domain.order.dto.response.OrderResponseDTO;
import com.prgrms.wadiz.domain.order.entity.Order;
import com.prgrms.wadiz.domain.order.repository.OrderRepository;
import com.prgrms.wadiz.domain.orderReward.dto.response.OrderRewardResponseDTO;
import com.prgrms.wadiz.domain.orderReward.entity.OrderReward;
import com.prgrms.wadiz.domain.post.repository.PostRepository;
import com.prgrms.wadiz.domain.project.entity.Project;
import com.prgrms.wadiz.domain.reward.entity.Reward;
import com.prgrms.wadiz.domain.reward.repository.RewardRepository;
import com.prgrms.wadiz.domain.supporter.entity.Supporter;
import com.prgrms.wadiz.domain.supporter.repository.SupporterRepository;
import com.prgrms.wadiz.global.util.exception.BaseException;
import com.prgrms.wadiz.global.util.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final SupporterRepository supporterRepository;
    private final RewardRepository rewardRepository;
    private final PostRepository postRepository;

    // 주문 생성
    @Transactional
    public void createOrder(
            Long supporterId,
            OrderCreateRequestDTO orderCreateRequestDto
    ) {
        Supporter supporter = supporterRepository.findById(supporterId)
                .orElseThrow(() -> {
                    log.error("Supporter {} is not found", supporterId);

                    return new BaseException(ErrorCode.UNKNOWN);
                });

        List<OrderReward> orderRewards = orderCreateRequestDto.orderRewards().stream()
                .map(orderRewardRequest -> {
                    Reward reward = rewardRepository.findById(orderRewardRequest.rewardId())
                            .orElseThrow(() -> {
                                log.error("reward is not found");

                                return new BaseException(ErrorCode.UNKNOWN);
                            });

                    Integer orderQuantity = orderRewardRequest.orderQuantity();

                    OrderReward orderReward = OrderReward.builder()
                            .reward(reward)
                            .orderRewardPrice(reward.getRewardPrice())
                            .orderRewardQuantity(orderQuantity)
                            .build();
                    reward.removeStock(orderQuantity);

                    return orderReward;
                })
                .collect(Collectors.toList());

        Project project = orderRewards.get(0).getReward().getProject();

        Order order = Order.builder()
                .supporter(supporter)
                .project(project)
                .orderRewards(orderRewards)
                .build();

        orderRewards.forEach(order::addOrderReward);

        orderRepository.save(order);
    }

    @Transactional(readOnly = true)
    public OrderResponseDTO getPurchase(
            Long supporterId,
            Long orderId
    ) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> {
            log.error("Order {} is not found", orderId);

            return new BaseException(ErrorCode.ORDER_NOT_FOUND);
        });

        if (!order.getSupporter().getSupporterId().equals(supporterId)){

            throw new BaseException(ErrorCode.INVALID_ACCESS);
        }

        Long projectId = order.getProject().getProjectId();
        String postTitle = postRepository.findByProjectId(projectId).orElseThrow(() -> {
            log.error("post is not found");

            return new BaseException(ErrorCode.POST_NOT_FOUND);
        }).getPostTitle();

        List<OrderRewardResponseDTO> orderRewardResponseDTOs = order.getOrderRewards().stream()
                .map(orderReward -> OrderRewardResponseDTO.of(
                        orderReward.getReward().getRewardName(),
                        orderReward.getReward().getRewardDescription(),
                        orderReward.getOrderRewardPrice(),
                        orderReward.getOrderRewardQuantity()

                ))
                .collect(Collectors.toList());

        return OrderResponseDTO.of(
                order.getOrderId(),
                postTitle,
                order.getProject().getMaker().getMakerBrand(),
                orderRewardResponseDTOs,
                order.getOrderStatus()
        );
    }

    @Transactional
    public void cancelOrder(
            Long supporterId,
            Long orderId
    ) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> {
            log.error("Order {} is not found", orderId);

            return new BaseException(ErrorCode.ORDER_NOT_FOUND);
        });

        if (!order.getSupporter().getSupporterId().equals(supporterId)){

            throw new BaseException(ErrorCode.INVALID_ACCESS);
        }

        order.cancel();
    }
}
