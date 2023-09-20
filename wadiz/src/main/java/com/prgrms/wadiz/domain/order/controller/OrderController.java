package com.prgrms.wadiz.domain.order.controller;

import com.prgrms.wadiz.domain.order.dto.request.OrderCreateRequestDTO;
import com.prgrms.wadiz.domain.order.dto.response.OrderResponseDTO;
import com.prgrms.wadiz.domain.order.service.OrderService;
import com.prgrms.wadiz.global.annotation.ApiErrorCodeExample;
import com.prgrms.wadiz.global.util.exception.BaseException;
import com.prgrms.wadiz.global.util.exception.ErrorCode;
import com.prgrms.wadiz.global.util.resTemplate.ResponseFactory;
import com.prgrms.wadiz.global.util.resTemplate.ResponseTemplate;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Tag(name = "orders", description = "주문 API")
@RestController
@RequestMapping("/api/orders/")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    /**
     * Order create
     */
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "주문 생성 성공"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "주문 생성 실패"
            )
    })
    @ApiErrorCodeExample(value = ErrorCode.class, domain = "Order")
    @Operation(
            summary = "주문 생성",
            description = "서포터 id와 주문 요청 양식(OrderCreateRequestDTO)을 이용하여 주문을 생성한다."
    )
    @PostMapping("supporter/{supporterId}/new")
    public ResponseEntity<ResponseTemplate> createOrder(
            @Parameter(description = "서포터 id") @PathVariable Long supporterId,
            @RequestBody @Valid OrderCreateRequestDTO orderCreateRequestDto
    ){
        OrderResponseDTO orderResponseDTO = orderService.createOrder(supporterId, orderCreateRequestDto);

        return ResponseEntity.ok(ResponseFactory.getSingleResult(orderResponseDTO));
    }

    /**
     * Order cancel
     */
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "주문 취소 성공"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "주문 취소 실패"
            )
    })
    @ApiErrorCodeExample(value = ErrorCode.class, domain = "Order")
    @Operation(
            summary = "주문 취소",
            description = "주문 Id와 주문한 서포터 Id를 이용하여 주문을 취소한다."
    )
    @PutMapping("{orderId}/supporters/{supporterId}")
    public ResponseEntity<ResponseTemplate> cancelOrder(
            @Parameter(description = "주문 id") @PathVariable Long orderId,
            @Parameter(description = "서포터 id") @PathVariable Long supporterId
    ){
        orderService.cancelOrder(supporterId, orderId);

        return ResponseEntity.ok(ResponseFactory.getSuccessResult());
    }

    /**
     * 서포터 주문 기록 조회(단건)
     */
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "서포터의 특정 주문 조회 성공"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "주문 조회 실패"
            )
    })
    @ApiErrorCodeExample(value = ErrorCode.class, domain = "Order")
    @Operation(
            summary = "서포터 주문 기록 조호(단건)",
            description = "주문 Id와 주문한 서포터 Id를 이용하여 특정 주문 정보를 조회한다."
    )
    @GetMapping("{orderId}/supporters/{supporterId}/purchase")
    public ResponseEntity<ResponseTemplate> getSupporterPurchase(
            @Parameter(description = "주문 id") @PathVariable Long orderId,
            @Parameter(description = "서포터 id") @PathVariable Long supporterId
    ){
        OrderResponseDTO orderResponseDTO = orderService.getSupporterPurchase(orderId, supporterId);

        return ResponseEntity.ok(ResponseFactory.getSingleResult(orderResponseDTO));
    }

    /**
     * 서포터 주문 기록 조회(다건)
     */
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "서포터의 주문 목록 조회(요약된 주문 조회) 성공"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "주문 조회 실패"
            )
    })
    @ApiErrorCodeExample(value = ErrorCode.class, domain = "Order")
    @Operation(
            summary = "서포터 주문 기록 조회(다건)",
            description = "주문한 서포터 Id를 이용하여 주문 정보 목록을 조회한다."
    )
    @GetMapping("supporters/{supporterId}/history")
    public ResponseEntity<ResponseTemplate> getSupporterPurchaseHistory(@Parameter(description = "서포터 id") @PathVariable Long supporterId){
        List<OrderResponseDTO> orderResponseDTOs = orderService.getSupporterPurchaseHistory(supporterId);

        return ResponseEntity.ok(ResponseFactory.getListResult(orderResponseDTOs));
    }

    /**
     * 메이커 주문 기록 조회
     */
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "메이커의 주문 조회(요약된 주문 조회) 성공"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "주문 조회 실패"
            )
    })
    @ApiErrorCodeExample(value = ErrorCode.class, domain = "Order")
    @Operation(
            summary = "메이커 프로젝트 기록 조회(다건)",
            description = "프로젝트 Id와 메이커 Id를 이용하여 메이커 주문 목록을 조회한다."
    )
    @GetMapping("projects/{projectId}/makers/{makerId}")
    public ResponseEntity<ResponseTemplate> getMakerProjectOrders(
            @Parameter(description = "프로젝트 Id") @PathVariable Long projectId,
            @Parameter(description = "메이커 Id") @PathVariable Long makerId
    ){
        List<OrderResponseDTO> orderResponseDTOs = orderService.getMakerProjectOrders(projectId,makerId);

        return ResponseEntity.ok(ResponseFactory.getListResult(orderResponseDTOs));
    }
}
