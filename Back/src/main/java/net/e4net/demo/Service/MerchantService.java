package net.e4net.demo.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.e4net.demo.DTO.BasketDto;
import net.e4net.demo.DTO.GoodsDto;
import net.e4net.demo.DTO.MerchantDto;
import net.e4net.demo.Entity.Basket;
import net.e4net.demo.Entity.Goods;
import net.e4net.demo.Entity.Merchant;
import net.e4net.demo.Repository.GoodsRepository;
import net.e4net.demo.Repository.MerchantRepository;
import org.hibernate.graph.spi.GraphHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
@Slf4j
@RequiredArgsConstructor
public class MerchantService {

    private final MerchantRepository merchantRepository;
    private final GoodsRepository goodsRepository;

    /**
     * 가맹점 목록 조회
     * */
    public List<MerchantDto> getAllMerchants() {
        List<Merchant> merchants = merchantRepository.findAll();
        List<MerchantDto> merchantDto = new ArrayList<>();
        for (Merchant e : merchants) {
            merchantDto.add(MerchantDto.of(e));
        }
        return merchantDto;
    }

    /**
     * 가맹점 하나 조회
     */
    public MerchantDto getOneMerchant(Long membSn){
        Optional<Merchant> merchant = merchantRepository.findById(membSn);
//        String merchantName = merchant.get().getMerchantNm();
        return MerchantDto.of(merchant.get());
    }


    /**
     * 상품 목록 조회
     *
     * @param merchantSn
     * @return
     */
    public List<GoodsDto> getGoods(Long merchantSn) {
        Optional<Merchant> merchant = merchantRepository.findById(merchantSn);
        List<Goods> goods = goodsRepository.findByMerchant(merchant.orElse(null));
        List<GoodsDto> goodsDtos = new ArrayList<>();
        for (Goods e : goods) {
            goodsDtos.add(GoodsDto.of(e));
        }
        return goodsDtos;
    }

    /**
     * 상품 상세 조회  =>dto 로 바꾸기, 배송비도 필요함
     *
     * @param goodsNo
     * @return
     */
//    public Long getGoodsAmt(String goodsNo) {
//        Optional<Goods> goods = goodsRepository.findById(goodsNo);
//        return goods.get().getGoodsAmt();
//    }

    public GoodsDto getGoodsAll(String goodsNo) {
        Optional<Goods> goods = goodsRepository.findById(goodsNo);

        return GoodsDto.of(goods.get());
    }


    /**
     * 이미지 서버에 업로드
     */
    @Transactional
    public GoodsDto insertImage(MultipartFile image, GoodsDto goodsDto) throws  IOException {
        String originalName = image.getOriginalFilename();

        String absolutePath = new File("src/main/resources/static/images/").getAbsolutePath();
        if (!image.isEmpty()) {
                String contentType = image.getContentType();
            String originalImageExtension = null;

                // 확장자 명이 없으면 종료
                if (!StringUtils.hasText(contentType)) {
                    log.debug("없음");
                } else {
                    if (contentType.contains("image/jpeg")) {
                        originalImageExtension = ".jpg";
                    } else if (contentType.contains("image/png")) {
                        originalImageExtension = ".png";
                    } else if (contentType.contains("image/gif")) {
                        originalImageExtension = ".gif";
                    }
                }

                log.debug("image 안에 뭐 들었나...=>{}",originalName );
                log.debug("original =>{}",originalImageExtension);
                //UUID로 랜덤으로 이름 생성
                String newImageName = UUID.randomUUID().toString() + originalImageExtension;
                log.debug("newImageName =>{}", newImageName);


                //파일 전송하기
                File file = new File(absolutePath + "/" + newImageName);
                if (!file.exists()) {
                    file.mkdirs();
                }
                image.transferTo(file);

                //goods db에 save

            //originalName  => real_file_NM
            //newImageName => goods_img_path

            Goods goods = Goods.builder().goodsNm(goodsDto.getGoodsNm())
                    .goodsQtt(goodsDto.getGoodsQtt())
                    .merchant(Merchant.builder().merchantSn(goodsDto.getMerchantSn()).build())
                    .goodsNm(goodsDto.getGoodsNm())
                    .goodsSellQtt(goodsDto.getGoodsSellQtt())
                    .goodsAmt(goodsDto.getGoodsAmt())
                    .goodsDesc(goodsDto.getGoodsDesc())
                    .goodsImgPath(newImageName)
                    .realFileNm(originalName)
            .build();

            log.debug("goods=>{}", goods.getMerchant().getMerchantSn());

            goodsRepository.save(goods);
            }
        return goodsDto;
    }

    /**
     * 이미지 불러오기
     */



}
