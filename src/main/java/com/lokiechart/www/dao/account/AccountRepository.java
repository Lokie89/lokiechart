package com.lokiechart.www.dao.account;

import com.lokiechart.www.common.TradeStrategy;
import com.lokiechart.www.domain.account.Account;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author SeongRok.Oh
 * @since 2021/04/19
 */
@Component
public class AccountRepository {
    public Account findByEmail(String email) {
        return new Account("tjdfhrdk10@naver.com", TradeStrategy.KEEP_TRADEPRICE_UNDERBOLLINGERBANDSTWICE, 5100);
    }

    public List<Account> findByTradeStrategy(TradeStrategy strategy) {
        List<Account> accounts = new ArrayList<>();
        accounts.add(new Account("tjdfhrdk10@naver.com", TradeStrategy.KEEP_TRADEPRICE_UNDERBOLLINGERBANDSTWICE, 5100));
        return accounts;
    }
}
