package com.lokiechart.www.controller;

import com.lokiechart.www.service.order.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author SeongRok.Oh
 * @since 2021/04/21
 */
@RequiredArgsConstructor
@RequestMapping(value = "/test")
@RestController
public class TestController {
    private final OrderService upbitOrderService;
    @GetMapping
    public void test(){
        upbitOrderService.tradeByAccount("");
    }
}
