package com.prgrms.wadiz.domain.order.service;

import com.prgrms.wadiz.domain.order.OrderStatus;
import com.prgrms.wadiz.domain.order.dto.request.OrderCreateRequestDTO;
import com.prgrms.wadiz.domain.order.dto.request.OrderRewardRequestDTO;
import com.prgrms.wadiz.domain.order.entity.Order;
import com.prgrms.wadiz.domain.order.repository.OrderRepository;
import com.prgrms.wadiz.domain.orderReward.entity.OrderReward;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @InjectMocks
    private OrderService orderService;

//    @Autowired
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

//        List<OrderReward> orderRewards = new ArrayList<>();
//        OrderReward orderReward = new OrderReward(reward, reward.getRewardPrice(), orderQuantity);
//        orderRewards.add(orderReward);
        Order order = new Order(supporter);

        OrderRewardRequestDTO orderRewardReq = new OrderRewardRequestDTO(1L, orderQuantity);
        List<OrderRewardRequestDTO> orderRequest = new ArrayList<>();
        orderRequest.add(orderRewardReq);
        OrderCreateRequestDTO orderCreateReq = new OrderCreateRequestDTO(orderRequest);

        given(supporterRepository.findById(anyLong())).willReturn(Optional.of(supporter));
        given(rewardRepository.findById(anyLong())).willReturn(Optional.of(reward));
        given(orderRepository.save(any())).willReturn(order);

        //when
        Long savedOrderId = orderService.createOrder(1L, orderCreateReq);

        //then
        Assertions.assertThat(order.getOrderStatus()).isEqualTo(OrderStatus.REQUESTED);

    }
//
//    @ParameterizedTest
//    @ValueSource(ints = {11,100})
//    @DisplayName("주문이 재고 초과로 실패한다.")
//    void failOrderByExceededOrderQuantity(int orderQuantity) {

//    }
//
//    @ParameterizedTest
//    @ValueSource(ints = {0,-1})
//    @DisplayName("주문이 0과 음수 입력으로 실패한다.")
//    void failOrderByZeroAndNegativeOrderQuantity(int orderQuantity) {

//    }

    private Reward createReward(Long rewardId, int price, int quantity){
        return new Reward(rewardId, price, quantity);
    }

    private Supporter createSupporter(Long supporterId, String email, String name) {
        return new Supporter(supporterId, email, name);
    }

}