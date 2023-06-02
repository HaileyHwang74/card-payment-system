package net.e4net.demo.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.sql.Timestamp;


@Entity(name = "TB_MONEY_TRANSFER_HST")
@Table(name = "TB_MONEY_TRANSFER_HST")
@Getter
@SequenceGenerator(
        name = "MONEY_SEQ_GENERATOR",
        sequenceName = "MONEY_SEQ",
        initialValue = 1,
        allocationSize = 1
)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MoneyHistory extends BaseEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MONEY_SEQ_GENERATOR")
    @Column(name = "MONEY_TRANSFER_HST_SN", length = 10)
    private Long moneyTransferHstSn;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnore
    @JoinColumn(name = "MEMB_SN", nullable = false)
    private Money money;

    //01:충전, 02: 사용, 03:환전
    @OneToOne
    @JsonIgnore
    @JoinColumn(name = "transfer_ty_cd", referencedColumnName = "column_code_sn")
    private ColumnCode transferTyCd;

    //거래금액
    @Column(name = "TRANSFER_AMT", length = 15)
    private Long transferAmt;

    //결제수단코드 01: 카드, 02:계좌이체, 03: 머니사용
    @OneToOne
    @JsonIgnore
    @JoinColumn(name = "pay_mean_cd", referencedColumnName = "column_code_sn",
            nullable = false)
    private ColumnCode payMeanCd;


    //충전시 결제 거래 고유번호
    @Column(name = "PAY_TRANSFER_NO", length = 20)
    private String payTransferNo;

    //구매이력일련번호
    @OneToOne
    @JsonIgnore
    @JoinColumn(name = "BUY_HST_SN", referencedColumnName = "BUY_HST_SN")
    private BuyHistory buyHistory;

    @Builder
    public MoneyHistory(String useYn, Long firstRegistMembSn, Timestamp firstRegistDt, Long lastRegistMembSn,
                        Timestamp lastChangeDt, Long moneyTransferHstSn, Money money, ColumnCode transferTyCd,
                        Long transferAmt, ColumnCode payMeanCd, String payTransferNo, BuyHistory buyHistory) {
        super("Y", firstRegistMembSn, firstRegistDt, lastRegistMembSn, lastChangeDt);
        this.money = money;
        this.moneyTransferHstSn = moneyTransferHstSn;
        this.transferTyCd = transferTyCd;
        this.transferAmt = transferAmt;
        this.payMeanCd = payMeanCd;
        this.payTransferNo = payTransferNo;
        this.buyHistory = buyHistory;
    }

}
