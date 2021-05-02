package com.lokiechart.www.controller.order;

import com.lokiechart.www.dao.account.dto.AccountResponse;
import com.lokiechart.www.service.account.AccountService;
import com.lokiechart.www.service.order.OrderService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;

/**
 * @author SeongRok.Oh
 * @since 2021/05/02
 */
@Api(value = "강제 주문 컨트롤러")
@RequiredArgsConstructor
@RequestMapping(value = "/order")
@RestController
public class UpbitOrderController {

    private final OrderService upbitOrderService;
    private final AccountService upbitAccountService;

    @GetMapping("/meomchwo")
    public void meomchwo(@NotNull @RequestParam String email) {
        AccountResponse accountResponse = upbitAccountService.getAccountByEmail(email);
        upbitOrderService.sellAllByAccount(accountResponse);
    }
}
