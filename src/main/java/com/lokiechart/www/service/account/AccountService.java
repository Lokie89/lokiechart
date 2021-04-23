package com.lokiechart.www.service.account;

import com.lokiechart.www.batch.TradeStrategy;
import com.lokiechart.www.dao.account.AccountRepository;
import com.lokiechart.www.dao.account.dto.AccountResponse;
import com.lokiechart.www.domain.account.Account;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author SeongRok.Oh
 * @since 2021/04/19
 */
@RequiredArgsConstructor
@Service
public class AccountService {
    private final AccountRepository repository;
    private final ModelMapper modelMapper;

    public AccountResponse getAccountByEmail(String email) {
        Account account = repository.findByEmail(email);
        return modelMapper.map(account, AccountResponse.class);
    }

    public List<AccountResponse> getAccountsByStrategy(TradeStrategy strategy) {
        List<Account> accounts = repository.findByTradeStrategy(strategy);
        return accounts.stream().map(account -> modelMapper.map(account, AccountResponse.class)).collect(Collectors.toList());
    }
}
