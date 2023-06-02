package net.e4net.demo.DTO;

import lombok.Getter;
import lombok.Setter;
import net.e4net.demo.Entity.BuyHistory;
import net.e4net.demo.Entity.Goods;
import net.e4net.demo.Entity.Member;
import net.e4net.demo.Entity.MoneyHistory;

@Getter
@Setter
public class BuyHistoryDto {
    private Long buyHstSn; 
    private MoneyHistory moneyHistory; 
    private Long memb; // 회원번호
    private String goods; //상품번호

//    private String goods[];   //상품번호 여러개
    private Long goodsAmt; // 상품금액
    private Long buyQtt; // 구매수량
    private Long buyAmt; // 구매금액

    public BuyHistory toEntity(){
        return BuyHistory.builder()
                .member(Member.builder().membSn(memb).build())
                .goods(Goods.builder().goodsNo(goods).build())
                .goodsAmt(goodsAmt)
                .buyQtt(buyQtt)
                .buyAmt(buyAmt)
                .build();
    }

}
