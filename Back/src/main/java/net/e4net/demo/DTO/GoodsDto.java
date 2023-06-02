package net.e4net.demo.DTO;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import net.e4net.demo.Entity.Goods;
import net.e4net.demo.Entity.Merchant;

@Getter
@Builder
//@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GoodsDto {
        private String goodsNo;
        private Long merchantSn;
        //private String merchantSn;
        private String goodsNm; // 상품명  nn
        private String goodsModelNo; // 상품모델번호
        private Long goodsAmt; // 상품 금액
        private Long goodsQtt; // 상품 수량
        private Long goodsSellQtt; // 판매 수량
        private String goodsClsDt; // 판매종료일/매진 일자 이것을 고려해서 리스트를 select 해야 한다.
        private Long goodsShopCost; // 배송비용 0인경우 무료 배송
        private String realFileNm; // 실제파일명  실제 업로드 시 파일명
        private String goodsImgPath; // 상품이미지 경로 rename 된 파일
        private String goodsDesc; // 상품설명


        public static Goods toGoods(GoodsDto dto, Merchant merchant) {
            return Goods.builder()
                    .goodsNo(dto.getGoodsNo())
                    .merchant(merchant)
                    .goodsNm(dto.getGoodsNm())
                    .goodsModelNo(dto.getGoodsModelNo())
                    .goodsAmt(dto.getGoodsAmt())
                    .goodsQtt(dto.getGoodsQtt())
                    .goodsSellQtt(dto.getGoodsSellQtt())
                    .goodsClsDt(dto.getGoodsClsDt())
                    .goodsShopCost(dto.getGoodsShopCost())
                    .realFileNm(dto.getRealFileNm())
                    .goodsImgPath(dto.getGoodsImgPath())
                    .goodsDesc(dto.getGoodsDesc())
                    .build();
        }


        public static GoodsDto of(Goods goods) {
            return GoodsDto.builder()
                    .goodsNo(goods.getGoodsNo())
                    .merchantSn(goods.getMerchant().getMerchantSn())
                    .goodsNm(goods.getGoodsNm())
                    .goodsModelNo(goods.getGoodsModelNo())
                    .goodsAmt(goods.getGoodsAmt())
                    .goodsQtt(goods.getGoodsQtt())
                    .goodsSellQtt(goods.getGoodsSellQtt())
                    .goodsClsDt(goods.getGoodsClsDt())
                    .goodsShopCost(goods.getGoodsShopCost())
                    .realFileNm(goods.getRealFileNm())
                    .goodsImgPath(goods.getGoodsImgPath())
                    .goodsDesc(goods.getGoodsDesc())
                    .build();
        }
    }
