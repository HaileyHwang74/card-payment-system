package net.e4net.demo.Controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.e4net.demo.DTO.BasketDto;
import net.e4net.demo.DTO.BuyHistoryDto;
import net.e4net.demo.DTO.MoneyDto;
import net.e4net.demo.Entity.Basket;
import net.e4net.demo.Entity.BuyHistory;
import net.e4net.demo.Service.MoneyService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("money")
public class MoneyController {

    private final MoneyService moneyService;


    @GetMapping("/checkBalance/{membSn}")
    public ResponseEntity<Long> checkBalance(@PathVariable Long membSn) {
        log.debug("membSn =>{}", membSn);
        return ResponseEntity.ok(moneyService.checkBalance(membSn));
    }

    @PostMapping("/chargeMoney")
    public ResponseEntity<MoneyDto> chargeMoney(@RequestBody MoneyDto dto) {
        log.debug("{}만큼 충전하였습니다.", dto.getMoneyBlce());
        return ResponseEntity.ok(moneyService.chargeMoney(dto));
    }
    //나중에 위에 money 없애기
    @PostMapping("/pay/card")
    public ResponseEntity<BuyHistory> UseCard(@RequestBody BuyHistoryDto dto) {
        return ResponseEntity.ok(moneyService.useCard(dto));
    }

    @PostMapping("/pay/money")
    public ResponseEntity<MoneyDto> UseMoney(@RequestBody BuyHistoryDto dto) {
        return ResponseEntity.ok(moneyService.useMoney(dto));
    }

    //장바구니에 넣기
    @PostMapping("/basket")
    public ResponseEntity<Basket> setBasket(@RequestBody BasketDto dto) {
        return ResponseEntity.ok(moneyService.setBasket(dto));
    }
    //장바구니 가져오기
    @GetMapping("/basket/{membSn}")
    public ResponseEntity<List<BasketDto>> getBasketAll(@PathVariable Long membSn){
        log.debug("이거 넘어오나=>{}",membSn);
        return ResponseEntity.ok(moneyService.getBasketAll(membSn));
    }
    //장바구니 삭제하기
    @DeleteMapping("/basket/{basketNo}")
    public void deleteBasket(@PathVariable Long basketNo){
        log.debug("basketNo,{}", basketNo);
        moneyService.deleteBasket(basketNo);
    }

}


