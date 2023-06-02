package net.e4net.demo.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.e4net.demo.DTO.MoneyHistoryDto;
import net.e4net.demo.Entity.*;
import net.e4net.demo.QueryDsl.CustomRepositoryImpl;
import net.e4net.demo.Repository.MoneyHistoryRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;


@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MoneyHistoryService {

    private final MoneyHistoryRepository moneyHistoryRepository;
    private final CustomRepositoryImpl customRepository;

    /**
     * 페이징 처리 안 하고, 전체 list 뽑아본 것
     * @param membSn
     * @return
     */
    public List<MoneyHistoryDto> selectAll(Long membSn){

        List<MoneyHistory> moneyHistories = moneyHistoryRepository.findByMoneyMoneySn(membSn);
        log.debug("몇 개 나오나 =>{}", moneyHistories.size());

        List<MoneyHistoryDto> moneyHistoryDto = new ArrayList<>();
        for (MoneyHistory e : moneyHistories) {
            moneyHistoryDto.add(MoneyHistoryDto.of(e));
        }
//        Optional<Goods> goods = goodsRepository.findById(membSn);
//        Optional<Merchant> merchant = merchantRepository.findById(membSn);
//        String goodsNm = goods.get().getGoodsNm();
//        String merchantNm =merchant.get().getMerchantNm();

        log.debug("moneyHistory 만들어졌나=>{}", moneyHistories.size());
       return moneyHistoryDto;
    }


    /**
     * 페이징 처리 All List
     * @param pageable
     * @param membSn
     * @param rownum
     * @return
     */
    public Page<MoneyHistoryDto> selectPage(Pageable pageable, Long membSn, int rownum) {
        return customRepository.findAll(pageable, membSn,rownum);
    }

    /**
     * 페이징처리 => 결제수단 검색
     * @param pageable
     * @param membSn
     * @param rownum
     * @param payMeanCd
     * @return
     */
    public Page<MoneyHistoryDto> searchPage(Pageable pageable, Long membSn, int rownum, Long payMeanCd) {
        return customRepository.findByMeanCd(pageable, membSn,rownum,payMeanCd);
    }

    /**
     * 페이징 처리 => 날짜 검색
     * @param pageable
     * @param membSn
     * @param rownum
     * @param t1
     * @param t2
     * @return
     */
    public Page<MoneyHistoryDto> searchDate(Pageable pageable, Long membSn, int rownum, Timestamp t1, Timestamp t2) {

        return customRepository.dateSearch(pageable, membSn,rownum,t1,t2);
    }

    /**
     * 날짜 + 결제수단 검색
     * @param pageable
     * @param membSn
     * @param rownum
     * @param payMeanCd
     * @param t1
     * @param t2
     * @return
     */
    public Page<MoneyHistoryDto> searchDatePayMeanCd(Pageable pageable, Long membSn, int rownum, Long payMeanCd, Timestamp t1, Timestamp t2) {

        return customRepository.datePayMeanCd(pageable, membSn,rownum,payMeanCd,t1,t2);
    }

}
