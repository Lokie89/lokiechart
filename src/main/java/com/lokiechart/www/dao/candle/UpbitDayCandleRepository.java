package com.lokiechart.www.dao.candle;

import com.lokiechart.www.common.ConvertType;
import com.lokiechart.www.common.SynchronizedNonOverlapList;
import com.lokiechart.www.dao.candle.dto.CandleResponse;
import com.lokiechart.www.dao.candle.dto.UpbitDayCandleResponse;
import com.lokiechart.www.dao.tunnel.CallByApi;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * @author SeongRok.Oh
 * @since 2021/04/19
 */

@Component
public class UpbitDayCandleRepository extends UpbitCandleRepository {
    private final ConvertType convertType;

    public UpbitDayCandleRepository(CallByApi api, ConvertType convertType) {
        super(api);
        this.convertType = convertType;
    }

    @Override
    protected SynchronizedNonOverlapList<CandleResponse> getCandlesByTime(String response) {
        UpbitDayCandleResponse[] upbitDayCandleResponses = convertType.stringToType(response, UpbitDayCandleResponse[].class);
        return new SynchronizedNonOverlapList<>(upbitDayCandleResponses);
    }
}
