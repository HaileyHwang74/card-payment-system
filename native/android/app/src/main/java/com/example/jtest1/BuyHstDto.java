package com.example.jtest1;


/********************************************** */
/**@brief : 상품구매정보 DTO
 * **/

public class BuyHstDto {

    private Long buyHstSn;
    private	Member member;
    private	Goods goods;
    private	Long goodsAmt;
    private	Long buyQtt;
    private	Long buyAmt;

    static class Member {
        Long membSn;

        public Member(){

        }

        public Long getMembSn() {
            return membSn;
        }

        public void setMembSn(Long membSn) {
            this.membSn = membSn;
        }
    }

    static class Goods {
        private	Long goodsNo;
        private	Merchant merchant;
        private	String goodsNm;
        private	String goodsModelNo;
        private Long goodsAmt;
        private Long goodsQtt;
        private Long goodsSellQtt;
        private String goodsClsDt;
        private String goodsShppCost;
        private String realFileNm;
        private String goodsImgPath;
        private String goodsDesc;

        public Long getGoodsNo() {
            return goodsNo;
        }

        public void setGoodsNo(Long goodsNo) {
            this.goodsNo = goodsNo;
        }

        public Merchant getMerchant() {
            return merchant;
        }

        public void setMerchant(Merchant merchant) {
            this.merchant = merchant;
        }

        public String getGoodsNm() {
            return goodsNm;
        }

        public void setGoodsNm(String goodsNm) {
            this.goodsNm = goodsNm;
        }

        public String getGoodsModelNo() {
            return goodsModelNo;
        }

        public void setGoodsModelNo(String goodsModelNo) {
            this.goodsModelNo = goodsModelNo;
        }

        public Long getGoodsAmt() {
            return goodsAmt;
        }

        public void setGoodsAmt(Long goodsAmt) {
            this.goodsAmt = goodsAmt;
        }

        public Long getGoodsQtt() {
            return goodsQtt;
        }

        public void setGoodsQtt(Long goodsQtt) {
            this.goodsQtt = goodsQtt;
        }

        public Long getGoodsSellQtt() {
            return goodsSellQtt;
        }

        public void setGoodsSellQtt(Long goodsSellQtt) {
            this.goodsSellQtt = goodsSellQtt;
        }

        public String getGoodsClsDt() {
            return goodsClsDt;
        }

        public void setGoodsClsDt(String goodsClsDt) {
            this.goodsClsDt = goodsClsDt;
        }

        public String getGoodsShppCost() {
            return goodsShppCost;
        }

        public void setGoodsShppCost(String goodsShppCost) {
            this.goodsShppCost = goodsShppCost;
        }

        public String getRealFileNm() {
            return realFileNm;
        }

        public void setRealFileNm(String realFileNm) {
            this.realFileNm = realFileNm;
        }

        public String getGoodsImgPath() {
            return goodsImgPath;
        }

        public void setGoodsImgPath(String goodsImgPath) {
            this.goodsImgPath = goodsImgPath;
        }

        public String getGoodsDesc() {
            return goodsDesc;
        }

        public void setGoodsDesc(String goodsDesc) {
            this.goodsDesc = goodsDesc;
        }
    }

    static class Merchant {
        private Long merchantSn;
        private Member member;
        private String merchantNm;
        private String merchantDesc;
        private String telNo;
        private String emailAddr;
        private String zipCd;
        private String zipAddr;
        private String detailAddr;

        public Long getMerchantSn() {
            return merchantSn;
        }

        public void setMerchantSn(Long merchantSn) {
            this.merchantSn = merchantSn;
        }

        public Member getMember() {
            return member;
        }

        public void setMember(Member member) {
            this.member = member;
        }

        public String getMerchantNm() {
            return merchantNm;
        }

        public void setMerchantNm(String merchantNm) {
            this.merchantNm = merchantNm;
        }

        public String getMerchantDesc() {
            return merchantDesc;
        }

        public void setMerchantDesc(String merchantDesc) {
            this.merchantDesc = merchantDesc;
        }

        public String getTelNo() {
            return telNo;
        }

        public void setTelNo(String telNo) {
            this.telNo = telNo;
        }

        public String getEmailAddr() {
            return emailAddr;
        }

        public void setEmailAddr(String emailAddr) {
            this.emailAddr = emailAddr;
        }

        public String getZipCd() {
            return zipCd;
        }

        public void setZipCd(String zipCd) {
            this.zipCd = zipCd;
        }

        public String getZipAddr() {
            return zipAddr;
        }

        public void setZipAddr(String zipAddr) {
            this.zipAddr = zipAddr;
        }

        public String getDetailAddr() {
            return detailAddr;
        }

        public void setDetailAddr(String detailAddr) {
            this.detailAddr = detailAddr;
        }
    }


    public Long getBuyHstSn() {
        return buyHstSn;
    }

    public void setBuyHstSn(Long buyHstSn) {
        this.buyHstSn = buyHstSn;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public Goods getGoods() {
        return goods;
    }

    public void setGoods(Goods goods) {
        this.goods = goods;
    }

    public Long getGoodsAmt() {
        return goodsAmt;
    }

    public void setGoodsAmt(Long goodsAmt) {
        this.goodsAmt = goodsAmt;
    }

    public Long getBuyQtt() {
        return buyQtt;
    }

    public void setBuyQtt(Long buyQtt) {
        this.buyQtt = buyQtt;
    }

    public Long getBuyAmt() {
        return buyAmt;
    }

    public void setBuyAmt(Long buyAmt) {
        this.buyAmt = buyAmt;
    }


}
