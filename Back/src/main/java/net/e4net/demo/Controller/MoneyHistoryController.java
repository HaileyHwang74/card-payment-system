package net.e4net.demo.Controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.e4net.demo.DTO.MoneyHistoryDto;
import net.e4net.demo.Service.MoneyHistoryService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/moneyHistory")
public class MoneyHistoryController {
    private final MoneyHistoryService moneyHistoryService;

    @GetMapping("/{membSn}")
    public ResponseEntity<List<MoneyHistoryDto>> selectAll(@PathVariable Long membSn){

        return ResponseEntity.ok(moneyHistoryService.selectAll(membSn));
    }

    //페이지 적용된 all
    @PostMapping("/pagination/{membSn}/{rownum}")
    public ResponseEntity<Page<MoneyHistoryDto>> findAll(@PageableDefault(page =1 ,size =3)Pageable page, @PathVariable Long membSn, @PathVariable int rownum){
        String searchStartDate = "2022-12-05" + " 00:00:00";
        log.debug("timestamp => {}",searchStartDate);
        java.sql.Timestamp t1 = java.sql.Timestamp.valueOf(searchStartDate);
        log.debug("변환=>{}", t1.getClass().getName());
        log.debug(Integer.toString(page.getPageNumber()));
        log.debug("서버에서 보낼때 갯수 => {}", Integer.toString(page.getPageSize()));

        return ResponseEntity.ok(moneyHistoryService.selectPage(page,membSn, rownum ));
    }

    //검색 조건 pageable
    @PostMapping("/search/{membSn}/{rownum}")
    public ResponseEntity<Page<MoneyHistoryDto>> findByPayMeanCd (@PageableDefault(page =1 ,size =3)Pageable page, @PathVariable Long membSn, @PathVariable int rownum, @RequestParam("payMeanCd") Long payMeanCd){

        log.debug(Integer.toString(page.getPageNumber()));
        log.debug("서버에서 보낼때 갯수 => {}", Integer.toString(page.getPageSize()));

        return ResponseEntity.ok(moneyHistoryService.searchPage(page,membSn, rownum,payMeanCd));
    }
    //날짜 + 결제수단 검색
    @PostMapping("/findDatePayMeancd/{membSn}/{rownum}")
    public ResponseEntity<Page<MoneyHistoryDto>> findDatePayMeancd (@PageableDefault(page =1 ,size =3)Pageable page, @PathVariable Long membSn,
                                                           @PathVariable int rownum, @RequestParam("payMeanCd") Long payMeanCd,@RequestParam("startDate") String startDate, @RequestParam("endDate") String endDate){


        String searchStartDate = startDate + " 00:00:00";
        Timestamp t1 = Timestamp.valueOf(searchStartDate);
        log.debug("timestamp => {}",searchStartDate);

        String searchEndDate = endDate +" 23:59:59.59";
        Timestamp t2 = Timestamp.valueOf(searchEndDate);


        log.debug(Integer.toString(page.getPageNumber()));
        log.debug("서버에서 보낼때 갯수 => {}", Integer.toString(page.getPageSize()));

        return ResponseEntity.ok(moneyHistoryService.searchDatePayMeanCd(page,membSn, rownum,payMeanCd,t1,t2));
    }



    //날짜 검색
    @PostMapping("/findDate/{membSn}/{rownum}")
    public ResponseEntity<Page<MoneyHistoryDto>> findDate (@PageableDefault(page =1 ,size =3)Pageable page, @PathVariable Long membSn,
                                                           @PathVariable int rownum, @RequestParam("startDate") String startDate, @RequestParam("endDate") String endDate){


        String searchStartDate = startDate + " 00:00:00";
        Timestamp t1 = Timestamp.valueOf(searchStartDate);

        log.debug("timestamp => {}",searchStartDate);

        String searchEndDate = endDate +" 23:59:59.59" ;
        Timestamp t2 = Timestamp.valueOf(searchEndDate);




        log.debug(Integer.toString(page.getPageNumber()));
        log.debug("서버에서 보낼때 갯수 => {}", Integer.toString(page.getPageSize()));

        return ResponseEntity.ok(moneyHistoryService.searchDate(page,membSn, rownum,t1,t2));
    }




}
