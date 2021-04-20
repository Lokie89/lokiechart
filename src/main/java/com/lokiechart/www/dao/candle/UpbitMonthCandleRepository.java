package com.lokiechart.www.dao.candle;

import com.lokiechart.www.common.ConvertType;
import com.lokiechart.www.common.SynchronizedNonOverlapList;
import com.lokiechart.www.dao.candle.dto.CandleResponse;
import com.lokiechart.www.dao.candle.dto.UpbitMonthCandleResponse;
import com.lokiechart.www.dao.tunnel.CallByApi;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

/**
 * @author SeongRok.Oh
 * @since 2021/04/19
 */

@Component
public class UpbitMonthCandleRepository extends UpbitCandleRepository {
    private final ConvertType convertType;

    public UpbitMonthCandleRepository(CallByApi api, ConvertType convertType) {
        super(api);
        this.convertType = convertType;
    }

    @Override
    protected SynchronizedNonOverlapList<CandleResponse> getCandlesByTime(String response) {
        UpbitMonthCandleResponse[] upbitMonthCandleResponses = convertType.stringToType(response, UpbitMonthCandleResponse[].class);
        return new SynchronizedNonOverlapList<>(upbitMonthCandleResponses);
    }
}
