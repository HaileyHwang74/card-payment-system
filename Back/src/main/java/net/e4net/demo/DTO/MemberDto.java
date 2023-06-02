package net.e4net.demo.DTO;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import net.e4net.demo.Entity.ColumnCode;
import net.e4net.demo.Entity.Enum.MembCls;
import net.e4net.demo.Entity.Member;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;


//@RequiredArgsConstructor
@Getter
@Setter
public class MemberDto {
    //값 다시 받아오기
//    private Long originMembCls; //여기에 프론트단으로부터 값 받아옴  , "3 " (사용자)
//    private Long originMembStatusCd;

    private Long membSn;
    private Long membStatusCdNum;
    private MembCls membCls;

    //swift용
    private String membType;
    private ColumnCode membStatusCd;
    private String membId;
    private String membPwd;
    private String membNm;
    private String mobileNo;
    private String emailAddr;
    private String zipCd;
    private String zipAddr;
    private String detailAddr;
    private String lastLoginDtm;


    @Builder
    private MemberDto(Long membSn, MembCls membCls, ColumnCode membStatusCd, String membId, String membPwd, String membNm,
                      String mobileNo, String emailAddr, String zipCd, String zipAddr, String detailAddr, String lastLoginDtm) {
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

    public Member toMember(PasswordEncoder passwordEncoder) {
        return Member.builder()
                .membId(membId)
                .membPwd(passwordEncoder.encode(membPwd))
                .membNm(membNm)
                .membSn(membSn)
                .membCls(membCls)
                .membStatusCd(ColumnCode.builder().columnCodeSn(membStatusCdNum).build())
                .mobileNo(mobileNo)
                .emailAddr(emailAddr)
                .zipCd(zipCd)
                .zipAddr(zipAddr)
                .detailAddr(detailAddr)
                .lastLoginDtm(lastLoginDtm)
                .build();
    }



    public static MemberDto of(Member member) {
        return MemberDto.builder()
                .membId(member.getMembId())
                .membPwd(member.getMembPwd())
                .membNm(member.getMembNm())
                .membSn(member.getMembSn())
                .membCls(member.getMembCls())
                .membStatusCd(member.getMembStatusCd())
                .mobileNo(member.getMobileNo())
                .emailAddr(member.getEmailAddr())
                .zipCd(member.getZipCd())
                .zipAddr(member.getZipAddr())
                .detailAddr(member.getDetailAddr())
                .lastLoginDtm(member.getLastLoginDtm())
                .build();
    }

    public UsernamePasswordAuthenticationToken toAuthentication() {
        return new UsernamePasswordAuthenticationToken(membId, membPwd);
    }

}
