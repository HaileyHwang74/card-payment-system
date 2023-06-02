package net.e4net.demo.Entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import javax.persistence.*;

@Entity(name = "TB_BASKET")
@Table(name = "TB_BASKET")
@Getter
@DynamicInsert
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SequenceGenerator(
        name = "BASKET_SEQ_GENERATOR",
        sequenceName = "BASKET_SEQ",
        initialValue = 1,
        allocationSize = 1
)

public class Basket {

    @Id
    @Column(name = "BASKET_NO", length = 15, updatable = false, nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "BASKET_SEQ_GENERATOR")
    private Long basketNo; //primary key
    @Column(name = "BASKET_NM", length = 200)
    private String basketNm;  //수육국밥
    @Column(name = "BASKET_AMT", length = 200)
    private Long basketAmt;
    @Column(name = "BASKET_COUNT", length = 10)
    private Long basketCount;

    @Column(name = "BASKET_MEMBSN", length = 10)
    private Long membSn;

    @Column(name = "GOODS_NO", length = 15)
    private String basketGoodsNo;

    @Builder
    public Basket(Long basketNo, String basketNm, Long basketAmt, Long basketCount, Long membSn, String basketGoodsNo) {
        this.basketNo = basketNo;
        this.basketNm = basketNm;
        this.basketAmt = basketAmt;
        this.basketCount = basketCount;
        this.membSn = membSn;
        this.basketGoodsNo = basketGoodsNo;
    }

}
