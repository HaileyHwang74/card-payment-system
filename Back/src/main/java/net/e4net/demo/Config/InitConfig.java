package net.e4net.demo.Config;

import lombok.RequiredArgsConstructor;
import net.e4net.demo.Entity.*;
import net.e4net.demo.Entity.Enum.MembCls;
import net.e4net.demo.Repository.*;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;

//Bean Configuration에 따로 Bean을 등록하지 않아도 사용
@Component
@RequiredArgsConstructor
public class InitConfig {

    private final ColumnCodeRepository codeRepository;
    private final MemberRepository memberRepository;
    private final MoneyRepository moneyRepository;
    private final MerchantRepository merchantRepository;

    private final GoodsRepository goodsRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * Q.어떻게 ColumnCode를 부를 수 있는지  => 기존에도 안 불렀었다.이거 @RequiredArgsConstructor 이런것들이 자동으로 역할해주나보다...
     */
//    private final ColumnCode columnCode;
    @PostConstruct
    @Transactional
    public void CreateColumnCode() {
        /**
         * 실행 할때마다 인서트 되므로 기존에 data가 들어가있는지 확인해야한다.
         * 방법: 첫번재 데이터를 찾아보는 조건을 걸어서 isEmpty(값 자체가 존재하지 않음)가 True면 아래의 코드가 실행되게하자.
         */
        if (this.codeRepository.findById(1L).isEmpty()) {
            this.codeRepository.save(ColumnCode.builder().codeId("memb_status_cd").codeValue("10").codeDesc("가입").build());
            this.codeRepository.save(ColumnCode.builder().codeId("memb_status_cd").codeValue("20").codeDesc("휴면").build());
            this.codeRepository.save(ColumnCode.builder().codeId("memb_status_cd").codeValue("99").codeDesc("탈퇴").build());
            this.codeRepository.save(ColumnCode.builder().codeId("transfer_ty_cd").codeValue("01").codeDesc("충전").build());
            this.codeRepository.save(ColumnCode.builder().codeId("transfer_ty_cd").codeValue("02").codeDesc("사용").build());
            this.codeRepository.save(ColumnCode.builder().codeId("transfer_ty_cd").codeValue("03").codeDesc("환전").build());
            this.codeRepository.save(ColumnCode.builder().codeId("pay_mean_cd").codeValue("01").codeDesc("카드").build());
            this.codeRepository.save(ColumnCode.builder().codeId("pay_mean_cd").codeValue("02").codeDesc("계좌이체").build());
            this.codeRepository.save(ColumnCode.builder().codeId("pay_mean_cd").codeValue("03").codeDesc("머니사용").build());

            //admin 미리 생성
            Member tbMembAdmin = memberRepository.save(Member.builder()
                    .membId("admin")
                    .membPwd(passwordEncoder.encode("admin"))
                    .membCls(MembCls.ROLE_ADMIN)
                    .membStatusCd(ColumnCode.builder().columnCodeSn(1l).build())
                    .membNm("어드민")
                    .build());
            moneyRepository.save(Money.builder().member(tbMembAdmin).moneyBlce(10000l).build());


            //1번째
            //seller
            Member tbMembSeller = memberRepository.save(Member.builder()
                    .membId("seller")
                    .membPwd(passwordEncoder.encode("seller"))
                    .membCls(MembCls.ROLE_SELLER)
                    .membStatusCd(ColumnCode.builder().columnCodeSn(1l).build())
                    .membNm("황서현")
                    .build());
            Money ff = Money.builder().member(tbMembSeller).build();
            moneyRepository.save(Money.builder().member(tbMembSeller).build());
            //가맹점
            Merchant merchant1 = Merchant.builder()
                    .membSn(tbMembSeller)
                    .merchantNm("담미온")
                    .build();
          merchant1 = merchantRepository.save(merchant1);
          //상품
            Goods goods1 = Goods.builder()
                    .merchant(merchant1)
                    .goodsNm("수육국밥")
                    .goodsModelNo("1")
                    .goodsAmt(1000L)
                    .goodsQtt(30L)
                    .goodsSellQtt(1L)
                    .goodsClsDt("20221231")
                    .goodsShopCost(2500L)
                    .goodsImgPath("su_yuk.jpg")
                    .goodsDesc("고기가 많음")
                    .build();
            Goods goods2 = Goods.builder()
                    .merchant(merchant1)
                    .goodsNm("순대국밥")
                    .goodsModelNo("2")
                    .goodsAmt(1500L)
                    .goodsQtt(20L)
                    .goodsSellQtt(1L)
                    .goodsClsDt("20221221")
                    .goodsImgPath("sun_dae.jpg")
                    .goodsDesc("고기는 적지만, 순대가 있음")
                    .build();
            goodsRepository.save(goods1);
            goodsRepository.save(goods2);

            //2번쨰
            //seller
            Member tbMembSeller2 = memberRepository.save(Member.builder()
                    .membId("seller2")
                    .membPwd(passwordEncoder.encode("seller2"))
                    .membCls(MembCls.ROLE_SELLER)
                    .membStatusCd(ColumnCode.builder().columnCodeSn(1l).build())
                    .membNm("최미르")
                    .build());

            moneyRepository.save(Money.builder().member(tbMembSeller2).build());

            //가맹점
            Merchant merchant2 = Merchant.builder()
                    .membSn(tbMembSeller2)
                    .merchantNm("무삥과 팟타이")
                    .build();
            merchant2 = merchantRepository.save(merchant2);
            //상품
            Goods goods3 = Goods.builder()
                    .merchant(merchant2)
                    .goodsNm("팟타이")
                    .goodsModelNo("3")
                    .goodsAmt(1000L)
                    .goodsQtt(30L)
                    .goodsSellQtt(1L)
                    .goodsClsDt("20221231")
                    .goodsShopCost(2500L)
                    .goodsImgPath("pot_tie.jpg")
                    .goodsDesc("볶음면임")
                    .build();
            Goods goods4 = Goods.builder()
                    .merchant(merchant2)
                    .goodsNm("쌀국수")
                    .goodsModelNo("4")
                    .goodsAmt(1500L)
                    .goodsQtt(20L)
                    .goodsSellQtt(1L)
                    .goodsClsDt("20221221")
                    .goodsImgPath("ssal_guk_su.jpg")
                    .goodsDesc("국물이 있음")
                    .build();
            goodsRepository.save(goods3);
            goodsRepository.save(goods4);

        }
    }

//    @PostConstruct
//    public void CreateMerchant() {
//        if (this.merchantRepository.findById(1L).isEmpty()) {
//            this.merchantRepository.save(Merchant.builder().merchantNm("이포넷").merchantDesc("it기업").homePage_url("www.e4net.net").telNo("01089408679").emailAddr("hellocie@naver.com").zipCd("1111").zipAddr("삼성역").detailAddr("동성빌딩").member(Member.builder().membSn(1L).build()).build());
//            this.merchantRepository.save(Merchant.builder().merchantNm("김기현컴퍼니").merchantDesc("it기업").homePage_url("www.kim.net").telNo("01089408679").emailAddr("hellocie@naver.com").zipCd("1111").zipAddr("삼성역").detailAddr("동성빌딩").member(Member.builder().membSn(2L).build()).build());
//        }
    /**
     * member 먼저 만들고, 그래야 membSn이 만들어짐
     */




//        public static Money createMoney (Member member){
//            return Money.builder().member(member).build();
//        }

