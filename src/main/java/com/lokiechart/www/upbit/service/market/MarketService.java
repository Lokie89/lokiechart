package com.lokiechart.www.upbit.service.market;

import com.lokiechart.www.upbit.dao.market.MarketRepository;
import com.lokiechart.www.upbit.dao.market.dto.CoinResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

/**
 * @author SeongRok.Oh
 * @since 2021/04/14
 */
@RequiredArgsConstructor
@Service
public class MarketService {
    private final MarketRepository repository;

    public List<CoinResponse> getAll() {
        List<CoinResponse> bo = null;
        try {
            bo = repository.getCoinList();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bo;
    }
}
