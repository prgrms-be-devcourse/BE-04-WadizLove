package com.prgrms.wadiz.domain.order.service;

import com.prgrms.wadiz.domain.order.OrderStatus;
import com.prgrms.wadiz.domain.order.dto.request.OrderCreateRequestDTO;
import com.prgrms.wadiz.domain.order.dto.request.OrderRewardRequestDTO;
import com.prgrms.wadiz.domain.order.entity.Order;
import com.prgrms.wadiz.domain.order.repository.OrderRepository;
import com.prgrms.wadiz.domain.reward.RewardRepository;
import com.prgrms.wadiz.domain.reward.entity.Reward;
import com.prgrms.wadiz.domain.supporter.SupporterRepository;
import com.prgrms.wadiz.domain.supporter.entity.Supporter;
import org.assertj.core.api.Assertions;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

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
    @DisplayName("주문 생성에 성공한다.")
    void successOrder(int orderQuantity){
        //given
        Supporter supporter = createSupporter(1L, "hihi@gmail.com", "min");
        Reward reward = createReward(1L,10000,10);
        Order order = new Order(1L,supporter,OrderStatus.REQUESTED);

        OrderRewardRequestDTO orderRewardReq = new OrderRewardRequestDTO(1L, orderQuantity);
        List<OrderRewardRequestDTO> orderRequest = new ArrayList<>();
        orderRequest.add(orderRewardReq);
        OrderCreateRequestDTO orderCreateReq = new OrderCreateRequestDTO(orderRequest);

        given(supporterRepository.findById(anyLong())).willReturn(Optional.of(supporter));
        given(rewardRepository.findById(anyLong())).willReturn(Optional.of(reward));
        given(orderRepository.save(any(Order.class))).willReturn(order);
        given(orderRepository.findById(anyLong())).willReturn(Optional.of(order));

        //when
        Long savedOrderId = orderService.createOrder(supporter.getSupporterId(), orderCreateReq);
        Order savedOrder = orderRepository.findById(savedOrderId).orElseThrow(IllegalArgumentException::new);

        //then
        assertThat(savedOrder.getOrderId()).isEqualTo(1L);
        assertThat(savedOrder.getOrderStatus()).isEqualTo(OrderStatus.REQUESTED);

    }


    @ParameterizedTest
    @ValueSource(ints = {0,-1})
    @DisplayName("주문이 0과 음수 입력으로 실패한다.")
    void failOrderByZeroAndNegativeOrderQuantity(int orderQuantity) {
        //given
        Supporter supporter = createSupporter(1L, "hihi@gmail.com", "min");
        Reward reward = createReward(1L,10000,10);
        Order order = new Order(1L,supporter,OrderStatus.REQUESTED);

        OrderRewardRequestDTO orderRewardReq = new OrderRewardRequestDTO(1L, orderQuantity);
        List<OrderRewardRequestDTO> orderRequest = new ArrayList<>();
        orderRequest.add(orderRewardReq);
        OrderCreateRequestDTO orderCreateReq = new OrderCreateRequestDTO(orderRequest);

        given(supporterRepository.findById(anyLong())).willReturn(Optional.of(supporter));
        given(rewardRepository.findById(anyLong())).willReturn(Optional.of(reward));
        given(orderRepository.save(any(Order.class))).willReturn(order);
        given(orderRepository.findById(anyLong())).willReturn(Optional.of(order));

        //when
        Long savedOrderId = orderService.createOrder(supporter.getSupporterId(), orderCreateReq);
        Order savedOrder = orderRepository.findById(savedOrderId).orElseThrow(IllegalArgumentException::new);

        //then
        assertThat(savedOrder.getOrderId()).isEqualTo(1L);
        assertThat(savedOrder.getOrderStatus()).isEqualTo(OrderStatus.REQUESTED);

    }

    @ParameterizedTest
    @ValueSource(ints = {11,100})
    @DisplayName("주문이 재고 초과로 실패한다.")
    void failOrderByExceededOrderQuantity(int orderQuantity) {
        //given
        Supporter supporter = createSupporter(1L, "hihi@gmail.com", "min");
        Reward reward = createReward(1L,10000,10);
        Order order = new Order(1L,supporter,OrderStatus.REQUESTED);

        OrderRewardRequestDTO orderRewardReq = new OrderRewardRequestDTO(1L, orderQuantity);
        List<OrderRewardRequestDTO> orderRequest = new ArrayList<>();
        orderRequest.add(orderRewardReq);
        OrderCreateRequestDTO orderCreateReq = new OrderCreateRequestDTO(orderRequest);

        given(supporterRepository.findById(anyLong())).willReturn(Optional.of(supporter));
        given(rewardRepository.findById(anyLong())).willReturn(Optional.of(reward));
        given(orderRepository.save(any(Order.class))).willReturn(order);
        given(orderRepository.findById(anyLong())).willReturn(Optional.of(order));

        //when
        Long savedOrderId = orderService.createOrder(supporter.getSupporterId(), orderCreateReq);
        Order savedOrder = orderRepository.findById(savedOrderId).orElseThrow(IllegalArgumentException::new);

        //then
        Assertions.assertThat(savedOrder.getOrderId()).isEqualTo(1L);
        Assertions.assertThat(savedOrder.getOrderStatus()).isEqualTo(OrderStatus.REQUESTED);
    }


    private Reward createReward(Long rewardId, int price, int quantity){
        return new Reward(rewardId, price, quantity);
    }

    private Supporter createSupporter(Long supporterId, String email, String name) {
        return new Supporter(supporterId, email, name);
    }

}