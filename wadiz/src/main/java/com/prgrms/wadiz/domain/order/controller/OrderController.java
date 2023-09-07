package com.prgrms.wadiz.domain.order.controller;

import com.prgrms.wadiz.domain.order.dto.request.OrderCreateRequestDTO;
import com.prgrms.wadiz.domain.order.dto.response.OrderResponseDTO;
import com.prgrms.wadiz.domain.order.service.OrderService;
import com.prgrms.wadiz.global.util.resTemplate.ResponseFactory;
import com.prgrms.wadiz.global.util.resTemplate.ResponseTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("orders/")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("new/supporter/{supporterId}")
    public ResponseEntity<ResponseTemplate> createOrder(
            @PathVariable Long supporterId,
            OrderCreateRequestDTO orderCreateRequestDto
    ){
        orderService.createOrder(supporterId, orderCreateRequestDto);

        return ResponseEntity.ok(ResponseFactory.getSuccessResult());
    }

    @GetMapping("{orderId}/supporters/{supporterId}")
    public ResponseEntity<ResponseTemplate> getSupporterPurchase(
            @PathVariable Long orderId,
            @PathVariable Long supporterId
    ){
        OrderResponseDTO orderResponseDTO = orderService.getSupporterPurchase(orderId, supporterId);

        return ResponseEntity.ok(ResponseFactory.getSingleResult(orderResponseDTO));
    }

    @GetMapping("supporters/{supporterId}")
    public ResponseEntity<ResponseTemplate> getSupporterPurchaseHistory(
            @PathVariable Long supporterId
    ){
        List<OrderResponseDTO> orderResponseDTOs = orderService.getSupporterPurchaseHistory(supporterId);

        return ResponseEntity.ok(ResponseFactory.getSingleResult(orderResponseDTOs));
    }

    @GetMapping("projects/{projectId}/makers/{makerId}")
    public ResponseEntity<ResponseTemplate> getMakerProjectOrders(
            @PathVariable Long projectId,
            @PathVariable Long makerId
    ){
        List<OrderResponseDTO> orderResponseDTOs = orderService.getMakerProjectOrders(projectId,makerId);

        return ResponseEntity.ok(ResponseFactory.getSingleResult(orderResponseDTOs));
    }

    @PutMapping("{supporterId}/{orderId}")
    public void cancelOrder(
        @PathVariable Long supporterId,
        @PathVariable Long orderId
    ){
        orderService.cancelOrder(supporterId, orderId);
    }

}
