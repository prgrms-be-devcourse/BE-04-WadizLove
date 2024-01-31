package com.prgrms.wadiz.domain.order.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.prgrms.wadiz.domain.funding.FundingCategory;
import com.prgrms.wadiz.domain.order.OrderController;
import com.prgrms.wadiz.domain.order.OrderStatus;
import com.prgrms.wadiz.domain.order.dto.request.OrderCreateRequestDTO;
import com.prgrms.wadiz.domain.orderReward.dto.request.OrderRewardCreateRequestDTO;
import com.prgrms.wadiz.domain.order.dto.response.OrderResponseDTO;
import com.prgrms.wadiz.domain.order.OrderService;
import com.prgrms.wadiz.domain.orderReward.dto.response.OrderRewardResponseDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.http.MediaType.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(OrderController.class)
class OrderControllerTest {
    final String BASE_URL = "/api/orders/";

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private OrderService orderService;

    @Test
    @DisplayName("[성공] : 주문 생성 요청 및 응답에 성공한다.")
    void createOrder() throws Exception {
        //given
        OrderCreateRequestDTO orderCreateReq = createOrderCreateRequestDTO();
        OrderResponseDTO orderRes = OrderResponseDTO.of(1L);
        given(orderService.createOrder(1L, orderCreateReq)).willReturn(orderRes);

        // when
        String body = objectMapper.writeValueAsString(orderCreateReq);
        ResultActions perform = mvc.perform(
                post(BASE_URL + "supporter/{supporterId}/new", 1)
                        .content(body)
                        .contentType(APPLICATION_JSON));

        // then
        perform.andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$.code").isNotEmpty())
                .andExpect(jsonPath("$.message").isNotEmpty())
                .andExpect(jsonPath("$.data.orderId").value(orderRes.orderId()));
    }

    @Test
    @DisplayName("[성공] : 서포터의 한 주문의 상세 정보를 조회할 수 있다.")
    void getSupporterPurchase() throws Exception {
        //given
        OrderResponseDTO orderRes = getOrderResponseDTO();
        given(orderService.getSupporterPurchase(1L, 2L)).willReturn(orderRes);

        // when
        ResultActions perform = mvc.perform(get(
                BASE_URL + "{orderId}/supporters/{supporterId}/purchase",
                1L,
                2L
        ));

        // then
        perform.andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$.code").isNotEmpty())
                .andExpect(jsonPath("$.message").isNotEmpty())
                .andExpect(jsonPath("$.data.orderId").value(orderRes.orderId()))
                .andExpect(jsonPath("$.data.postTitle").value(orderRes.postTitle()))
                .andExpect(jsonPath("$.data.makerBrand").value(orderRes.makerBrand()))
                .andExpect(jsonPath("$.data.orderRewardResponseDTOs[0].rewardName")
                        .value(orderRes.orderRewardResponseDTOs().get(0).rewardName()))
                .andExpect(jsonPath("$.data.orderStatus")
                        .value(orderRes.orderStatus().name()));
    }


    @Test
    @DisplayName("[성공] : 서포터가 본인이 주문한 주문 요약 목록을 조회할 수 있다.")
    void getSupporterPurchaseHistory() throws Exception {
        OrderResponseDTO orderRes = getOrderSummaryResponseDTO();

        // when
        given(orderService.getSupporterPurchaseHistory(1L)).willReturn(Arrays.asList(orderRes));

        ResultActions perform = mvc.perform(get(
                BASE_URL + "supporters/{supporterId}/history",
                1L
        ));

        // then
        perform.andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$.code").isNotEmpty())
                .andExpect(jsonPath("$.message").isNotEmpty())
                .andExpect(jsonPath("$.data[0].orderId").value(orderRes.orderId()))
                .andExpect(jsonPath("$.data[0].postTitle").value(orderRes.postTitle()))
                .andExpect(jsonPath("$.data[0].makerBrand").value(orderRes.makerBrand()))
                .andExpect(jsonPath("$.data[0].fundingCategory")
                        .value(orderRes.fundingCategory().name()));
    }


    @Test
    @DisplayName("[성공] : 메이커가 런칭한 프로젝트의 주문을 조회할 수 있다.")
    void getMakerProjectOrders() throws Exception {
        OrderResponseDTO orderRes = getOrderResponseDTO();

        given(orderService.getMakerProjectOrders(1L, 1L)).willReturn(Arrays.asList(orderRes));

        // when
        ResultActions perform = mvc.perform(get(
                BASE_URL + "projects/{projectId}/makers/{makerId}",
                1L,
                1L
        ));

        // then
        perform.andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$.code").isNotEmpty())
                .andExpect(jsonPath("$.message").isNotEmpty())
                .andExpect(jsonPath("$.data[0].orderId").value(orderRes.orderId()))
                .andExpect(jsonPath("$.data[0].orderRewardResponseDTOs[0].rewardName")
                        .value(orderRes.orderRewardResponseDTOs().get(0).rewardName()))
                .andExpect(jsonPath("$.data[0].orderRewardResponseDTOs[0].rewardDescription")
                        .value(orderRes.orderRewardResponseDTOs().get(0).rewardDescription()));
    }

    @Test
    @DisplayName("[성공] : 주문을 취소할 수 있다.")
    void cancelOrder() throws Exception {
        orderService.cancelOrder(1L, 1L);

        // when
        ResultActions perform = mvc.perform(put(
                BASE_URL + "{orderId}/supporters/{supporterId}",
                1L,
                1L
        ));

        // then
        perform.andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$.code").isNotEmpty())
                .andExpect(jsonPath("$.message").isNotEmpty());
    }

    private static OrderCreateRequestDTO createOrderCreateRequestDTO() {
        OrderRewardCreateRequestDTO orderRewardReq1 = new OrderRewardCreateRequestDTO(
                1L,
                10);
        OrderRewardCreateRequestDTO orderRewardReq2 = new OrderRewardCreateRequestDTO(
                2L,
                20);
        List<OrderRewardCreateRequestDTO> orderRewardReq = Arrays.asList(
                orderRewardReq1,
                orderRewardReq2
        );
        OrderCreateRequestDTO orderCreateReq = new OrderCreateRequestDTO(orderRewardReq);
        return orderCreateReq;
    }


    private static OrderResponseDTO getOrderResponseDTO() {
        OrderRewardResponseDTO orderRewardRes = getOrderRewardResponseDTO();
        List<OrderRewardResponseDTO> orderRewardResponses = Arrays.asList(orderRewardRes);
        OrderResponseDTO orderResponseDTO = OrderResponseDTO.of(
                1L,
                "맛깔난 참죽 펀딩",
                "씨제이",
                orderRewardResponses,
                10000 * 10,
                OrderStatus.COMPLETED
        );

        return orderResponseDTO;
    }

    private static OrderRewardResponseDTO getOrderRewardResponseDTO() {
        OrderRewardResponseDTO orderRewardRes1 = OrderRewardResponseDTO.of(
                "맛깔난 참죽",
                "참죽으로 사실 안만듦",
                10000,
                10
        );

        return orderRewardRes1;
    }

    private static OrderResponseDTO getOrderSummaryResponseDTO() {
        OrderResponseDTO orderRes = OrderResponseDTO.of(
                1L,
                "맛깔 참죽",
                "씨제이",
                FundingCategory.FOOD,
                LocalDateTime.now()
        );
        return orderRes;
    }


}