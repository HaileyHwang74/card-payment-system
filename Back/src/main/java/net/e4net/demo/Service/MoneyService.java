package net.e4net.demo.Service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.e4net.demo.DTO.*;
import net.e4net.demo.Entity.*;
import net.e4net.demo.Repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MoneyService {

    private final MoneyRepository moneyRepository;

    private final MoneyHistoryRepository moneyHistoryRepository;


    private final BuyHistoryRepository buyHistoryRepository;

    private final BasketRepository basketRepository;

    /**
     * 잔고조회
     *
     * @param membSn
     * @return
     */
    public Long checkBalance(Long membSn) {
        log.debug("membSn => {}", membSn);
        Optional<Money> money = moneyRepository.findById(membSn);  // entity임
        Long result = money.get().getMoneyBlce();
        log.debug("membSn => {}", membSn);
        return result;
    }

    /**
     * 머니(포인트) 충전하기  + 거래이력 남기기
     * @param dto
     * @return
     */
    @Transactional
    public MoneyDto chargeMoney(MoneyDto dto) {
        // Long tycd = payMethod.equals("충전")||payMethod.equals("사용")? 7L : 8L;
        // Long meanCd = payMeanCd.equals("머니")||payMeanCd.equals("카드") ? 12l : 10l;
        Money money = dto.toMoneyEntity();
        System.out.println("money.getMember().getMembSn() " + money.getMember().getMembSn());
        System.out.println("dto.getTransferType" + dto.getTransferType());
        System.out.println("dto.getPayMeanCd()" + dto.getPayMeanCd()); //point
        // 충전, 머니 사용, 카드 사용  이 3가지 경우중 카드로 결제만 save 안 함.
        moneyRepository.save(money);
        moneyHistoryRepository.save(MoneyHistory.builder()
                .money(money)
                .transferTyCd(ColumnCode.builder().columnCodeSn((dto.getTransferType().equals("01")) ? 4l : 5l).build())  //거래종류: 충전
                .transferAmt(dto.getTransferAmt())
                .payMeanCd(ColumnCode.builder().columnCodeSn(dto.getPayMeanCd().equals("01") ? 7l : 9l).build()) //거래종류: 카드, 계좌이체 없음
                .payTransferNo(dto.getImp_uid())
                .build());

        return dto;
    }

    /**
     * 카드로만 계산 + 구매이력
     */
    @Transactional
    public BuyHistory useCard(BuyHistoryDto dto) {
        BuyHistory buyHistory = dto.toEntity();
        log.debug("membSn 왜 안 들어가나.. => {}", buyHistory.getMember().getMembSn());
        buyHistory = buyHistoryRepository.save(buyHistory);
        return buyHistory;
    }

    /**
     * 충전한 머니로 계산 + 거래이력 + 구매이력
     */
    @Transactional
    public MoneyDto useMoney(BuyHistoryDto dto) {

        Money money = moneyRepository.findById(dto.getMemb()).orElse(null);
        log.debug("잔고가 존재할까? => {}, {}", money.getMember().getMembSn(), money.getMoneySn());
        Long blce = money.getMoneyBlce();
        Long amt = dto.getBuyAmt();
        log.debug("buyAmt: {},",dto.getBuyAmt());
        Long res = blce - amt;
        log.debug("결제 하고 남을 돈 => {}", res);
        if (res < 0) {
            throw new RuntimeException("잔고에 돈이 부족합니다.");
        }
        BuyHistory buyHistory = dto.toEntity();
        // 구매이력 저장
        buyHistory = buyHistoryRepository.save(buyHistory);
        log.debug("구매이력 있습니까? ={}", buyHistory.getMember().getMembSn());
        // 머니거래이력 저장
        moneyHistoryRepository.save(MoneyHistory.builder()
                .buyHistory(buyHistory)
                .money(money)
                .transferTyCd(ColumnCode.builder().columnCodeSn(5l).build())  //거래종류: 충전
                .transferAmt(amt)
                .payMeanCd(ColumnCode.builder().columnCodeSn(9l).build()) //거래종류: 카드, 계좌이체 없음
                .build());
        log.debug("여기까지는 옵니까?");

        // 여기까지가 히스토리 남기는 거 - 예외 안났으면 이제 통장 정보 수정
        // 변경감지로 값 수정하기
        money.setMoneyBlce(res);

        //money = membMoneyRepository.save(money.builder().moneyBlce(res).build());
//		// 이거 근데 이렇게 재사용 해도 되나?
        return MoneyDto.of(money);
    }

    /**장바구니
     * @param dto
     * @return
     */
    @Transactional
    public Basket setBasket(BasketDto dto){
//        Basket basket = dto.toBasket(dto);
        Basket basket = dto.toBasket();
        log.debug("basket 왜 안 들어가나.. => {}", basket.getBasketNm());
        log.debug("basket 왜 안 들어가나.. => {}", basket.getBasketAmt());
        basketRepository.save(basket);
        return basket;
    }

    @Transactional
    public List<BasketDto> getBasketAll(Long membSn) {
        log.debug("membSn 왜 안 들어가나.. => {}", membSn);
        List<Basket> baskets = basketRepository.findAllByMembSn(membSn);
//        List<Basket> baskets = basketRepository.findByAllNum(basket.getBasketNm());
//        Optional<Basket> basket = basketRepository.findByMembSn(membSn);
        log.debug("count=>{}", baskets.size());
        List<BasketDto> basketDtos = new ArrayList<>();
        for (Basket e : baskets) {
            basketDtos.add(BasketDto.of(e));
        }
        return basketDtos;
    }

    /**나중에 basketNo => list로 받아서 front 단 말고, back단에서 처리해보기*/
    @Transactional
    public void deleteBasket(Long basketNo){
        basketRepository.deleteById(basketNo);
    }



}