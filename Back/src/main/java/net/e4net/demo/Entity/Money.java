package net.e4net.demo.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import javax.persistence.*;
import java.sql.Timestamp;

@Entity(name = "TB_MEMB_MONEY")
@Table(name = "TB_MEMB_MONEY")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicInsert
@SequenceGenerator(
        name = "TB_MEMB_MONEY_SEQ_GENERATOR",
        sequenceName = "TB_MEMB_MONEY_SEQ",
        initialValue = 1,
        allocationSize = 1
)

public class Money extends BaseEntity {

    @Id
    @Column(name = "MONEY_SN", length = 10)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TB_MEMB_MONEY_SEQ_GENERATOR")
    private Long moneySn;

    @OneToOne()
    @JsonIgnore
    @JoinColumn(name = "MEMB_SN", referencedColumnName = "MEMB_SN")
    private Member member;

    @Column(columnDefinition = "int8 default 0")    //왜 int8이어야하나
    private Long moneyBlce;


    public void setMoneyBlce(Long moneyBlce) {
        this.moneyBlce = moneyBlce;
    }


    @Builder
    private Money(String useYn, Long firstRegistMembSn, Timestamp firstRegistDt, Long lastRegistMembSn,
                  Timestamp lastChangeDt, Long moneySn, Member member, Long moneyBlce) {
        super("Y",firstRegistMembSn, firstRegistDt, lastRegistMembSn, lastChangeDt);
        this.member = member;
        this.moneySn = moneySn;
        this.moneyBlce = moneyBlce;
        this.member.setMoney(this);
    }

    //영속성 유지
    public static Money createMoney(Member member) {
        return Money.builder()
                .member(member)
                .build();
    }


}
