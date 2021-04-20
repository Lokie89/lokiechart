package com.lokiechart.www.dao.candle;

import com.lokiechart.www.common.ConvertType;
import com.lokiechart.www.common.SynchronizedNonOverlapList;
import com.lokiechart.www.dao.candle.dto.CandleResponse;
import com.lokiechart.www.dao.candle.dto.UpbitMinuteCandleResponse;
import com.lokiechart.www.dao.tunnel.CallByApi;
import org.springframework.stereotype.Component;

/**
 * @author SeongRok.Oh
 * @since 2021/04/19
 */

@Component
public class UpbitMinuteCandleRepository extends UpbitCandleRepository {
    private final ConvertType convertType;

    public UpbitMinuteCandleRepository(CallByApi api, ConvertType convertType) {
        super(api);
        this.convertType = convertType;
    }

    @Override
    protected SynchronizedNonOverlapList<CandleResponse> getCandlesByTime(String response) {
        UpbitMinuteCandleResponse[] upbitMinuteCandleResponses = convertType.stringToType(response, UpbitMinuteCandleResponse[].class);
        return new SynchronizedNonOverlapList<>(upbitMinuteCandleResponses);
    }
}
