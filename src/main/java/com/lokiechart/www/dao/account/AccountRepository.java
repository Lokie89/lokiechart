package com.lokiechart.www.dao.account;

import com.lokiechart.www.domain.account.Account;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * @author SeongRok.Oh
 * @since 2021/04/19
 */
@Component
public class AccountRepository {
    private final String[] excludeMarkets = {"BTC", "ETH", "XRP", "ADA", "DOGE", "DOT", "LTC", "BCH", "LINK", "VET", "XLM", "THETA", "TRX"};
//    private final String[] decidedMarkets = {"SC", "MOC", "SOLVE", "STMX", "IQ", "DKA"};

    private final Account tjdfhr = Account.builder()
            .email("tjdfhrdk10@naver.com")
            .excludeMarket(Arrays.asList(excludeMarkets))
            .maxBuyMarket(20)
            .totalSeed(6000000)
            .totalTradeCount(2)
            .buyFlag(true)
            .sellFlag(true)
//            .decidedMarket(Arrays.asList(decidedMarkets))
            .build()
            ;

    private final Account tjdals = Account.builder()
            .email("01099589323@hanmail.net")
            .excludeMarket(Arrays.asList(excludeMarkets))
            .totalSeed(1600000)
            .maxBuyMarket(20)
            .totalTradeCount(2)
            .buyFlag(true)
            .sellFlag(true)
//            .decidedMarket(Arrays.asList(decidedMarkets))
            .build()
            ;

    private final Map<String, Account> accountMap = new HashMap<>();

    public AccountRepository() {
        accountMap.put(tjdfhr.getEmail(), tjdfhr);
        accountMap.put(tjdals.getEmail(), tjdals);
    }


    public Account findByEmail(final String email) {
        return accountMap.get(email);
    }

    public List<Account> findAll() {
        return new ArrayList<>(accountMap.values());
    }
}
