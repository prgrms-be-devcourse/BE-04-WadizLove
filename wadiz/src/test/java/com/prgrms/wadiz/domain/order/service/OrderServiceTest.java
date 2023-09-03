package com.prgrms.wadiz.domain.order.service;

import com.prgrms.wadiz.domain.order.OrderStatus;
import com.prgrms.wadiz.domain.order.dto.request.OrderCreateRequestDTO;
import com.prgrms.wadiz.domain.order.dto.response.OrderResponseDTO;
import com.prgrms.wadiz.domain.order.dto.request.OrderRewardRequestDTO;
import com.prgrms.wadiz.domain.order.entity.Order;
import com.prgrms.wadiz.domain.order.repository.OrderRepository;
import com.prgrms.wadiz.domain.orderReward.entity.OrderReward;
import com.prgrms.wadiz.domain.reward.entity.Reward;
import com.prgrms.wadiz.domain.reward.repository.RewardRepository;
import com.prgrms.wadiz.domain.supporter.entity.Supporter;
import com.prgrms.wadiz.domain.supporter.repository.SupporterRepository;
import com.prgrms.wadiz.global.util.exception.BaseException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @InjectMocks
    private OrderService orderService;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private SupporterRepository supporterRepository;

    @Mock
    private RewardRepository rewardRepository;

    @ParameterizedTest
    @ValueSource(ints = {1,10})
    @DisplayName("[성공] 주문을 생성한다.")
    void successOrder(int orderQuantity){
        //given
        Long orderId = 1L;
        Supporter supporter = createSupporter(1L, "hihi@gmail.com", "min");
        Reward reward = createReward(1L,10000,10);

        OrderReward orderReward = new OrderReward(reward, 10000, orderQuantity);
        List<OrderReward> orderRewards = new ArrayList<>();
        orderRewards.add(orderReward);

        Order order = new Order(supporter,orderRewards);

        // request
        OrderRewardRequestDTO orderRewardReq = OrderRewardRequestDTO.builder()
                .rewardId(1L)
                .orderQuantity(orderQuantity)
                .build();

        List<OrderRewardRequestDTO> orderRewardReqs = new ArrayList<>();
        orderRewardReqs.add(orderRewardReq);

        OrderCreateRequestDTO orderCreateReq = OrderCreateRequestDTO.builder()
                .orderRewards(orderRewardReqs)
                .build();

        given(supporterRepository.findById(anyLong())).willReturn(Optional.of(supporter));
        given(rewardRepository.findById(anyLong())).willReturn(Optional.of(reward));
        given(orderRepository.save(any(Order.class))).willReturn(order);

        //when
        OrderResponseDTO savedOrder = orderService.createOrder(supporter.getSupporterId(), orderCreateReq);

        //then
        assertThat(savedOrder.orderRewards()).isEqualTo(order.getOrderRewards());
        assertThat(savedOrder.orderStatus()).isEqualTo(order.getOrderStatus());

    }

//    @ParameterizedTest
//    @ValueSource(ints = {0,-1})
//    @DisplayName("[실패] 주문 수량 입력 0과 음수 입력으로 실패한다.") // 질문 dto에 대한 검증 테스트도 따로 해주고 도메인에서도 따로 해줘야 하는것인가? 도메인 레이어에서 테스트할 내용인가?
//    void failOrderByZeroAndNegativeOrderQuantity(int orderQuantity) {
//
//    }

    @ParameterizedTest
    @ValueSource(ints = {11,100})
    @DisplayName("[실패] 주문이 재고 초과로 실패한다.")
    void failOrderByExceededOrderQuantity(int orderQuantity) {
        //given
        Supporter supporter = createSupporter(1L, "hihi@gmail.com", "min");
        Reward reward = createReward(1L,10000,10);

        OrderReward orderReward = new OrderReward(reward, 10000, orderQuantity);
        List<OrderReward> orderRewards = new ArrayList<>();
        orderRewards.add(orderReward);

        // request
        OrderRewardRequestDTO orderRewardReq = OrderRewardRequestDTO.builder()
                .rewardId(1L)
                .orderQuantity(orderQuantity)
                .build();

        List<OrderRewardRequestDTO> orderRewardReqs = new ArrayList<>();
        orderRewardReqs.add(orderRewardReq);

        OrderCreateRequestDTO orderCreateReq = OrderCreateRequestDTO.builder()
                .orderRewards(orderRewardReqs)
                .build();

        given(supporterRepository.findById(anyLong())).willReturn(Optional.of(supporter));
        given(rewardRepository.findById(anyLong())).willReturn(Optional.of(reward));

        //when, then
        assertThatThrownBy(() ->orderService.createOrder(supporter.getSupporterId(), orderCreateReq)).isInstanceOf(BaseException.class);
    }

    private Reward createReward(Long rewardId, int price, int quantity){
        return new Reward(rewardId, price, quantity);
    }

    private Supporter createSupporter(Long supporterId, String email, String name) {
        return new Supporter(supporterId, email, name);
    }

}