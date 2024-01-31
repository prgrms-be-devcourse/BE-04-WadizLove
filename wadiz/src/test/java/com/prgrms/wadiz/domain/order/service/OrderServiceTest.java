package com.prgrms.wadiz.domain.order.service;

import com.prgrms.wadiz.domain.funding.FundingCategory;
import com.prgrms.wadiz.domain.funding.Funding;
import com.prgrms.wadiz.domain.funding.FundingRepository;
import com.prgrms.wadiz.domain.maker.entity.Maker;
import com.prgrms.wadiz.domain.order.OrderService;
import com.prgrms.wadiz.domain.order.OrderStatus;
import com.prgrms.wadiz.domain.order.dto.request.OrderCreateRequestDTO;
import com.prgrms.wadiz.domain.order.dto.response.OrderResponseDTO;
import com.prgrms.wadiz.domain.order.Order;
import com.prgrms.wadiz.domain.order.OrderRepository;
import com.prgrms.wadiz.domain.orderReward.dto.request.OrderRewardCreateRequestDTO;
import com.prgrms.wadiz.domain.orderReward.dto.response.OrderRewardResponseDTO;
import com.prgrms.wadiz.domain.orderReward.OrderReward;
import com.prgrms.wadiz.domain.post.entity.Post;
import com.prgrms.wadiz.domain.post.PostRepository;
import com.prgrms.wadiz.domain.project.Project;
import com.prgrms.wadiz.domain.project.repository.ProjectRepository;
import com.prgrms.wadiz.domain.reward.entity.Reward;
import com.prgrms.wadiz.domain.reward.repository.RewardRepository;
import com.prgrms.wadiz.domain.supporter.entity.Supporter;
import com.prgrms.wadiz.domain.supporter.repository.SupporterRepository;
import com.prgrms.wadiz.global.util.exception.BaseException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.prgrms.wadiz.domain.reward.RewardType.EARLY_BIRD;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
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

    @Mock
    private ProjectRepository projectRepository;

    @Nested
    @DisplayName("주문 생성 테스트")
    class orderCreateTest {
        Project project = createProject();
        Supporter supporter = createSupporter();
        Reward reward = createReward(project);

        @ParameterizedTest
        @ValueSource(ints = {1,10})
        @DisplayName("[성공] 주문을 생성한다.")
        void successOrder(int orderQuantity){
            //given
            Funding funding = createFunding(project);

            Order order = Order.builder()
                    .supporter(supporter)
                    .project(project)
                    .build();
            ReflectionTestUtils.setField(order,"orderId",1L);

            // make request
            OrderCreateRequestDTO orderCreateReq = createOrderRequest(orderQuantity);

            given(supporterRepository.findById(anyLong())).willReturn(Optional.of(supporter));
            given(rewardRepository.findById(anyLong())).willReturn(Optional.ofNullable(reward));
            given(fundingRepository.findByProject_ProjectId(anyLong())).willReturn(Optional.ofNullable(funding));
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
            // make request
            OrderCreateRequestDTO orderCreateReq = createOrderRequest(orderQuantity);

            given(supporterRepository.findById(anyLong())).willReturn(Optional.of(supporter));
            given(rewardRepository.findById(anyLong())).willReturn(Optional.of(reward));

            //when, then
            assertThatThrownBy(() ->orderService.createOrder(supporter.getSupporterId(), orderCreateReq))
                    .isInstanceOf(BaseException.class);
        }
    }

    @Nested
    @DisplayName("주문 조회 테스트")
    class orderGetTest {
        Project project = createProject();
        Supporter supporter = createSupporter();
        Reward reward = createReward(project);
        Post post = createPost(project);
        Maker maker = createMaker();
        Funding funding = createFunding(project);

        OrderReward orderReward = new OrderReward(
                reward,
                reward.getRewardPrice(),
                1
        );

        Order order = Order.builder()
                .project(project)
                .supporter(supporter)
                .build();


        @Test
        @DisplayName("[성공] 서포터는 한 주문의 상세 정보를 확인할 수 있다.")
        void  successSupporterGetPurchase(){
            //given
            ReflectionTestUtils.setField(
                    project,
                    "maker",
                    maker
            );
            order.addOrderReward(orderReward);

            List<OrderRewardResponseDTO> orderRewardResponseDTOS = Arrays.asList(
                    OrderRewardResponseDTO.of(
                            orderReward.getReward().getRewardName(),
                            orderReward.getReward().getRewardDescription(),
                            orderReward.getOrderRewardPrice(),
                            orderReward.getOrderRewardQuantity()
                    )
            );

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
            ReflectionTestUtils.setField(
                    project,
                    "maker",
                    maker
            );
            OrderResponseDTO orderRes = OrderResponseDTO.of(
                    order.getOrderId(),
                    post.getPostTitle(),
                    maker.getMakerBrand(),
                    funding.getFundingCategory(),
                    LocalDateTime.now()
            );

            given(orderRepository.findAllBySupporter_SupporterId(anyLong())).willReturn(Optional.of(List.of(order)));
            given(postRepository.findByProject_ProjectId(anyLong())).willReturn(Optional.of(post));
            given(fundingRepository.findByProject_ProjectId(anyLong())).willReturn(Optional.of(funding));

            List<OrderResponseDTO> purchaseHis = orderService.getSupporterPurchaseHistory(1L);

            assertThat(purchaseHis.get(0).postTitle()).isEqualTo(orderRes.postTitle());
            assertThat(purchaseHis.get(0).fundingCategory()).isEqualTo(orderRes.fundingCategory());
            assertThat(purchaseHis.get(0).makerBrand()).isEqualTo(orderRes.makerBrand());
        }

        @Test
        @DisplayName("[성공] 메이커의 프로젝트의 주문 목록 조회에 성공한다.")
        void successGetMakerProjectOrders(){
            ReflectionTestUtils.setField(
                    project,
                    "maker",
                    maker
            );
            order.addOrderReward(orderReward);

            List<OrderRewardResponseDTO> orderRewardResponseDTOS = Arrays.asList(
                    OrderRewardResponseDTO.of(
                            orderReward.getReward().getRewardName(),
                            orderReward.getReward().getRewardDescription(),
                            orderReward.getOrderRewardPrice(),
                            orderReward.getOrderRewardQuantity()
                    ));

            OrderResponseDTO orderRes = OrderResponseDTO.of(
                    order.getOrderId(),
                    orderRewardResponseDTOS,
                    order.getTotalOrderPrice(),
                    order.getOrderStatus()
            );

            given(orderRepository.findAllByProject_ProjectId(anyLong())).willReturn(Optional.of(List.of(order)));

            List<OrderResponseDTO> makerProjectOrders = orderService.getMakerProjectOrders(1L, 1L);

            assertThat(orderRes.orderId()).isEqualTo(makerProjectOrders.get(0).orderId());
            assertThat(orderRes.orderStatus()).isEqualTo(makerProjectOrders.get(0).orderStatus());
            assertThat(orderRes.orderRewardResponseDTOs().get(0).orderRewardQuantity())
                    .isEqualTo(makerProjectOrders.get(0).orderRewardResponseDTOs().get(0).orderRewardQuantity());

        }
    }


    @Test
    @DisplayName("[성공] 주문 취소에 성공한다.")
    void successCancelOrder(){
        Supporter supporter = createSupporter();
        Project project = createProject();
        Reward reward = createReward(project);
        Funding funding = createFunding(project);

        OrderReward orderReward = new OrderReward(
                reward,
                reward.getRewardPrice(),
                1
        );

        Order order = Order.builder()
                .project(project)
                .supporter(supporter)
                .build();

        order.addOrderReward(orderReward);

        given(orderRepository.findById(anyLong())).willReturn(Optional.of(order));
        given(fundingRepository.findByProject_ProjectId(order.getProject().getProjectId()))
                .willReturn(Optional.ofNullable(funding));

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

    private Reward createReward(Project project){
        Reward reward = Reward.builder()
                .project(project)
                .rewardName("겁나 싼 우산")
                .rewardDescription("수분 충전 가능")
                .rewardPrice(10000)
                .rewardQuantity(10)
                .rewardType(EARLY_BIRD)
                .build();
        ReflectionTestUtils.setField(
                reward,
                "rewardId",
                1L
        );

        return reward;
    }

    private Post createPost(Project project) {
        Post post = Post.builder()
                .project(project)
                .postTitle("postTitle")
                .build();
        ReflectionTestUtils.setField(
                post,
                "postId",
                1L
        );
        return post;
    }

    private Funding createFunding(Project project){
        Funding funding = Funding.builder()
                .project(project)
                .fundingStartAt(LocalDateTime.of(2024,9,24,10,00,00))
                .fundingEndAt(LocalDateTime.of(2024,9,25,10,00,00))
                .fundingTargetAmount(100000)
                .fundingCategory(FundingCategory.TECH)
                .build();
        ReflectionTestUtils.setField(
                funding,
                "fundingId",
                1L
        );

        return funding;
    }

    private Project createProject(){
        Project project = Project.builder().build();
        ReflectionTestUtils.setField(
                project,
                "projectId",
                1L
        );

        return project;
    }

    private static Maker createMaker() {
        Maker maker = Maker.builder()
                .makerBrand("cj")
                .build();
        ReflectionTestUtils.setField(
                maker,
                "makerId",
                1L
        );
        return maker;
    }

    private static OrderCreateRequestDTO createOrderRequest(int orderQuantity) {
        List<OrderRewardCreateRequestDTO> orderRewardReqs = Arrays.asList(
                OrderRewardCreateRequestDTO.builder()
                        .rewardId(1L)
                        .orderQuantity(orderQuantity)
                        .build());

        OrderCreateRequestDTO orderCreateReq = OrderCreateRequestDTO.builder()
                .orderRewards(orderRewardReqs)
                .build();
        return orderCreateReq;
    }

}