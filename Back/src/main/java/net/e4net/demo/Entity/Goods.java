package net.e4net.demo.Entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import net.e4net.demo.Config.DatePrefixedSequenceIdGenerator;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity(name = "TB_GOODS")
@Table(name = "TB_GOODS")
@Getter
@DynamicInsert
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@GenericGenerator(
        name = "seq_goods",
        strategy = "net.e4net.demo.Config.DatePrefixedSequenceIdGenerator",
        parameters = {
                @Parameter(name = DatePrefixedSequenceIdGenerator.INCREMENT_PARAM, value = "10")
        })
public class Goods extends BaseEntity {

    /**
     * 일단 Long 타입으로 해놓고 나중에 String으로 바꾸자
     */
    @Id
    @Column(name = "GOODS_NO", length = 15, updatable = false, nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_goods")
    private String goodsNo;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MERCHANT_SN")
    private Merchant merchant;
    @Column(name = "GOODS_NM", length = 200)
    private String goodsNm;
    @Column(name = "GOODS_MODEL_NO", length = 30)
    private String goodsModelNo;
    @Column(name = "GOODS_AMT", length = 20)
    private Long goodsAmt;
    @Column(name = "GOODS_QTT", length = 200)
    private Long goodsQtt;
    @Column(name = "GOODS_SELL_QTT", length = 8)
    private Long goodsSellQtt;
    @Column(name = "GOODS_CLS_DT", length = 8)
    private String goodsClsDt;
    @Column(name = "GOODS_SHOP_COST", length = 6, columnDefinition = "int8 default 0")
    private Long goodsShopCost;
    @Column(name = "REAL_FILE_NM", length = 100)
    private String realFileNm;
    @Column(name = "GOODS_IMG_PATH", length = 100)
    private String goodsImgPath;
    @Column(name = "GOODS_DESC", length = 4000)
    private String goodsDesc;

    @Builder
    public Goods(String useYn, Long frstRegistMembSn, Timestamp frstRegistDt, Long lastRegistMembSn,
                 Timestamp lastChangeDt, String goodsNo, Merchant merchant, String goodsNm, String goodsModelNo,
                 Long goodsAmt, Long goodsQtt, Long goodsSellQtt, String goodsClsDt, Long goodsShopCost, String realFileNm,
                 String goodsImgPath, String goodsDesc) {
        super(useYn, frstRegistMembSn, frstRegistDt, lastRegistMembSn, lastChangeDt);
        this.goodsNo = goodsNo;
        this.merchant = merchant;
        this.goodsNm = goodsNm;
        this.goodsModelNo = goodsModelNo;
        this.goodsAmt = goodsAmt;
        this.goodsQtt = goodsQtt;
        this.goodsSellQtt = goodsSellQtt;
        this.goodsClsDt = goodsClsDt;
        this.goodsShopCost = goodsShopCost;
        this.realFileNm = realFileNm;
        this.goodsImgPath = goodsImgPath;
        this.goodsDesc = goodsDesc;
    }


}
