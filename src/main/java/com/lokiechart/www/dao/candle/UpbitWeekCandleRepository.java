package com.lokiechart.www.dao.candle;

import com.lokiechart.www.common.ConvertType;
import com.lokiechart.www.common.SynchronizedNonOverlapList;
import com.lokiechart.www.dao.candle.dto.CandleResponse;
import com.lokiechart.www.dao.candle.dto.UpbitWeekCandleResponse;
import com.lokiechart.www.dao.tunnel.CallByApi;
import org.springframework.stereotype.Component;

/**
 * @author SeongRok.Oh
 * @since 2021/04/19
 */

@Component
public class UpbitWeekCandleRepository extends UpbitCandleRepository {
    private final ConvertType convertType;

    public UpbitWeekCandleRepository(CallByApi api, ConvertType convertType) {
        super(api);
        this.convertType = convertType;
    }

    @Override
    protected SynchronizedNonOverlapList<CandleResponse> getCandlesByTime(String response) {
        UpbitWeekCandleResponse[] upbitWeekCandleResponses = convertType.stringToType(response, UpbitWeekCandleResponse[].class);
        return new SynchronizedNonOverlapList<>(upbitWeekCandleResponses);
    }
}
