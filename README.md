### MVC
1. Service 에서는 데이터 저장, 검색 및 조건 필터링
2. Controller Req, Resp View
3. Dao API ( 외부 ) 요청 또는 DB 사용

### DOMAIN
- Market : 정해진 기간 마다 요청하여 업데이트 함
- Candle : 분봉별 시간마다 업데이트 함
- Account : 전략 포함 하여 저장
- Strategy : 해당 조건이 맞는지 확인
- Order : 계정에서 금액 조회하여 주문
### STATIC
1. Strategy

### 순서
1. 코인목록
    - 거래되는 코인 목록 가져와
2. 차트 가져오기
    - 분봉 별 코인 마다 차트 가져오기
3. 계정 ( 전략 )
    - 저장된 계정 매수 매도 전략 가져오기
4. 조건 검사
    - 조건 타입 넣고 검사 확인
5. 주문
    - 주문


