package com.lokiechart.www.dao.candle;

import com.lokiechart.www.dao.candle.dto.UpbitMinuteCandleParameter;
import com.lokiechart.www.dao.tunnel.CallByApi;
import org.springframework.stereotype.Component;

/**
 * @author SeongRok.Oh
 * @since 2021/04/18
 */
@Component
public class UpbitMinuteCandleRepository extends UpbitCandleRepository {
    public UpbitMinuteCandleRepository(CallByApi api) {
        super(api);
    }

    @Override
    protected String getUrl(UpbitMinuteCandleParameter parameter) {
        String url = "https://api.upbit.com/v1/candles/minutes/";
        url += parameter.getCandleMinute().getNumber() + "?market=" + parameter.getMarket() + "&count=" + parameter.getCount();
        return url;
    }
}
