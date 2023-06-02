package net.e4net.demo.Controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.e4net.demo.DTO.GoodsDto;
import net.e4net.demo.DTO.MerchantDto;

import net.e4net.demo.Repository.MerchantRepository;
import net.e4net.demo.Service.MerchantService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;


@RestController
@Slf4j
@RequestMapping("/merchant")
@RequiredArgsConstructor
public class MerchantController {
    private final MerchantService merchantService;
    private final MerchantRepository merchantRepository;

    /**
     * 모든 merchant 불러오기
     *
     * @return
     */
    @GetMapping("")
    public ResponseEntity<List<MerchantDto>> getAllMerchants() {
        return ResponseEntity.ok(merchantService.getAllMerchants());
    }

    /**
     * membSn과 일치하는 merchant 하나만 불러오기
     */
    @GetMapping("/oneMerchant/{membSn}")
    public ResponseEntity<MerchantDto> getOneMerchant(@PathVariable Long membSn) {
        return ResponseEntity.ok(merchantService.getOneMerchant(membSn));
    }


    /**
     * goods 목록 불러오기
     */
    @GetMapping("/goods/{merchantSn}")
    public ResponseEntity<List<GoodsDto>> getGoods(@PathVariable Long merchantSn) {
        return ResponseEntity.ok(merchantService.getGoods(merchantSn));
    }

    /**
     * 하나의 goods 안에 있는 정보 불러오기
     *
     * @param goodsNo
     * @return
     */
    @GetMapping("/goodsAmt/{goodsNo}")
    public ResponseEntity<GoodsDto> getGoodsAmt(@PathVariable String goodsNo) {
        return ResponseEntity.ok(merchantService.getGoodsAll(goodsNo));
    }

    /**
     * 이미지 업로드 + db에 상품 정보
     */
    @PostMapping(value = "/goodsUpload", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<GoodsDto> uploadImage(@RequestPart(value = "file", required = false) MultipartFile image,
                                                @RequestPart("data") GoodsDto goodsDto) throws IOException {
        log.debug("ddssss");
        return   ResponseEntity.ok(merchantService.insertImage(image, goodsDto));
    }


}