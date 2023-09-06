package com.prgrms.wadiz.domain.order.service;

import com.prgrms.wadiz.domain.order.dto.request.OrderCreateRequestDTO;
import com.prgrms.wadiz.domain.order.dto.response.OrderResponseDTO;
import com.prgrms.wadiz.domain.order.dto.request.OrderRewardRequestDTO;
import com.prgrms.wadiz.domain.order.entity.Order;
import com.prgrms.wadiz.domain.order.repository.OrderRepository;
import com.prgrms.wadiz.domain.orderReward.entity.OrderReward;
import com.prgrms.wadiz.domain.project.entity.Project;
import com.prgrms.wadiz.domain.reward.RewardType.RewardType;
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
import org.springframework.test.util.ReflectionTestUtils;

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
        Supporter supporter = createSupporter();
        ReflectionTestUtils.setField(
                supporter,
                "supporterId",
                1L
        );

        Reward reward = createReward();
        ReflectionTestUtils.setField(
                reward,
                "rewardId",
                1L
        );

        Project project = Project.builder().build();

        OrderReward orderReward = createOrderReward(
                reward,
                orderQuantity
        );

        List<OrderReward> orderRewards = new ArrayList<>();
        orderRewards.add(orderReward);

        Order order = createOrder(
                supporter,
                project,
                orderRewards
        );

        ReflectionTestUtils.setField(
                order,
                "orderId",
                1L
        );

        // make request
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
        given(orderRepository.findById(anyLong())).willReturn(Optional.of(order));

        //when
        orderService.createOrder(
                supporter.getSupporterId(),
                orderCreateReq
        );

        //then
        Order savedOrder = orderRepository.findById(order.getOrderId()).get();

        assertThat(order.getOrderId()).isEqualTo(savedOrder.getOrderId());
        assertThat(order.getSupporter()).isEqualTo(savedOrder.getSupporter());
        assertThat(order.getOrderRewards()).isEqualTo(savedOrder.getOrderRewards());
    }

//    @ParameterizedTest
//    @ValueSource(ints = {0,-1})
//    @DisplayName("[실패] 주문 수량 입력 0과 음수 입력으로 실패한다.") // 질문 dto에 대한 검증 테스트도 따로 해주고 도메인에서도 따로 해줘야 하는것인가? 도메인 레이어에서 테스트할 내용인가?
//    void failOrderByZeroAndNegativeOrderQuantity(int orderQuantity) {
//
//    }
//
//    @ParameterizedTest
//    @ValueSource(ints = {11,100})
//    @DisplayName("[실패] 주문이 재고 초과로 실패한다.")
//    void failOrderByExceededOrderQuantity(int orderQuantity) {
//        //given
//        Supporter supporter = createSupporter(1L, "hihi@gmail.com", "min");
//        Reward reward = createReward();
//        ReflectionTestUtils.setField(reward,"rewardId",1L);
//
//        OrderReward orderReward = new OrderReward(reward, 10000, orderQuantity);
//        List<OrderReward> orderRewards = new ArrayList<>();
//        orderRewards.add(orderReward);
//
//        // request
//        OrderRewardRequestDTO orderRewardReq = OrderRewardRequestDTO.builder()
//                .rewardId(1L)
//                .orderQuantity(orderQuantity)
//                .build();
//
//        List<OrderRewardRequestDTO> orderRewardReqs = new ArrayList<>();
//        orderRewardReqs.add(orderRewardReq);
//
//        OrderCreateRequestDTO orderCreateReq = OrderCreateRequestDTO.builder()
//                .orderRewards(orderRewardReqs)
//                .build();
//
//        given(supporterRepository.findById(anyLong())).willReturn(Optional.of(supporter));
//        given(rewardRepository.findById(anyLong())).willReturn(Optional.of(reward));
//
//        //when, then
//        assertThatThrownBy(() ->orderService.createOrder(supporter.getSupporterId(), orderCreateReq)).isInstanceOf(BaseException.class);
//    }

    private Reward createReward(){
        return Reward.builder()
                .rewardName("겁나 싼 우산")
                .rewardDescription("수분 충전 가능")
                .rewardPrice(10000)
                .rewardQuantity(10)
                .rewardType(RewardType.EARLY_BIRD)
                .build();
    }

    private Supporter createSupporter() {
        return Supporter.builder()
                .supporterName("min")
                .supporterEmail("test@gmail.com")
                .build();
    }

    private OrderReward createOrderReward(
            Reward reward,
            Integer orderQuantity
    ){
        return OrderReward.builder()
                .reward(reward)
                .orderRewardPrice(10000)
                .orderRewardQuantity(orderQuantity)
                .build();
    }

    private Order createOrder(
            Supporter supporter,
            Project project,
            List<OrderReward> orderRewards
    ){
        return Order.builder()
                .supporter(supporter)
                .project(project)
                .build();
    }

}