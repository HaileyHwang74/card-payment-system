package net.e4net.demo.DTO;


import lombok.Builder;
import lombok.Getter;
import net.e4net.demo.Entity.Basket;

@Getter
@Builder
public class BasketDto {
    private Long membSn;
    private Long basketNo;
    private String basketNm;

    private Long basketAmt;
    private Long basketCount;

    private String basketGoodsNo;

    public Basket toBasket() {
        return Basket.builder()
                .basketNo(basketNo)
                .basketCount(basketCount)
                .basketNm(basketNm)
                .basketAmt(basketAmt)
                .membSn(membSn)
                .basketGoodsNo(basketGoodsNo)
                .build();
    }

    public static BasketDto of(Basket basket){
        return BasketDto.builder()
                .basketNo(basket.getBasketNo())
                .basketCount(basket.getBasketCount())
                .basketNm(basket.getBasketNm())
                .basketAmt(basket.getBasketAmt())
                .membSn(basket.getMembSn())
                .basketGoodsNo(basket.getBasketGoodsNo())
                .build();
    }


}
