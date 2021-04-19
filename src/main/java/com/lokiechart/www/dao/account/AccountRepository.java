package com.lokiechart.www.dao.account;

import com.lokiechart.www.common.TradeStrategy;
import com.lokiechart.www.domain.account.Account;
import org.springframework.stereotype.Component;

/**
 * @author SeongRok.Oh
 * @since 2021/04/19
 */
@Component
public class AccountRepository {
    public Account findByEmail(String email) {
        return new Account("tjdfhrdk10@naver.com", TradeStrategy.TEST);
    }
}
