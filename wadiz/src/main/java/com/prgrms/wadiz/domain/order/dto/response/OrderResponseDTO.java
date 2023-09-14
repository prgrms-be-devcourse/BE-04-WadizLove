package com.prgrms.wadiz.domain.order.dto.response;

import com.prgrms.wadiz.domain.funding.FundingCategory;
import com.prgrms.wadiz.domain.order.OrderStatus;
import com.prgrms.wadiz.domain.orderReward.dto.response.OrderRewardResponseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import java.time.LocalDateTime;
import java.util.List;

@Schema(description = "주문 관련 응답 DTO")
@Builder
public record OrderResponseDTO(
        @Schema(description = "주문 id 값")
        Long orderId,

        @Schema(description = "게시물 제목")
        String postTitle,

        @Schema(description = "판매자 브랜드")
        String makerBrand,

        @Schema(description = "주문 상품 리스트(리워드 정보 및 주문 수량)")
        List<OrderRewardResponseDTO> orderRewardResponseDTOs,

        @Schema(description = "펀딩 카테고리")
        FundingCategory fundingCategory,

        @Schema(description = "총 주문 금액")
        Integer totalOrderPrice,

        @Schema(description = "주문 생성 일자")
        LocalDateTime createdAt,

        @Schema(description = "주문 상태")
        OrderStatus orderStatus
) {
    public static OrderResponseDTO of(Long orderId){
        return OrderResponseDTO.builder()
                .orderId(orderId)
                .build();
    }

    public static OrderResponseDTO of(
            Long orderId,
            String postTitle,
            String makerBrand,
            List<OrderRewardResponseDTO> orderRewardResponseDTOs,
            Integer totalOrderPrice,
            OrderStatus orderStatus
    ){
        return OrderResponseDTO.builder()
                .orderId(orderId)
                .postTitle(postTitle)
                .makerBrand(makerBrand)
                .orderRewardResponseDTOs(orderRewardResponseDTOs)
                .totalOrderPrice(totalOrderPrice)
                .orderStatus(orderStatus)
                .build();
    }

    public static OrderResponseDTO of(
            Long orderId,
            List<OrderRewardResponseDTO> orderRewardResponseDTOs,
            Integer totalOrderPrice,
            OrderStatus orderStatus
    ){
        return OrderResponseDTO.builder()
                .orderId(orderId)
                .orderRewardResponseDTOs(orderRewardResponseDTOs)
                .totalOrderPrice(totalOrderPrice)
                .orderStatus(orderStatus)
                .build();
    }

    public static OrderResponseDTO of(
            Long orderId,
            String postTitle,
            String makerBrand,
            FundingCategory fundingCategory,
            LocalDateTime createdAt
    ){
        return OrderResponseDTO.builder()
                .orderId(orderId)
                .postTitle(postTitle)
                .makerBrand(makerBrand)
                .fundingCategory(fundingCategory)
                .createdAt(createdAt)
                .build();
    }
}