package com.prgrms.wadiz.domain.order.service;

import com.prgrms.wadiz.domain.funding.FundingCategory;
import com.prgrms.wadiz.domain.funding.entity.Funding;
import com.prgrms.wadiz.domain.funding.repository.FundingRepository;
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
    private final FundingRepository fundingRepository;

    /**
     * Order create
     */
    @Transactional
    public OrderResponseDTO createOrder(
            Long supporterId,
            OrderCreateRequestDTO orderCreateRequestDto
    ) {
        Supporter supporter = supporterRepository.findById(supporterId)
                .orElseThrow(() -> {
                    log.error(
                            "Supporter {} is not found",
                            supporterId
                    );

                    throw new BaseException(ErrorCode.UNKNOWN);
                });

        List<OrderReward> orderRewards = orderCreateRequestDto.orderRewards().stream()
                .map(orderRewardRequest -> {
                    Reward reward = rewardRepository.findById(orderRewardRequest.rewardId())
                            .orElseThrow(() -> {
                                log.error(
                                        "reward {} is not found",
                                        orderRewardRequest.rewardId()
                                );

                                throw new BaseException(ErrorCode.UNKNOWN);
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
                .toList();

        Project project = orderRewards.get(0).getReward().getProject();

        Order order = Order.builder()
                .supporter(supporter)
                .project(project)
                .build();

        orderRewards.forEach(order::addOrderReward);
        orderRewards.forEach(order::calculateTotalOrderPrice);

        Funding funding = fundingRepository.findByProject_ProjectId(project.getProjectId())
                .orElseThrow(() -> {
                    log.warn(
                            "Funding cannot found as projectId : {} is not found",
                            project.getProjectId()
                    );

                    throw new BaseException(ErrorCode.PROJECT_NOT_FOUND);
                });

        funding.addOrderInfo(order.getTotalOrderPrice());
        return OrderResponseDTO.of(orderRepository.save(order).getOrderId());
    }

    /**
     * Supporter 주문 조회
     */
    @Transactional(readOnly = true)
    public OrderResponseDTO getSupporterPurchase(
            Long orderId,
            Long supporterId
    ) {
        Order order = getOrderInfo(orderId);

        validateSupporter(supporterId, order.getSupporter().getSupporterId());

        Long projectId = order.getProject().getProjectId();

        String postTitle = postRepository.findByProject_ProjectId(projectId)
                .orElseThrow(() -> {
                    log.error(
                            "Post cannot found as projectId : {} is not found",
                            projectId
                    );

                    throw new BaseException(ErrorCode.POST_NOT_FOUND);
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
                order.getTotalOrderPrice(),
                order.getOrderStatus()
        );
    }

    /**
     * Supporter 주문 목록 조회
     */
    @Transactional(readOnly = true)
    public List<OrderResponseDTO> getSupporterPurchaseHistory(Long supporterId) {
        List<Order> orders = orderRepository.findAllBySupporter_SupporterId(supporterId)
                .orElseThrow(() -> {
                    log.error(
                            "Orders are not found by supporterId : {}",
                            supporterId
                    );

                    throw new BaseException(ErrorCode.ORDER_COUNT_ERROR);
                });

        validateSupporter(supporterId,orders.get(0).getSupporter().getSupporterId());

        List<OrderResponseDTO> orderResponseDTOs = orders.stream()
                .map(order -> {
                    String postTitle = postRepository.findByProject_ProjectId(order.getProject().getProjectId())
                            .orElseThrow(() -> {
                                log.error("post is not found");

                                throw new BaseException(ErrorCode.POST_NOT_FOUND);
                            }).getPostTitle();

                    FundingCategory fundingCategory = fundingRepository.findByProject_ProjectId(order.getProject().getProjectId())
                            .orElseThrow(() -> {
                                log.error("post is not found");

                                throw new BaseException(ErrorCode.FUNDING_NOT_FOUND);
                            }).getFundingCategory();

                    String makerBrand = order.getProject().getMaker().getMakerBrand();

                    return OrderResponseDTO.of(
                            order.getOrderId(),
                            postTitle,
                            makerBrand,
                            fundingCategory,
                            order.getCreatedAt()
                    );
                }).collect(Collectors.toList());

        return orderResponseDTOs;
    }

    /**
     * Maker 주문 조회
     */
    @Transactional(readOnly = true)
    public List<OrderResponseDTO> getMakerProjectOrders(Long projectId, Long makerId) {
        List<Order> orders = orderRepository.findAllByProject_ProjectId(projectId)
                .orElseThrow(() -> {
                    log.error(
                            "Orders are not found by projectId : {}",
                            projectId
                    );

                    throw new BaseException(ErrorCode.ORDER_COUNT_ERROR);
                });

        validateMaker(makerId,orders.get(0).getProject().getMaker().getMakerId());

        List<OrderResponseDTO> orderResponseDTOs = orders.stream()
                .map(order -> {
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
                            orderRewardResponseDTOs,
                            order.getTotalOrderPrice(),
                            order.getOrderStatus()
                    );
                }).collect(Collectors.toList());

        return orderResponseDTOs;
    }

    /**
     * Order cancel
     */
    @Transactional //수정 취소될때 참여자 수, 참여 금액, 퍼센트 모두 빼줘야 한다.
    public void cancelOrder(
            Long supporterId,
            Long orderId
    ) {
        Order order = getOrderInfo(orderId);

        validateSupporter(
                supporterId,
                order.getSupporter().getSupporterId()
        );

        Funding funding = fundingRepository.findByProject_ProjectId(order.getProject().getProjectId())
                .orElseThrow(() -> {
                    log.warn("Funding is not found");

                    throw new BaseException(ErrorCode.ORDER_NOT_FOUND);
                });

        funding.removeOrderInfo(order.getTotalOrderPrice());

        order.cancel();
    }

    /**
     * Supporter 검증
     */
    private void validateSupporter(Long supporterId, Long orderSupporterId) {
        if (!orderSupporterId.equals(supporterId)){
            log.warn("Supporter is not match");

            throw new BaseException(ErrorCode.INVALID_ACCESS);
        }
    }

    /**
     * Maker 검증
     */
    private void validateMaker(Long makerId, Long projectMakerId) {
        if(!makerId.equals(projectMakerId)){
            log.warn("Maker is not match");

            throw new BaseException(ErrorCode.INVALID_ACCESS);
        }
    }

    /**
     * Order 정보 조회
     */
    private Order getOrderInfo(Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> {
            log.warn("Order {} is not found", orderId);

            throw new BaseException(ErrorCode.ORDER_NOT_FOUND);
        });

        return order;
    }

}
