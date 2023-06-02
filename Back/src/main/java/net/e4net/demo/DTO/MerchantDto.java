package net.e4net.demo.DTO;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import net.e4net.demo.Entity.Merchant;


@Builder
@Getter
@Setter
public class MerchantDto {

    //
    private Long membSn;
//    private Member membSn;
    private Long merchantSn;
    private String merchantNm;
    private String merchantDesc;
    private String homePage_url;
    private String telNo;
    private String emailAddr;
    private String zipCd;
    private String zipAddr;
    private String detailAddr;

//    public Merchant toMerchantEntity(MerchantDto dto) {
//        return Merchant.builder()
//                .merchantSn(merchantSn)
//                .member(Member.builder().membSn(dto.getMembSn()).build())
//                .merchantNm(merchantNm)
//                .merchantDesc(merchantDesc)
//                .homePage_url(homePage_url)
//                .telNo(telNo)
//                .emailAddr(emailAddr)
//                .zipAddr(zipAddr)
//                .zipCd(zipCd)
//                .detailAddr(detailAddr)
//                .build();
//    }
public static MerchantDto of(Merchant merchant) {
    return MerchantDto.builder()
            .membSn(merchant.getMemb().getMembSn())
            .merchantSn(merchant.getMerchantSn())
            .merchantNm(merchant.getMerchantNm())
            .merchantDesc(merchant.getMerchantDesc())
            .homePage_url(merchant.getHomePage_url())
            .telNo(merchant.getTelNo())
            .emailAddr(merchant.getEmailAddr())
            .zipCd(merchant.getZipCd())
            .zipAddr(merchant.getZipAddr())
            .detailAddr(merchant.getDetailAddr())
            .build();
}


}
