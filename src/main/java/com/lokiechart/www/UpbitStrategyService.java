package com.lokiechart.www;

import com.lokiechart.www.service.strategy.StrategyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author SeongRok.Oh
 * @since 2021/04/19
 */
@RequiredArgsConstructor
@Service
public class UpbitStrategyService implements StrategyService {
    @Override
    public boolean enforceByAccount(String account) {
        return false;
    }
}
