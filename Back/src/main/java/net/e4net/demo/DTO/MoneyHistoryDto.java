package net.e4net.demo.DTO;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import net.e4net.demo.Entity.*;

import java.sql.Timestamp;

@Getter
@Setter
@Builder
public class MoneyHistoryDto {

    //sequence
    private Long moneyTransferHstSn;

    //membSn
    private Long membSn;

    //거래종류 01:충전, 02: 사용, 03:환전
    private Long transferType;
//    private ColumnCode transferType;

    //거래금액 => transferAmt
    private Long transferAmt;

    //결제수단코드 01: 카드, 02:계좌이체, 03: 머니사용
//    private ColumnCode payMeanCd;
    private Long payMeanCd;

    //충전시 결제 거래 고유번호 => payTransferNo
    private String imp_uid;

    //구매이력일련번호 , sequence임
    private Long buyHistory;

    /**
     * 거래내역 페이지를 위해 만든 컬럼
     */
    //거래일자 = 등록일시(baseEntity)
    private Timestamp firstRegistDt;
    //상품명 => goods에서 goodsNm
    private String goodsNm;
    //가맹점명 => merchant에서 merchant
    private String merchantNm;
    //처리구분 =>거래종류,이미 있음
    //결제수단 =>이미 있음
    //처리금액 => 이미 잇음
    //처리상태 => 이거 애매함  => 정상, 오류 => react 단에서 해결하기


    public static MoneyHistoryDto of(MoneyHistory moneyHistory) {

//        Date date = new Date(moneyHistory.getFirstRegistDt().getTime());

        return MoneyHistoryDto.builder()
                .moneyTransferHstSn(moneyHistory.getMoneyTransferHstSn())
                .firstRegistDt(moneyHistory.getFirstRegistDt())
                .membSn(moneyHistory.getMoney().getMoneySn())
                .goodsNm(moneyHistory.getBuyHistory() == null ? null : moneyHistory.getBuyHistory().getGoods().getGoodsNm())
                .merchantNm(moneyHistory.getBuyHistory() == null ? null : moneyHistory.getBuyHistory().getGoods().getMerchant().getMerchantNm())
                .transferType(moneyHistory.getTransferTyCd().getColumnCodeSn()) //04:충전
                .transferAmt(moneyHistory.getTransferAmt())
                .payMeanCd(moneyHistory.getPayMeanCd().getColumnCodeSn())//07:카드
                .imp_uid(moneyHistory.getPayTransferNo())
                .buyHistory(moneyHistory.getBuyHistory() == null ? null : moneyHistory.getBuyHistory().getBuyHstSn())
                .build();
    }


}
