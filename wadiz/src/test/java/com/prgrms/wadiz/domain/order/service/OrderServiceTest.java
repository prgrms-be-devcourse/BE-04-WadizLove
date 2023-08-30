package com.prgrms.wadiz.domain.order.service;

import com.prgrms.wadiz.domain.order.OrderStatus;
import com.prgrms.wadiz.domain.order.dto.request.OrderCreateRequestDTO;
import com.prgrms.wadiz.domain.order.entity.Order;
import com.prgrms.wadiz.domain.order.repository.OrderRepository;
import com.prgrms.wadiz.domain.supporter.entity.Supporter;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @InjectMocks
    private OrderService orderService;

    @Mock
    private SupporterService supporterService;

    @Mock
    private RewardService rewardService;

    @Mock
    private OrderRepository orderRepository;

    private Order order;

    @Test
    @DisplayName("주문 생성에 성공한다.")
    void successOrder(){
        //given
        Supporter supporter = createSupporter("hihi@gmail.com", "min");
        Reward reward = createReward("비빔밥",12000,10);

        List<Reward> rewards = new ArrayList<>();

        OrderCreateRequestDTO requestDTO = new OrderCreateRequestDTO(rewards);

        //when
        when(supporterService.getSupporter(anyLong())).thenReturn(supporter);
        when(rewardService.getReward(anyLong())).thenReturn(reward);
        when(orderRepository.save(any(Order.class))).thenReturn(order);

        //then
        Order order = Order.createOrder(anyLong(), requestDTO);
        assertThat(order.getOrderStatus()).isEqualTo(OrderStatus.APPROVED);

    }

    @Test
    @DisplayName("주문량이 재고 초과로 실패한다.")
    void failOrderByExceedStockRequest(){
        //given
        Supporter supporter = createSupporter("hihi@gmail.com", "min");
        Reward reward = createReward("비빔밥",12000,10);

        //when
        when(supporterService.getSupporter(anyLong())).thenReturn(supporter);
        when(rewardService.getReward(anyLong())).thenReturn(reward);
        when(orderRepository.save(any(Order.class))).thenReturn(order);

    }

    private Reward createReward(String name, int price, int quantity){
        return new Reward(name, price, quantity);
    }

    private Supporter createSupporter(String email, String name) {
        return new Supporter(email, name);
    }

}