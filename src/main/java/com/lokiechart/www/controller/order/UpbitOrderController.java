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
import java.util.Objects;
import java.util.Random;

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
    private long randomNumber;

    @GetMapping("/meomchwo")
    public String meomchwo(@NotNull @RequestParam String email, @RequestParam Double profit, @RequestParam Long random) {
        if (Objects.isNull(profit) && random.doubleValue() != this.randomNumber) {
            randomNumber = new Random().nextLong();
            return "random=" + randomNumber;
        }
//        AccountResponse accountResponse = upbitAccountService.getAccountByEmail(email);
//        upbitOrderService.sellAssetByAccount(accountResponse, profit);
        return "올 매도";
    }
}
