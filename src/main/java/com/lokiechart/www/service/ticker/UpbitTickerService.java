package com.lokiechart.www.service.ticker;

import com.lokiechart.www.dao.ticker.TickerRepository;
import com.lokiechart.www.dao.ticker.dto.TickerResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author SeongRok.Oh
 * @since 2021/05/01
 */
@RequiredArgsConstructor
@Service
public class UpbitTickerService implements TickerService {
    private final TickerRepository upbitTickerRepository;

    @Override
    public TickerResponses getTickersByMarkets(String markets){
        return upbitTickerRepository.getTicker(markets);
    }
}
