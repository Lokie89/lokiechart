package com.lokiechart.www.service.order;

import com.lokiechart.www.batch.CandleMinute;
import com.lokiechart.www.dao.account.dto.AccountResponse;
import com.lokiechart.www.dao.order.UpbitOrderRepository;
import com.lokiechart.www.service.account.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author SeongRok.Oh
 * @since 2021/04/19
 */
@RequiredArgsConstructor
@Service
public class UpbitOrderService implements OrderService {
    private final UpbitOrderRepository upbitOrderRepository;
    private final AccountService accountService;

    @Override
    public void tradeByAccount(String email, final CandleMinute candleMinute) {
        AccountResponse accountResponse = accountService.getAccountByEmail(email);
        upbitOrderRepository.orderByStrategy(accountResponse, candleMinute);
    }
}
