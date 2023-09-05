package com.prgrms.wadiz.domain.order.controller;

import com.prgrms.wadiz.domain.order.dto.request.OrderCreateRequestDTO;
import com.prgrms.wadiz.domain.order.dto.response.OrderResponseDTO;
import com.prgrms.wadiz.domain.order.service.OrderService;
import com.prgrms.wadiz.global.util.resTemplate.ResponseFactory;
import com.prgrms.wadiz.global.util.resTemplate.ResponseTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<ResponseTemplate> getPurchase(
            @PathVariable Long orderId,
            @PathVariable Long supporterId
    ){
        OrderResponseDTO orderResponseDTO = orderService.getPurchase(orderId, supporterId);

        return ResponseEntity.ok(ResponseFactory.getSingleResult(orderResponseDTO));
    }

    @PatchMapping("{supporterId}/{orderId}")
    public void cancelOrder(
        @PathVariable Long supporterId,
        @PathVariable Long orderId
    ){

        orderService.cancelOrder(supporterId, orderId);
    }

}
