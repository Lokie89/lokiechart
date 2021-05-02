package com.lokiechart.www.service.market;

import com.lokiechart.www.dao.market.MarketRepository;
import com.lokiechart.www.dao.market.dto.MarketResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author SeongRok.Oh
 * @since 2021/05/01
 */
@RequiredArgsConstructor
@Service
public class UpbitMarketService implements MarketService {

    private final MarketRepository upbitMarketRepository;

    public MarketResponses getAll() {
        return new MarketResponses(upbitMarketRepository.getMarkets());
    }
}
