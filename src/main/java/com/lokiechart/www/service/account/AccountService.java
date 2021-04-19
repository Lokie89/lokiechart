package com.lokiechart.www.service.account;

import com.lokiechart.www.dao.account.AccountRepository;
import com.lokiechart.www.dao.account.dto.AccountResponse;
import com.lokiechart.www.domain.account.Account;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

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
}