        /**
         * 어드민 계정 , 처음 시작할 때 아예 생성해버리기
         */

    }



//    insert into tb_column_code values (nextval('column_code_seq'), 'memb_cls', 'role_admin', '어드민');
//        insert into tb_column_code values (nextval('column_code_seq'), 'memb_cls', 'role_seller', '판매자');
//        insert into tb_column_code values (nextval('column_code_seq'), 'memb_cls', 'role_user', );
//        insert into tb_column_code values (nextval('column_code_seq'), 'memb_status_cd', '10', '가입');
//        insert into tb_column_code values (nextval('column_code_seq'), 'memb_status_cd', '20', '휴면');
//        insert into tb_column_code values (nextval('column_code_seq'), 'memb_status_cd', '99', '탈퇴');
//        insert into tb_column_code values (nextval('column_code_seq'), 'transfer_ty_cd', '01', '충전');
//        insert into tb_column_code values (nextval('column_code_seq'), 'transfer_ty_cd', '02', '사용');
//        insert into tb_column_code values (nextval('column_code_seq'), 'transfer_ty_cd', '03', '환전');
//        insert into tb_column_code values (nextval('column_code_seq'), 'pay_mean_cd', '01', '카드');
//        insert into tb_column_code values (nextval('column_code_seq'), 'pay_mean_cd', '02', '계좌이체');
//        insert into tb_column_code values (nextval('column_code_seq'), 'pay_mean_cd', '03', '머니사용');