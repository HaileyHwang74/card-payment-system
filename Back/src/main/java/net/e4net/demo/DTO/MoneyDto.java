package net.e4net.demo.DTO;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import net.e4net.demo.Entity.BuyHistory;
import net.e4net.demo.Entity.Member;
import net.e4net.demo.Entity.Money;

@Getter
@Setter
@Builder
public class MoneyDto {
    private Long moneySn;
    private Long moneyBlce;
//    private Long membSn;
    private Long transferAmt;
    private String payMeanCd;
    private String imp_uid;
    private BuyHistory buyHistory;
    private String transferType;


    //충전
    public Money toMoneyEntity() {
        return Money.builder()
                .moneySn(moneySn)
                .member(Member.builder().membSn(moneySn).build())
                .moneyBlce(moneyBlce)
                .build();
    }
    public static MoneyDto of(Money money) {
        return MoneyDto.builder()
                .moneySn(money.getMoneySn())
                .moneyBlce(money.getMoneyBlce())
                .build();
    }


}
