package net.e4net.demo.QueryDsl;


import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.e4net.demo.DTO.MoneyHistoryDto;
import net.e4net.demo.Entity.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Repository
public class CustomRepositoryImpl implements payRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    /**
     * join 순서 : MoneyHistory => BuyHistory => Goods => Merchants
     */

    private QMoneyHistory moneyHistory = QMoneyHistory.moneyHistory;
    private QBuyHistory buyHistory = QBuyHistory.buyHistory;
    private QGoods goods = QGoods.goods;
    private QMerchant merchant = QMerchant.merchant;


    //일단 전체 List만 출력
    @Override
    public Page<MoneyHistoryDto> findAll(Pageable pageable, Long membSn, int rownum) {

        List<MoneyHistory> result = jpaQueryFactory.select(moneyHistory)
                .from(moneyHistory)
                .leftJoin(moneyHistory.buyHistory, buyHistory)
                .fetchJoin()
                .leftJoin(buyHistory.goods, goods)
                .fetchJoin()
                .leftJoin(goods.merchant, merchant)
                .fetchJoin()
                .where(
                        moneyHistory.money.member.membSn.eq(membSn)
                )
                .offset(rownum).limit(pageable.getPageSize())
                .fetch();

        List<MoneyHistoryDto> c = result.stream().map(MoneyHistoryDto::of).collect(Collectors.toList());

        Integer count = jpaQueryFactory.selectFrom(moneyHistory)
                .where(
                        moneyHistory
                                .money.member.membSn
                                .eq(membSn)
                ).fetch().size();
        log.info("fff" + count.toString()); //4

        return new PageImpl<>(c, pageable, count);
    }

    //페이지 --결제수단 검색
    @Override
    public Page<MoneyHistoryDto> findByMeanCd(Pageable pageable, Long membSn, int rownum, Long payMeanCd) {

        List<MoneyHistory> result = jpaQueryFactory.select(moneyHistory)
                .from(moneyHistory)
                .leftJoin(moneyHistory.buyHistory, buyHistory)
                .fetchJoin()
                .leftJoin(buyHistory.goods, goods)
                .fetchJoin()
                .leftJoin(goods.merchant, merchant)
                .fetchJoin()
                .where(
                        moneyHistory.money.member.membSn.eq(membSn)
                                .and(moneyHistory.payMeanCd.columnCodeSn.eq(payMeanCd)))
                .offset(rownum).limit(pageable.getPageSize())
                .fetch();

        List<MoneyHistoryDto> c = result.stream().map(MoneyHistoryDto::of).collect(Collectors.toList());

        Integer count = jpaQueryFactory.selectFrom(moneyHistory)
                .where(
                        moneyHistory
                                .money.member.membSn
                                .eq(membSn)
                                .and(moneyHistory.payMeanCd.columnCodeSn.eq(payMeanCd))).fetch().size();

        log.info("fff" + count.toString()); //4
        log.debug("payMeanCd=>{}", payMeanCd.toString());
        return new PageImpl<>(c, pageable, count);
    }


    //페이지 -- 날짜 검색 +결제수단
    @Override
    public Page<MoneyHistoryDto> datePayMeanCd(Pageable pageable, Long membSn, int rownum, Long payMeanCd, Timestamp searchStartDate, Timestamp searchEndDate) {


        List<MoneyHistory> result = jpaQueryFactory.select(moneyHistory)
                .from(moneyHistory)
                .leftJoin(moneyHistory.buyHistory, buyHistory)
                .fetchJoin()
                .leftJoin(buyHistory.goods, goods)
                .fetchJoin()
                .leftJoin(goods.merchant, merchant)
                .fetchJoin()
                .where(
                        moneyHistory.money.member.membSn.eq(membSn)
                                .and(moneyHistory.firstRegistDt.between(searchStartDate, searchEndDate))
                                .and(moneyHistory.payMeanCd.columnCodeSn.eq(payMeanCd)))
                .offset(rownum).limit(pageable.getPageSize())
                .fetch();

        List<MoneyHistoryDto> c = result.stream().map(MoneyHistoryDto::of).collect(Collectors.toList());

        Integer count = jpaQueryFactory.selectFrom(moneyHistory)
                .where(
                        moneyHistory
                                .money.member.membSn.eq(membSn)
                                .and(moneyHistory.payMeanCd.columnCodeSn.eq(payMeanCd))
                                .and(moneyHistory.firstRegistDt.between(searchStartDate, searchEndDate))

                ).fetch().size();

        log.info("fff" + count.toString()); //4
        log.debug("payMeanCd=>{}", payMeanCd.toString());
        return new PageImpl<>(c, pageable, count);
    }

    //only 날짜 검색
    @Override
    public Page<MoneyHistoryDto> dateSearch(Pageable pageable, Long membSn, int rownum,  Timestamp searchStartDate, Timestamp searchEndDate){

        List<MoneyHistory> result = jpaQueryFactory.select(moneyHistory)
                .from(moneyHistory)
                .leftJoin(moneyHistory.buyHistory, buyHistory)
                .fetchJoin()
                .leftJoin(buyHistory.goods, goods)
                .fetchJoin()
                .leftJoin(goods.merchant, merchant)
                .fetchJoin()
                .where(
                        moneyHistory.money.member.membSn.eq(membSn)
                                .and (moneyHistory.firstRegistDt. between(searchStartDate,searchEndDate)))
                .offset(rownum).limit(pageable.getPageSize())
                .fetch();

        List<MoneyHistoryDto> c = result.stream().map(MoneyHistoryDto::of).collect(Collectors.toList());

        Integer count = jpaQueryFactory.selectFrom(moneyHistory)
                .where(
                        moneyHistory
                                .money.member.membSn
                                .eq(membSn)
                                .and (moneyHistory.firstRegistDt. between(searchStartDate,searchEndDate))
                ).fetch().size();

        log.info("fff" +count.toString()); //4

        return new PageImpl<>(c,pageable, count);
    }

    @Override
    public List<MoneyHistory> findDynamicQueryAdvance(Long membSn, String keyword) {
        return null;
    }


}