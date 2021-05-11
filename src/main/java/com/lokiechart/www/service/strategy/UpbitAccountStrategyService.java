package com.lokiechart.www.service.strategy;

import com.lokiechart.www.dao.strategy.UpbitAccountStrategyRepository;
import com.lokiechart.www.domain.strategy.AccountStrategy;
import com.lokiechart.www.service.strategy.dto.AccountStrategyResponse;
import com.lokiechart.www.service.strategy.dto.AccountStrategyResponses;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * @author SeongRok.Oh
 * @since 2021/05/11
 */
@RequiredArgsConstructor
@Service
public class UpbitAccountStrategyService implements AccountStrategyService {
    private final ModelMapper modelMapper;
    private final UpbitAccountStrategyRepository accountStrategyRepository;

    @Override
    public AccountStrategyResponses getAll() {
        List<AccountStrategy> accountStrategyList = accountStrategyRepository.getAll();
        AccountStrategyResponse[] accountStrategyResponses = modelMapper.map(accountStrategyList, AccountStrategyResponse[].class);
        return new AccountStrategyResponses(Arrays.asList(accountStrategyResponses));
    }
}
