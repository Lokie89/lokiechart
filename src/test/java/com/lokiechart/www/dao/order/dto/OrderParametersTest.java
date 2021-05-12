package com.lokiechart.www.dao.order.dto;

import com.lokiechart.www.dao.asset.dto.AssetResponse;
import com.lokiechart.www.dao.asset.dto.AssetResponses;
import com.lokiechart.www.dao.asset.dto.UpbitAssetResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author SeongRok.Oh
 * @since 2021/05/06
 */
@DisplayName("주문 파라미터 리스트 테스트")
class OrderParametersTest {

    @DisplayName("자신이 가진 파라미터 놔두고 앞에서 원하는 개수만큼 필터링")
    @Test
    void filterAlreadyOwnAndAddCountTest(){
        List<AssetResponse> assetResponseList = new ArrayList<>();
        assetResponseList.add(UpbitAssetResponse.builder().currency("SC").unitCurrency("KRW").build());
        assetResponseList.add(UpbitAssetResponse.builder().currency("BTC").unitCurrency("KRW").build());
        assetResponseList.add(UpbitAssetResponse.builder().currency("ENJ").unitCurrency("KRW").build());
        AssetResponses assetResponses = new AssetResponses(assetResponseList);


        List<OrderParameter> orderParameterList = new ArrayList<>();
        orderParameterList.add(UpbitOrderParameter.builder().orderType(OrderType.LIMIT).side(OrderSide.BUY).volume(1.0).price(5001.0).market("KRW-BTC").build());
        orderParameterList.add(UpbitOrderParameter.builder().orderType(OrderType.LIMIT).side(OrderSide.BUY).volume(1.0).price(5001.0).market("KRW-SC").build());
        orderParameterList.add(UpbitOrderParameter.builder().orderType(OrderType.LIMIT).side(OrderSide.BUY).volume(1.0).price(5001.0).market("KRW-XTZ").build());
        orderParameterList.add(UpbitOrderParameter.builder().orderType(OrderType.LIMIT).side(OrderSide.BUY).volume(1.0).price(5001.0).market("KRW-MTL").build());
        orderParameterList.add(UpbitOrderParameter.builder().orderType(OrderType.LIMIT).side(OrderSide.BUY).volume(1.0).price(5001.0).market("KRW-SOLVE").build());
        OrderParameters orderParameters = new OrderParameters(orderParameterList);


        orderParameters.dropAlreadyOwnAndAddCount(assetResponses,2);

        System.out.println(orderParameters.size());
    }

    @DisplayName("교차 파라미터 필터링 테스트")
    @Test
    void intersectTest(){
        List<OrderParameter> orderParameterList = new ArrayList<>();
        OrderParameters orderParameters1 = new OrderParameters(orderParameterList);

        orderParameterList.add(UpbitOrderParameter.builder().orderType(OrderType.LIMIT).side(OrderSide.BUY).volume(1.0).price(5001.0).market("KRW-BTC").build());
        orderParameterList.add(UpbitOrderParameter.builder().orderType(OrderType.LIMIT).side(OrderSide.BUY).volume(1.0).price(5001.0).market("KRW-XTZ").build());
        orderParameterList.add(UpbitOrderParameter.builder().orderType(OrderType.LIMIT).side(OrderSide.BUY).volume(1.0).price(5001.0).market("KRW-MTL").build());
        orderParameterList.add(UpbitOrderParameter.builder().orderType(OrderType.LIMIT).side(OrderSide.BUY).volume(1.0).price(5001.0).market("KRW-SOLVE").build());
        OrderParameters orderParameters = new OrderParameters(orderParameterList);

        List<OrderParameter> orderParameterList2 = new ArrayList<>();
        orderParameterList2.add(UpbitOrderParameter.builder().orderType(OrderType.LIMIT).side(OrderSide.BUY).volume(1.0).price(5001.0).market("KRW-BTC").build());
        orderParameterList2.add(UpbitOrderParameter.builder().orderType(OrderType.LIMIT).side(OrderSide.BUY).volume(1.0).price(5001.0).market("KRW-SC").build());
        orderParameterList2.add(UpbitOrderParameter.builder().orderType(OrderType.LIMIT).side(OrderSide.BUY).volume(1.0).price(5001.0).market("KRW-SOLVE").build());
        OrderParameters orderParameters2 = new OrderParameters(orderParameterList2);

        orderParameters1.intersect(orderParameters);
        orderParameters1.intersect(orderParameters2);

        System.out.println(orderParameters1);

    }
}
