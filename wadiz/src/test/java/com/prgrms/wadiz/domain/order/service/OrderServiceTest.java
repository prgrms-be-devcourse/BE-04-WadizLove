package com.prgrms.wadiz.domain.order.service;

import com.prgrms.wadiz.domain.funding.FundingCategory;
import com.prgrms.wadiz.domain.funding.entity.Funding;
import com.prgrms.wadiz.domain.funding.repository.FundingRepository;
import com.prgrms.wadiz.domain.maker.entity.Maker;
import com.prgrms.wadiz.domain.order.OrderStatus;
import com.prgrms.wadiz.domain.order.dto.request.OrderCreateRequestDTO;
import com.prgrms.wadiz.domain.order.dto.response.OrderResponseDTO;
import com.prgrms.wadiz.domain.orderReward.dto.request.OrderRewardCreateRequestDTO;
import com.prgrms.wadiz.domain.order.entity.Order;
import com.prgrms.wadiz.domain.order.repository.OrderRepository;
import com.prgrms.wadiz.domain.orderReward.dto.response.OrderRewardResponseDTO;
import com.prgrms.wadiz.domain.orderReward.entity.OrderReward;
import com.prgrms.wadiz.domain.post.entity.Post;
import com.prgrms.wadiz.domain.post.repository.PostRepository;
import com.prgrms.wadiz.domain.project.entity.Project;
import com.prgrms.wadiz.domain.reward.RewardType.RewardType;
import com.prgrms.wadiz.domain.reward.entity.Reward;
import com.prgrms.wadiz.domain.reward.repository.RewardRepository;
import com.prgrms.wadiz.domain.supporter.entity.Supporter;
import com.prgrms.wadiz.domain.supporter.repository.SupporterRepository;
import com.prgrms.wadiz.global.util.exception.BaseException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDateTime;
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
    private PostRepository postRepository;

    @Mock
    private RewardRepository rewardRepository;

    @Mock
    private FundingRepository fundingRepository;

    @ParameterizedTest
    @ValueSource(ints = {1,10})
    @DisplayName("[성공] 주문을 생성한다.")
    void successOrder(int orderQuantity){
        //given
        Supporter supporter = createSupporter();

        Reward reward = createReward();

        Project project = Project.builder().build();

        List<OrderReward> orderRewards = Arrays.asList(
                createOrderReward(
                reward,
                orderQuantity
        ));

        Order order = createOrder(
                supporter,
                project
        );
        for (OrderReward orderReward : orderRewards){
            order.addOrderReward(orderReward);
        }

        // make request
        List<OrderRewardCreateRequestDTO> orderRewardReqs = Arrays.asList(
                OrderRewardCreateRequestDTO.builder()
                .rewardId(1L)
                .orderQuantity(orderQuantity)
                .build());

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


    @ParameterizedTest
    @ValueSource(ints = {11,100})
    @DisplayName("[실패] 주문이 재고 초과로 실패한다.")
    void failOrderByExceededOrderQuantity(int orderQuantity) {
        //given
        Supporter supporter = createSupporter();

        Reward reward = createReward();

        // make request
        OrderRewardCreateRequestDTO orderRewardReq = OrderRewardCreateRequestDTO.builder()
                .rewardId(1L)
                .orderQuantity(orderQuantity)
                .build();

        List<OrderRewardCreateRequestDTO> orderRewardReqs = Arrays.asList(orderRewardReq);

        OrderCreateRequestDTO orderCreateReq = OrderCreateRequestDTO.builder()
                .orderRewards(orderRewardReqs)
                .build();

        given(supporterRepository.findById(anyLong())).willReturn(Optional.of(supporter));
        given(rewardRepository.findById(anyLong())).willReturn(Optional.of(reward));

        //when, then
        assertThatThrownBy(() ->orderService.createOrder(supporter.getSupporterId(), orderCreateReq))
                .isInstanceOf(BaseException.class);
    }

    @Test
    @DisplayName("[성공] 서포터는 한 주문의 상세 정보를 확인할 수 있다.")
    void  successSupporterGetPurchase(){
        //given
        Supporter supporter = createSupporter();

        Reward reward = createReward();

        Maker maker = Maker.builder()
                .makerBrand("cj")
                .build();
        ReflectionTestUtils.setField(
                maker,
                "makerId",
                1L
        );

        Project project = Project.builder()
                .maker(maker).build();
        ReflectionTestUtils.setField(
                project,
                "projectId",
                1L
        );

        Post post = Post.builder()
                .project(project)
                .postTitle("postTitle")
                .build();
        ReflectionTestUtils.setField(
                post,
                "postId",
                1L
        );

        List<OrderReward> orderRewards = Arrays.asList(
                createOrderReward(
                        reward,
                        2
                )
        );

        Order order = createOrder(
                supporter,
                project
        );
        for(OrderReward orderReward : orderRewards){
            order.addOrderReward(orderReward);
        }

        List<OrderRewardResponseDTO> orderRewardResponseDTOS = Arrays.asList(
                OrderRewardResponseDTO.of(
                orderRewards.get(0).getReward().getRewardName(),
                orderRewards.get(0).getReward().getRewardDescription(),
                orderRewards.get(0).getOrderRewardPrice(),
                orderRewards.get(0).getOrderRewardQuantity()
        ));

        OrderResponseDTO orderRes = OrderResponseDTO.of(
                order.getOrderId(),
                post.getPostTitle(),
                maker.getMakerBrand(),
                orderRewardResponseDTOS,
                order.getTotalOrderPrice(),
                OrderStatus.COMPLETED
        );

         given(postRepository.findByProject_ProjectId(anyLong())).willReturn(Optional.of(post));
         given(orderRepository.findById(anyLong())).willReturn(Optional.of(order));

         OrderResponseDTO realResponseDTO = orderService.getSupporterPurchase(1L,1L);

        assertThat(orderRes.orderId()).isEqualTo(realResponseDTO.orderId());
        assertThat(orderRes.postTitle()).isEqualTo(realResponseDTO.postTitle());
        assertThat(orderRes.fundingCategory()).isEqualTo(realResponseDTO.fundingCategory());
        assertThat(orderRes.makerBrand()).isEqualTo(realResponseDTO.makerBrand());
        assertThat(orderRes.orderRewardResponseDTOs().get(0).orderRewardQuantity())
                .isEqualTo(realResponseDTO.orderRewardResponseDTOs().get(0).orderRewardQuantity());
    }

    @Test
    @DisplayName("[성공] 서포터의 주문 목록 조회에 성공한다.")
    void SuccessGetSupporterPurchaseHistory(){

        //given
        Supporter supporter = createSupporter();

        Reward reward = createReward();

        Maker maker = Maker.builder()
                .makerBrand("cj")
                .build();
        ReflectionTestUtils.setField(
                maker,
                "makerId",
                1L
        );

        Project project = Project.builder()
                .maker(maker).build();
        ReflectionTestUtils.setField(
                project,
                "projectId",
                1L
        );

        Post post = Post.builder()
                .project(project)
                .postTitle("postTitle")
                .build();
        ReflectionTestUtils.setField(
                post,
                "postId",
                1L
        );

        Funding funding = Funding.builder()
                .fundingCategory(FundingCategory.TECH)
                .build();
        ReflectionTestUtils.setField(
                funding,
                "fundingId",
                1L
        );

        List<OrderReward> orderRewards = Arrays.asList(
                createOrderReward(
                        reward,
                        2
                )
        );

        Order order = createOrder(
                supporter,
                project
        );
        for(OrderReward orderReward : orderRewards){
            order.addOrderReward(orderReward);
        }
        List<Order> orders = Arrays.asList(order);

        OrderResponseDTO orderRes = OrderResponseDTO.of(
                order.getOrderId(),
                post.getPostTitle(),
                maker.getMakerBrand(),
                funding.getFundingCategory(),
                LocalDateTime.now()
        );

        given(orderRepository.findAllBySupporter_SupporterId(anyLong())).willReturn(Optional.of(orders));
        given(postRepository.findByProject_ProjectId(anyLong())).willReturn(Optional.of(post));
        given(fundingRepository.findByProjectId(anyLong())).willReturn(Optional.of(funding));

        List<OrderResponseDTO> purchaseHis = orderService.getSupporterPurchaseHistory(1L);

        assertThat(purchaseHis.get(0).postTitle()).isEqualTo(orderRes.postTitle());
        assertThat(purchaseHis.get(0).fundingCategory()).isEqualTo(orderRes.fundingCategory());
        assertThat(purchaseHis.get(0).makerBrand()).isEqualTo(orderRes.makerBrand());
    }

    @Test
    @DisplayName("[성공] 메이커의 프로젝트의 주문 목록 조회에 성공한다.")
    void successGetMakerProjectOrders(){
        //given
        Supporter supporter = createSupporter();

        Reward reward = createReward();

        Maker maker = Maker.builder()
                .makerBrand("cj")
                .build();
        ReflectionTestUtils.setField(
                maker,
                "makerId",
                1L
        );

        Project project = Project.builder()
                .maker(maker)
                .build();
        ReflectionTestUtils.setField(
                project,
                "projectId",
                1L
        );

        List<OrderReward> orderRewards = Arrays.asList(
                createOrderReward(
                        reward,
                        2
                )
        );

        Order order = createOrder(
                supporter,
                project
        );
        for(OrderReward orderReward : orderRewards){
            order.addOrderReward(orderReward);
        }
        List<Order> orders = Arrays.asList(order);

        // 수정
        List<OrderRewardResponseDTO> orderRewardResponseDTOS = Arrays.asList(
                OrderRewardResponseDTO.of(
                        orderRewards.get(0).getReward().getRewardName(),
                        orderRewards.get(0).getReward().getRewardDescription(),
                        orderRewards.get(0).getOrderRewardPrice(),
                        orderRewards.get(0).getOrderRewardQuantity()
                ));

        OrderResponseDTO orderRes = OrderResponseDTO.of(
                order.getOrderId(),
                orderRewardResponseDTOS,
                order.getTotalOrderPrice(),
                order.getOrderStatus()
        );

        given(orderRepository.findAllByProject_ProjectId(anyLong())).willReturn(Optional.of(orders));

        List<OrderResponseDTO> makerProjectOrders = orderService.getMakerProjectOrders(1L, 1L);

        assertThat(orderRes.orderId()).isEqualTo(makerProjectOrders.get(0).orderId());
        assertThat(orderRes.orderStatus()).isEqualTo(makerProjectOrders.get(0).orderStatus());
        assertThat(orderRes.orderRewardResponseDTOs().get(0).orderRewardQuantity())
                .isEqualTo(makerProjectOrders.get(0).orderRewardResponseDTOs().get(0).orderRewardQuantity());
    }

    @Test
    @DisplayName("[성공] 주문 취소에 성공한다.")
    void successCancelOrder(){
        Supporter supporter = createSupporter();

        Reward reward = createReward();

        List<OrderReward> orderRewards = Arrays.asList(
                createOrderReward(
                        reward,
                        2
                )
        );

        Project project = Project.builder().build();
        ReflectionTestUtils.setField(
                project,
                "projectId",
                1L
        );

        Order order = createOrder(
                supporter,
                project
        );
        for(OrderReward orderReward : orderRewards){
            order.addOrderReward(orderReward);
        }

        given(orderRepository.findById(anyLong())).willReturn(Optional.of(order));

        orderService.cancelOrder(supporter.getSupporterId(),1L);

        assertThat(order.getOrderStatus()).isEqualTo(OrderStatus.CANCELED);
        //질문 취소에 대한 재고확인 처리도 해줘야 하는지
    }

    private Supporter createSupporter() {
        Supporter supporter = Supporter.builder()
                .supporterName("min")
                .supporterEmail("test@gmail.com")
                .build();
        ReflectionTestUtils.setField(
                supporter,
                "supporterId",
                1L
        );

        return supporter;
    }

    private Reward createReward(){
        Reward reward = Reward.builder()
                .rewardName("겁나 싼 우산")
                .rewardDescription("수분 충전 가능")
                .rewardPrice(10000)
                .rewardQuantity(10)
                .rewardType(RewardType.EARLY_BIRD)
                .build();
        ReflectionTestUtils.setField(
                reward,
                "rewardId",
                1L
        );

        return reward;
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
            Project project
    ){
        Order order = Order.builder()
                .supporter(supporter)
                .project(project)
                .build();
        ReflectionTestUtils.setField(
                order,
                "orderId",
                1L
        );

        return order;
    }

//    @ParameterizedTest
//    @ValueSource(ints = {0,-1})
//    @DisplayName("[실패] 주문 수량 입력 0과 음수 입력으로 실패한다.") // 질문 dto에 대한 검증 테스트도 따로 해주고 도메인에서도 따로 해줘야 하는것인가? 도메인 레이어에서 테스트할 내용인가?
//    void failOrderByZeroAndNegativeOrderQuantity(int orderQuantity) {
//
//    }



}