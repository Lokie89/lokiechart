package com.lokiechart.www.common;

import com.lokiechart.www.dao.candle.dto.CandleResponses;
import com.lokiechart.www.dao.candle.dto.UpbitMinuteCandleResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;

/**
 * @author SeongRok.Oh
 * @since 2021/04/24
 */
@DisplayName("조건 검사 테스트")
@SpringBootTest
public class TradeStrategyTest {

    @Autowired
    ConvertType convertType;

    @Test
    void test() {
        String bollingerBandsTestStr = "[" +
                "  {" +
                "    \"market\": \"KRW-BTC\"," +
                "    \"candle_date_time_utc\": \"2021-04-24T10:06:00\"," +
                "    \"candle_date_time_kst\": \"2021-04-24T19:06:00\"," +
                "    \"opening_price\": 58838000.00000000," +
                "    \"high_price\": 58838000.00000000," +
                "    \"low_price\": 58793000.00000000," +
                "    \"trade_price\": 58798000.00000000," +
                "    \"timestamp\": 1619258820465," +
                "    \"candle_acc_trade_price\": 1644822846.42148000," +
                "    \"candle_acc_trade_volume\": 27.97177763," +
                "    \"unit\": 1" +
                "  }," +
                "  {" +
                "    \"market\": \"KRW-BTC\"," +
                "    \"candle_date_time_utc\": \"2021-04-24T10:05:00\"," +
                "    \"candle_date_time_kst\": \"2021-04-24T19:05:00\"," +
                "    \"opening_price\": 58982000.00000000," +
                "    \"high_price\": 59000000.00000000," +
                "    \"low_price\": 58837000.00000000," +
                "    \"trade_price\": 58837000.00000000," +
                "    \"timestamp\": 1619258760447," +
                "    \"candle_acc_trade_price\": 1015335950.00186000," +
                "    \"candle_acc_trade_volume\": 17.23449953," +
                "    \"unit\": 1" +
                "  }," +
                "  {" +
                "    \"market\": \"KRW-BTC\"," +
                "    \"candle_date_time_utc\": \"2021-04-24T10:04:00\"," +
                "    \"candle_date_time_kst\": \"2021-04-24T19:04:00\"," +
                "    \"opening_price\": 59041000.00000000," +
                "    \"high_price\": 59046000.00000000," +
                "    \"low_price\": 58970000.00000000," +
                "    \"trade_price\": 59000000.00000000," +
                "    \"timestamp\": 1619258702071," +
                "    \"candle_acc_trade_price\": 1583465959.44849000," +
                "    \"candle_acc_trade_volume\": 26.83667356," +
                "    \"unit\": 1" +
                "  }," +
                "  {" +
                "    \"market\": \"KRW-BTC\"," +
                "    \"candle_date_time_utc\": \"2021-04-24T10:03:00\"," +
                "    \"candle_date_time_kst\": \"2021-04-24T19:03:00\"," +
                "    \"opening_price\": 59120000.00000000," +
                "    \"high_price\": 59120000.00000000," +
                "    \"low_price\": 59041000.00000000," +
                "    \"trade_price\": 59041000.00000000," +
                "    \"timestamp\": 1619258640553," +
                "    \"candle_acc_trade_price\": 734022218.83634000," +
                "    \"candle_acc_trade_volume\": 12.42230009," +
                "    \"unit\": 1" +
                "  }," +
                "  {" +
                "    \"market\": \"KRW-BTC\"," +
                "    \"candle_date_time_utc\": \"2021-04-24T10:02:00\"," +
                "    \"candle_date_time_kst\": \"2021-04-24T19:02:00\"," +
                "    \"opening_price\": 59140000.00000000," +
                "    \"high_price\": 59170000.00000000," +
                "    \"low_price\": 59120000.00000000," +
                "    \"trade_price\": 59120000.00000000," +
                "    \"timestamp\": 1619258580179," +
                "    \"candle_acc_trade_price\": 354881956.68391000," +
                "    \"candle_acc_trade_volume\": 6.00091407," +
                "    \"unit\": 1" +
                "  }," +
                "  {" +
                "    \"market\": \"KRW-BTC\"," +
                "    \"candle_date_time_utc\": \"2021-04-24T10:01:00\"," +
                "    \"candle_date_time_kst\": \"2021-04-24T19:01:00\"," +
                "    \"opening_price\": 59210000.00000000," +
                "    \"high_price\": 59228000.00000000," +
                "    \"low_price\": 59130000.00000000," +
                "    \"trade_price\": 59158000.00000000," +
                "    \"timestamp\": 1619258520154," +
                "    \"candle_acc_trade_price\": 671884204.99176000," +
                "    \"candle_acc_trade_volume\": 11.35600612," +
                "    \"unit\": 1" +
                "  }," +
                "  {" +
                "    \"market\": \"KRW-BTC\"," +
                "    \"candle_date_time_utc\": \"2021-04-24T10:00:00\"," +
                "    \"candle_date_time_kst\": \"2021-04-24T19:00:00\"," +
                "    \"opening_price\": 59283000.00000000," +
                "    \"high_price\": 59283000.00000000," +
                "    \"low_price\": 59186000.00000000," +
                "    \"trade_price\": 59200000.00000000," +
                "    \"timestamp\": 1619258460235," +
                "    \"candle_acc_trade_price\": 670124730.80439000," +
                "    \"candle_acc_trade_volume\": 11.31174277," +
                "    \"unit\": 1" +
                "  }," +
                "  {" +
                "    \"market\": \"KRW-BTC\"," +
                "    \"candle_date_time_utc\": \"2021-04-24T09:59:00\"," +
                "    \"candle_date_time_kst\": \"2021-04-24T18:59:00\"," +
                "    \"opening_price\": 59316000.00000000," +
                "    \"high_price\": 59320000.00000000," +
                "    \"low_price\": 59252000.00000000," +
                "    \"trade_price\": 59271000.00000000," +
                "    \"timestamp\": 1619258399910," +
                "    \"candle_acc_trade_price\": 487152664.46287000," +
                "    \"candle_acc_trade_volume\": 8.21491855," +
                "    \"unit\": 1" +
                "  }," +
                "  {" +
                "    \"market\": \"KRW-BTC\"," +
                "    \"candle_date_time_utc\": \"2021-04-24T09:58:00\"," +
                "    \"candle_date_time_kst\": \"2021-04-24T18:58:00\"," +
                "    \"opening_price\": 59335000.00000000," +
                "    \"high_price\": 59335000.00000000," +
                "    \"low_price\": 59310000.00000000," +
                "    \"trade_price\": 59316000.00000000," +
                "    \"timestamp\": 1619258339619," +
                "    \"candle_acc_trade_price\": 460842926.29298000," +
                "    \"candle_acc_trade_volume\": 7.76819837," +
                "    \"unit\": 1" +
                "  }," +
                "  {" +
                "    \"market\": \"KRW-BTC\"," +
                "    \"candle_date_time_utc\": \"2021-04-24T09:57:00\"," +
                "    \"candle_date_time_kst\": \"2021-04-24T18:57:00\"," +
                "    \"opening_price\": 59334000.00000000," +
                "    \"high_price\": 59335000.00000000," +
                "    \"low_price\": 59330000.00000000," +
                "    \"trade_price\": 59333000.00000000," +
                "    \"timestamp\": 1619258279892," +
                "    \"candle_acc_trade_price\": 496222833.57727000," +
                "    \"candle_acc_trade_volume\": 8.36337230," +
                "    \"unit\": 1" +
                "  }," +
                "  {" +
                "    \"market\": \"KRW-BTC\"," +
                "    \"candle_date_time_utc\": \"2021-04-24T09:56:00\"," +
                "    \"candle_date_time_kst\": \"2021-04-24T18:56:00\"," +
                "    \"opening_price\": 59350000.00000000," +
                "    \"high_price\": 59361000.00000000," +
                "    \"low_price\": 59333000.00000000," +
                "    \"trade_price\": 59335000.00000000," +
                "    \"timestamp\": 1619258220105," +
                "    \"candle_acc_trade_price\": 521929318.47957000," +
                "    \"candle_acc_trade_volume\": 8.79491299," +
                "    \"unit\": 1" +
                "  }," +
                "  {" +
                "    \"market\": \"KRW-BTC\"," +
                "    \"candle_date_time_utc\": \"2021-04-24T09:55:00\"," +
                "    \"candle_date_time_kst\": \"2021-04-24T18:55:00\"," +
                "    \"opening_price\": 59413000.00000000," +
                "    \"high_price\": 59413000.00000000," +
                "    \"low_price\": 59348000.00000000," +
                "    \"trade_price\": 59350000.00000000," +
                "    \"timestamp\": 1619258159977," +
                "    \"candle_acc_trade_price\": 387833194.89642000," +
                "    \"candle_acc_trade_volume\": 6.53313076," +
                "    \"unit\": 1" +
                "  }," +
                "  {" +
                "    \"market\": \"KRW-BTC\"," +
                "    \"candle_date_time_utc\": \"2021-04-24T09:54:00\"," +
                "    \"candle_date_time_kst\": \"2021-04-24T18:54:00\"," +
                "    \"opening_price\": 59457000.00000000," +
                "    \"high_price\": 59457000.00000000," +
                "    \"low_price\": 59410000.00000000," +
                "    \"trade_price\": 59412000.00000000," +
                "    \"timestamp\": 1619258100436," +
                "    \"candle_acc_trade_price\": 272305679.52638000," +
                "    \"candle_acc_trade_volume\": 4.58142079," +
                "    \"unit\": 1" +
                "  }," +
                "  {" +
                "    \"market\": \"KRW-BTC\"," +
                "    \"candle_date_time_utc\": \"2021-04-24T09:53:00\"," +
                "    \"candle_date_time_kst\": \"2021-04-24T18:53:00\"," +
                "    \"opening_price\": 59480000.00000000," +
                "    \"high_price\": 59527000.00000000," +
                "    \"low_price\": 59452000.00000000," +
                "    \"trade_price\": 59480000.00000000," +
                "    \"timestamp\": 1619258040066," +
                "    \"candle_acc_trade_price\": 337694614.67277000," +
                "    \"candle_acc_trade_volume\": 5.67776231," +
                "    \"unit\": 1" +
                "  }," +
                "  {" +
                "    \"market\": \"KRW-BTC\"," +
                "    \"candle_date_time_utc\": \"2021-04-24T09:52:00\"," +
                "    \"candle_date_time_kst\": \"2021-04-24T18:52:00\"," +
                "    \"opening_price\": 59519000.00000000," +
                "    \"high_price\": 59519000.00000000," +
                "    \"low_price\": 59480000.00000000," +
                "    \"trade_price\": 59481000.00000000," +
                "    \"timestamp\": 1619257980226," +
                "    \"candle_acc_trade_price\": 277882184.62220000," +
                "    \"candle_acc_trade_volume\": 4.67058185," +
                "    \"unit\": 1" +
                "  }," +
                "  {" +
                "    \"market\": \"KRW-BTC\"," +
                "    \"candle_date_time_utc\": \"2021-04-24T09:51:00\"," +
                "    \"candle_date_time_kst\": \"2021-04-24T18:51:00\"," +
                "    \"opening_price\": 59524000.00000000," +
                "    \"high_price\": 59529000.00000000," +
                "    \"low_price\": 59510000.00000000," +
                "    \"trade_price\": 59518000.00000000," +
                "    \"timestamp\": 1619257918684," +
                "    \"candle_acc_trade_price\": 101057251.70233000," +
                "    \"candle_acc_trade_volume\": 1.69775886," +
                "    \"unit\": 1" +
                "  }," +
                "  {" +
                "    \"market\": \"KRW-BTC\"," +
                "    \"candle_date_time_utc\": \"2021-04-24T09:50:00\"," +
                "    \"candle_date_time_kst\": \"2021-04-24T18:50:00\"," +
                "    \"opening_price\": 59551000.00000000," +
                "    \"high_price\": 59560000.00000000," +
                "    \"low_price\": 59520000.00000000," +
                "    \"trade_price\": 59524000.00000000," +
                "    \"timestamp\": 1619257860086," +
                "    \"candle_acc_trade_price\": 97671638.48728000," +
                "    \"candle_acc_trade_volume\": 1.64035249," +
                "    \"unit\": 1" +
                "  }," +
                "  {" +
                "    \"market\": \"KRW-BTC\"," +
                "    \"candle_date_time_utc\": \"2021-04-24T09:49:00\"," +
                "    \"candle_date_time_kst\": \"2021-04-24T18:49:00\"," +
                "    \"opening_price\": 59553000.00000000," +
                "    \"high_price\": 59560000.00000000," +
                "    \"low_price\": 59549000.00000000," +
                "    \"trade_price\": 59550000.00000000," +
                "    \"timestamp\": 1619257799880," +
                "    \"candle_acc_trade_price\": 99591720.24372000," +
                "    \"candle_acc_trade_volume\": 1.67238065," +
                "    \"unit\": 1" +
                "  }," +
                "  {" +
                "    \"market\": \"KRW-BTC\"," +
                "    \"candle_date_time_utc\": \"2021-04-24T09:48:00\"," +
                "    \"candle_date_time_kst\": \"2021-04-24T18:48:00\"," +
                "    \"opening_price\": 59529000.00000000," +
                "    \"high_price\": 59560000.00000000," +
                "    \"low_price\": 59529000.00000000," +
                "    \"trade_price\": 59553000.00000000," +
                "    \"timestamp\": 1619257738432," +
                "    \"candle_acc_trade_price\": 64028621.29344000," +
                "    \"candle_acc_trade_volume\": 1.07528259," +
                "    \"unit\": 1" +
                "  }," +
                "  {" +
                "    \"market\": \"KRW-BTC\"," +
                "    \"candle_date_time_utc\": \"2021-04-24T09:47:00\"," +
                "    \"candle_date_time_kst\": \"2021-04-24T18:47:00\"," +
                "    \"opening_price\": 59532000.00000000," +
                "    \"high_price\": 59560000.00000000," +
                "    \"low_price\": 59510000.00000000," +
                "    \"trade_price\": 59530000.00000000," +
                "    \"timestamp\": 1619257680187," +
                "    \"candle_acc_trade_price\": 242065501.31944000," +
                "    \"candle_acc_trade_volume\": 4.06608025," +
                "    \"unit\": 1" +
                "  }," +
                "  {" +
                "    \"market\": \"KRW-BTC\"," +
                "    \"candle_date_time_utc\": \"2021-04-24T09:46:00\"," +
                "    \"candle_date_time_kst\": \"2021-04-24T18:46:00\"," +
                "    \"opening_price\": 59508000.00000000," +
                "    \"high_price\": 59553000.00000000," +
                "    \"low_price\": 59503000.00000000," +
                "    \"trade_price\": 59541000.00000000," +
                "    \"timestamp\": 1619257619842," +
                "    \"candle_acc_trade_price\": 214619965.16638000," +
                "    \"candle_acc_trade_volume\": 3.60492765," +
                "    \"unit\": 1" +
                "  }," +
                "  {" +
                "    \"market\": \"KRW-BTC\"," +
                "    \"candle_date_time_utc\": \"2021-04-24T09:45:00\"," +
                "    \"candle_date_time_kst\": \"2021-04-24T18:45:00\"," +
                "    \"opening_price\": 59498000.00000000," +
                "    \"high_price\": 59511000.00000000," +
                "    \"low_price\": 59490000.00000000," +
                "    \"trade_price\": 59509000.00000000," +
                "    \"timestamp\": 1619257560085," +
                "    \"candle_acc_trade_price\": 140147867.50106000," +
                "    \"candle_acc_trade_volume\": 2.35550193," +
                "    \"unit\": 1" +
                "  }," +
                "  {" +
                "    \"market\": \"KRW-BTC\"," +
                "    \"candle_date_time_utc\": \"2021-04-24T09:44:00\"," +
                "    \"candle_date_time_kst\": \"2021-04-24T18:44:00\"," +
                "    \"opening_price\": 59520000.00000000," +
                "    \"high_price\": 59521000.00000000," +
                "    \"low_price\": 59493000.00000000," +
                "    \"trade_price\": 59501000.00000000," +
                "    \"timestamp\": 1619257500183," +
                "    \"candle_acc_trade_price\": 222953445.96864000," +
                "    \"candle_acc_trade_volume\": 3.74645204," +
                "    \"unit\": 1" +
                "  }," +
                "  {" +
                "    \"market\": \"KRW-BTC\"," +
                "    \"candle_date_time_utc\": \"2021-04-24T09:43:00\"," +
                "    \"candle_date_time_kst\": \"2021-04-24T18:43:00\"," +
                "    \"opening_price\": 59544000.00000000," +
                "    \"high_price\": 59554000.00000000," +
                "    \"low_price\": 59516000.00000000," +
                "    \"trade_price\": 59517000.00000000," +
                "    \"timestamp\": 1619257437856," +
                "    \"candle_acc_trade_price\": 149326032.51175000," +
                "    \"candle_acc_trade_volume\": 2.50872773," +
                "    \"unit\": 1" +
                "  }," +
                "  {" +
                "    \"market\": \"KRW-BTC\"," +
                "    \"candle_date_time_utc\": \"2021-04-24T09:42:00\"," +
                "    \"candle_date_time_kst\": \"2021-04-24T18:42:00\"," +
                "    \"opening_price\": 59593000.00000000," +
                "    \"high_price\": 59593000.00000000," +
                "    \"low_price\": 59510000.00000000," +
                "    \"trade_price\": 59554000.00000000," +
                "    \"timestamp\": 1619257380171," +
                "    \"candle_acc_trade_price\": 110634490.25676000," +
                "    \"candle_acc_trade_volume\": 1.85776015," +
                "    \"unit\": 1" +
                "  }," +
                "  {" +
                "    \"market\": \"KRW-BTC\"," +
                "    \"candle_date_time_utc\": \"2021-04-24T09:41:00\"," +
                "    \"candle_date_time_kst\": \"2021-04-24T18:41:00\"," +
                "    \"opening_price\": 59545000.00000000," +
                "    \"high_price\": 59593000.00000000," +
                "    \"low_price\": 59544000.00000000," +
                "    \"trade_price\": 59593000.00000000," +
                "    \"timestamp\": 1619257320176," +
                "    \"candle_acc_trade_price\": 96407924.17516000," +
                "    \"candle_acc_trade_volume\": 1.61860753," +
                "    \"unit\": 1" +
                "  }," +
                "  {" +
                "    \"market\": \"KRW-BTC\"," +
                "    \"candle_date_time_utc\": \"2021-04-24T09:40:00\"," +
                "    \"candle_date_time_kst\": \"2021-04-24T18:40:00\"," +
                "    \"opening_price\": 59662000.00000000," +
                "    \"high_price\": 59718000.00000000," +
                "    \"low_price\": 59557000.00000000," +
                "    \"trade_price\": 59561000.00000000," +
                "    \"timestamp\": 1619257256561," +
                "    \"candle_acc_trade_price\": 157974408.27908000," +
                "    \"candle_acc_trade_volume\": 2.64857446," +
                "    \"unit\": 1" +
                "  }," +
                "  {" +
                "    \"market\": \"KRW-BTC\"," +
                "    \"candle_date_time_utc\": \"2021-04-24T09:39:00\"," +
                "    \"candle_date_time_kst\": \"2021-04-24T18:39:00\"," +
                "    \"opening_price\": 59591000.00000000," +
                "    \"high_price\": 59730000.00000000," +
                "    \"low_price\": 59591000.00000000," +
                "    \"trade_price\": 59662000.00000000," +
                "    \"timestamp\": 1619257200238," +
                "    \"candle_acc_trade_price\": 279115835.58157000," +
                "    \"candle_acc_trade_volume\": 4.67697517," +
                "    \"unit\": 1" +
                "  }," +
                "  {" +
                "    \"market\": \"KRW-BTC\"," +
                "    \"candle_date_time_utc\": \"2021-04-24T09:38:00\"," +
                "    \"candle_date_time_kst\": \"2021-04-24T18:38:00\"," +
                "    \"opening_price\": 59541000.00000000," +
                "    \"high_price\": 59600000.00000000," +
                "    \"low_price\": 59513000.00000000," +
                "    \"trade_price\": 59591000.00000000," +
                "    \"timestamp\": 1619257140007," +
                "    \"candle_acc_trade_price\": 669141790.76645000," +
                "    \"candle_acc_trade_volume\": 11.23914341," +
                "    \"unit\": 1" +
                "  }," +
                "  {" +
                "    \"market\": \"KRW-BTC\"," +
                "    \"candle_date_time_utc\": \"2021-04-24T09:37:00\"," +
                "    \"candle_date_time_kst\": \"2021-04-24T18:37:00\"," +
                "    \"opening_price\": 59544000.00000000," +
                "    \"high_price\": 59544000.00000000," +
                "    \"low_price\": 59520000.00000000," +
                "    \"trade_price\": 59521000.00000000," +
                "    \"timestamp\": 1619257079935," +
                "    \"candle_acc_trade_price\": 148041858.21530000," +
                "    \"candle_acc_trade_volume\": 2.48712976," +
                "    \"unit\": 1" +
                "  }," +
                "  {" +
                "    \"market\": \"KRW-BTC\"," +
                "    \"candle_date_time_utc\": \"2021-04-24T09:36:00\"," +
                "    \"candle_date_time_kst\": \"2021-04-24T18:36:00\"," +
                "    \"opening_price\": 59533000.00000000," +
                "    \"high_price\": 59560000.00000000," +
                "    \"low_price\": 59525000.00000000," +
                "    \"trade_price\": 59525000.00000000," +
                "    \"timestamp\": 1619257020369," +
                "    \"candle_acc_trade_price\": 116252751.32260000," +
                "    \"candle_acc_trade_volume\": 1.95258802," +
                "    \"unit\": 1" +
                "  }," +
                "  {" +
                "    \"market\": \"KRW-BTC\"," +
                "    \"candle_date_time_utc\": \"2021-04-24T09:35:00\"," +
                "    \"candle_date_time_kst\": \"2021-04-24T18:35:00\"," +
                "    \"opening_price\": 59535000.00000000," +
                "    \"high_price\": 59561000.00000000," +
                "    \"low_price\": 59520000.00000000," +
                "    \"trade_price\": 59533000.00000000," +
                "    \"timestamp\": 1619256959636," +
                "    \"candle_acc_trade_price\": 162410037.55388000," +
                "    \"candle_acc_trade_volume\": 2.72834478," +
                "    \"unit\": 1" +
                "  }," +
                "  {" +
                "    \"market\": \"KRW-BTC\"," +
                "    \"candle_date_time_utc\": \"2021-04-24T09:34:00\"," +
                "    \"candle_date_time_kst\": \"2021-04-24T18:34:00\"," +
                "    \"opening_price\": 59544000.00000000," +
                "    \"high_price\": 59550000.00000000," +
                "    \"low_price\": 59535000.00000000," +
                "    \"trade_price\": 59539000.00000000," +
                "    \"timestamp\": 1619256900041," +
                "    \"candle_acc_trade_price\": 141660829.96217000," +
                "    \"candle_acc_trade_volume\": 2.37919466," +
                "    \"unit\": 1" +
                "  }," +
                "  {" +
                "    \"market\": \"KRW-BTC\"," +
                "    \"candle_date_time_utc\": \"2021-04-24T09:33:00\"," +
                "    \"candle_date_time_kst\": \"2021-04-24T18:33:00\"," +
                "    \"opening_price\": 59569000.00000000," +
                "    \"high_price\": 59570000.00000000," +
                "    \"low_price\": 59544000.00000000," +
                "    \"trade_price\": 59544000.00000000," +
                "    \"timestamp\": 1619256839776," +
                "    \"candle_acc_trade_price\": 83463674.24376000," +
                "    \"candle_acc_trade_volume\": 1.40129333," +
                "    \"unit\": 1" +
                "  }," +
                "  {" +
                "    \"market\": \"KRW-BTC\"," +
                "    \"candle_date_time_utc\": \"2021-04-24T09:32:00\"," +
                "    \"candle_date_time_kst\": \"2021-04-24T18:32:00\"," +
                "    \"opening_price\": 59576000.00000000," +
                "    \"high_price\": 59576000.00000000," +
                "    \"low_price\": 59556000.00000000," +
                "    \"trade_price\": 59569000.00000000," +
                "    \"timestamp\": 1619256780043," +
                "    \"candle_acc_trade_price\": 160893491.23867000," +
                "    \"candle_acc_trade_volume\": 2.70103890," +
                "    \"unit\": 1" +
                "  }" +
                "]";
        UpbitMinuteCandleResponse[] upbitMinuteCandleResponses = convertType.stringToType(bollingerBandsTestStr, UpbitMinuteCandleResponse[].class);
        Arrays.sort(upbitMinuteCandleResponses);
        CandleResponses candleResponses = new CandleResponses(new SynchronizedNonOverlapList<>(upbitMinuteCandleResponses));
        candleResponses.setUnderBollingerBands(2);
        System.out.println(candleResponses.getCandleResponses().getRecent(0));
        System.out.println(candleResponses.getCandleResponses().getRecent(0).compareUnderBollingerBands());
        System.out.println(candleResponses.getCandleResponses().getRecent(1));
        System.out.println(candleResponses.getCandleResponses().getRecent(1).compareUnderBollingerBands());

        System.out.println(candleResponses.getCandleResponses().copyRecent(0, 2));
    }
}
