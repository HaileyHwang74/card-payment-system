package net.e4net.demo.Entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;

import static javax.persistence.FetchType.LAZY;

@Entity(name = "TB_MEMB_LOGIN_HST")
@Table(name = "TB_MEMB_LOGIN_HST")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SequenceGenerator(
        name = "MEMB_LOGIN_HST_SEQ_GENERATOR",
        sequenceName = "MEMB_LOGIN_HST_SEQ",
        initialValue = 1,
        allocationSize = 1
)
public class LoginHistory extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MEMB_LOGIN_HST_SEQ_GENERATOR")
    //회원 id, length=10 보통은 이정도의 주석
    @Column(name = "LOGIN_SN", length = 10)
    private Long loginSn;
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "MEMB_SN", referencedColumnName = "MEMB_SN")
    private Member member;
    @Column(name = "CONNECT_IP", length = 50)
    private String connectIp;


    @Builder
    public LoginHistory(String useYn, Long firstRegistMembSn, Timestamp firstRegistDt, Long lastRegistMembSn, Timestamp lastChangeDt, Long loginSn, Member member, String connectIp) {
        super(useYn, firstRegistMembSn, firstRegistDt, lastRegistMembSn, lastChangeDt);
        this.loginSn = loginSn;
        this.member = member;
        this.connectIp = connectIp;
    }
}
