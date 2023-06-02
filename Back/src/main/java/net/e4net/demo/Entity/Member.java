package net.e4net.demo.Entity;


import lombok.*;
import net.e4net.demo.Entity.Enum.MembCls;

import javax.persistence.*;
import java.sql.Timestamp;


@Entity(name = "TB_MEMB")
@Table(name = "TB_MEMB")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SequenceGenerator(
        name = "MEMBER_SEQ_GENERATOR",
        sequenceName = "MEMBER_SEQ",
        initialValue = 1,
        allocationSize = 1
)
//nullable check 하기
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MEMBER_SEQ_GENERATOR")
    //회원 id, length=10 보통은 이정도의 주석
    @Column(name = "MEMB_SN", length = 10)
    private Long membSn;

    @Enumerated(EnumType.STRING)
    private MembCls membCls;

    @OneToOne()
    @JoinColumn(name = "memb_status_cd", referencedColumnName = "COLUMN_CODE_SN")
    private ColumnCode membStatusCd;
    @Column(name = "MEMB_ID", nullable = false, length = 12, unique = true)
    private String membId;
    @Column(name = "MEMB_PWD", nullable = false, length = 100)
    private String membPwd;
    @Column(name = "MEMB_NM", nullable = false, length = 100)
    private String membNm;
    @Column(name = "MOBILE_NO", length = 20)
    private String mobileNo;
    @Column(name = "EMAIL_ADDR", unique = true, length = 100)
    private String emailAddr;
    @Column(name = "ZIP_CD", length = 6)
    private String zipCd;
    @Column(name = "ZIP_ADDR", length = 150)
    private String zipAddr;
    @Column(name = "DETAIL_ADDR", length = 150)
    private String detailAddr;
    @Column(name = "LAST_LOGIN_DTM", length = 14)
    private String lastLoginDtm;

    @OneToOne(mappedBy = "member", cascade = CascadeType.PERSIST)
    private Money money;


    @Builder
    private Member(String useYn, Long firstRegistMembSn, Timestamp firstRegistDt, Long lastRegistMembSn,
                   Timestamp lastChangeDt, Long membSn, MembCls membCls, ColumnCode membStatusCd, String membId, String membPwd, String membNm,
                   String mobileNo, String emailAddr, String zipCd, String zipAddr, String detailAddr, String lastLoginDtm) {
        super("Y", 1L, firstRegistDt, lastRegistMembSn, lastChangeDt);
        this.membSn = membSn;
        this.membCls = membCls;
        this.membStatusCd = membStatusCd;
        this.membId = membId;
        this.membPwd = membPwd;
        this.membNm = membNm;
        this.mobileNo = mobileNo;
        this.emailAddr = emailAddr;
        this.zipCd = zipCd;
        this.zipAddr = zipAddr;
        this.detailAddr = detailAddr;
        this.lastLoginDtm = lastLoginDtm;
    }


    public static Member createMember(Member member) {
        return Member.builder()
                .membSn(member.membSn)
                .membCls(member.membCls)
                .membStatusCd(member.membStatusCd)
                .membNm(member.membNm)
                .membId(member.membId)
                .membPwd(member.membPwd)
                .mobileNo(member.mobileNo)
                .emailAddr(member.emailAddr)
                .zipCd(member.zipCd)
                .zipAddr(member.zipAddr)
                .detailAddr(member.detailAddr)
                .lastLoginDtm(member.lastLoginDtm)
                .build();
    }


    public void setMoney(Money money) {
        this.money = money;
    }

    //비밀번호 찾기(안드로이드)
    public void setPassWord(String passWord) {
        this.membPwd = passWord;
    }


}
