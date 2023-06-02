package net.e4net.demo.Entity;

import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;


@Entity(name = "TB_MERCHANT")
@Table(name = "TB_MERCHANT")
@Getter
@RequiredArgsConstructor
@SequenceGenerator(
        name = "MERCHANT_SEQ_GENERATOR",
        sequenceName = "MERCHANT_SEQ",
        initialValue = 1,
        allocationSize = 1
)
public class Merchant extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MERCHANT_SEQ_GENERATOR")
    //회원 id, length=10 보통은 이정도의 주석
    @Column(name = "MERCHANT_SN", length = 10)
    private Long merchantSn;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMB", referencedColumnName = "MEMB_SN")//, referencedColumnName = "MEMB_SN"
    private Member memb;
    @Column(name = "MERCHANT_NM", length = 100)
    private String merchantNm;
    @Column(name = "MERCHANT_DESC", length = 400)
    private String merchantDesc;
    @Column(name = "MERCHANT_URL", length = 100)
    private String homePage_url;
    @Column(name = "TEL_NO", length = 20)
    private String telNo;
    @Column(name = "EMAIL_ADDR", length = 100)
    private String emailAddr;
    @Column(name = "ZIP_CD", length = 6)
    private String zipCd;
    @Column(name = "ZIP_ADDR", length = 150)
    private String zipAddr;
    @Column(name = "DETAIL_ADDR", length = 150)
    private String detailAddr;

    @Builder
    private Merchant(Long merchantSn,Timestamp firstRegistDt,Timestamp lastChangeDt,Long lastRegistMembSn, Member membSn, String merchantNm, String merchantDesc, String homePage_url,
                     String telNo, String emailAddr, String zipCd, String zipAddr, String detailAddr) {
        super("Y", 1L, firstRegistDt, 2L, lastChangeDt);
        this.merchantSn = merchantSn;
        this.memb = membSn;
        this.merchantNm = merchantNm;
        this.merchantDesc = merchantDesc;
        this.homePage_url = homePage_url;
        this.telNo = telNo;
        this.emailAddr = emailAddr;
        this.zipCd = zipCd;
        this.zipAddr = zipAddr;
        this.detailAddr = detailAddr;
    }





//    public static MerchantDto toMerchantDto(Merchant merchant) {
//        return MerchantDto.builder()
//                .merchantSn(merchant.getMerchantSn())
//                .member(merchant.getMember())
//                .merchantNm(merchant.getMerchantNm())
//                .merchantDesc(merchant.getMerchantDesc())
//                .homePage_url(merchant.getHomePage_url())
//                .telNo(merchant.getTelNo())
//                .emailAddr(merchant.getEmailAddr())
//                .zipAddr(merchant.getZipAddr())
//                .zipCd(merchant.getZipCd())
//                .detailAddr(merchant.getDetailAddr())
//                .build();
//    }
}


