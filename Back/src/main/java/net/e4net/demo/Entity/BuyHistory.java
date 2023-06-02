package net.e4net.demo.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.sql.Timestamp;
import static javax.persistence.FetchType.LAZY;


@Entity(name = "TB_BUY_HST")
@Table(name = "TB_BUY_HST")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SequenceGenerator(
        name = "BUY_HST_SEQ_GENERATOR",
        sequenceName = "BUY_HST_SEQ",
        initialValue = 1,
        allocationSize = 1
)
public class BuyHistory extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "BUY_HST_SEQ_GENERATOR")
    //회원 id, length=10 보통은 이정도의 주석
    @Column(name = "BUY_HST_SN", length = 10)
    private Long buyHstSn;
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "MEMB_SN")
    @JsonIgnore
    private Member member;
    @ManyToOne(fetch = LAZY)
    @JsonIgnore
    @JoinColumn(name = "GOODS_NO")
    private Goods goods;
    @Column(name = "GOODS_AMT", length = 15)
    private Long goodsAmt;
    @Column(name = "BUY_QTT", length = 8)
    private Long buyQtt;
    @Column(name = "BUY_AMT", length = 15)
    private Long buyAmt;
    @OneToOne(mappedBy = "buyHistory", cascade = CascadeType.ALL)
    @JsonIgnore
    private MoneyHistory moneyHistory;

    @Builder
    public BuyHistory(String useYn, Long frstRegistMembSn, Timestamp frstRegistDt, Long lastRegistMembSn,
                    Timestamp lastChangeDt, Long buyHstSn, MoneyHistory moneyHistory, Member member,
                    Goods goods, Long goodsAmt, Long buyQtt, Long buyAmt) {
        super("Y", frstRegistMembSn, frstRegistDt, lastRegistMembSn, lastChangeDt);
        this.buyHstSn = buyHstSn;
        this.moneyHistory = moneyHistory;
        this.member = member;
        this.goods = goods;
        this.goodsAmt = goodsAmt;
        this.buyQtt = buyQtt;
        this.buyAmt = buyAmt;
    }

}
