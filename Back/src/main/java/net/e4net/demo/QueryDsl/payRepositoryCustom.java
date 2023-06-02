package net.e4net.demo.QueryDsl;


import net.e4net.demo.DTO.MoneyHistoryDto;
import net.e4net.demo.Entity.MoneyHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.sql.Timestamp;
import java.util.List;

public interface payRepositoryCustom {


    //일단 paging 처리만이라도
    Page<MoneyHistoryDto> findAll(Pageable pageable, Long membSn, int rownum);

    //페이지 --검색
    Page<MoneyHistoryDto> findByMeanCd(Pageable pageable, Long membSn, int rownum, Long payMeanCd);

    //날짜 검색
    Page<MoneyHistoryDto> dateSearch(Pageable pageable, Long membSn, int rownum, Timestamp searchStartDate, Timestamp searchEndDate);

    //페이지 -- 날짜 검색 +결제수단
    Page<MoneyHistoryDto> datePayMeanCd(Pageable pageable, Long membSn, int rownum, Long payMeanCd, Timestamp searchStartDate, Timestamp searchEndDate);

    List<MoneyHistory> findDynamicQueryAdvance(Long membSn, String keyword);

}
