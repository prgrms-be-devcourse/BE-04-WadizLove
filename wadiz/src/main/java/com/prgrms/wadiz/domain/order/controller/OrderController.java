package com.prgrms.wadiz.domain.order.controller;

import com.prgrms.wadiz.domain.order.dto.request.OrderCreateRequestDTO;
import com.prgrms.wadiz.domain.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("orders/")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("new/{supporterId}")
    public void createOrder(@PathVariable Long supporterId, OrderCreateRequestDTO orderCreateRequestDto){
        orderService.createOrder(supporterId, orderCreateRequestDto);
    }

    @GetMapping("{orderId}")
    public void getOrder(@PathVariable Long orderId){
        orderService.getOrder(orderId);
    }
}
