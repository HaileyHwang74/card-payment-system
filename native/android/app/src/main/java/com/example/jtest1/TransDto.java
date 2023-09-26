package com.example.jtest1;


/********************************************** */
/**
 * @brief : 머니충전DTO
 **/
public class TransDto {

    Long transferAmt ;
    Member member;
    String payMeanCd ;
    String transferTyCd ;





    String payTransferNo ;
    Long membMoney ;

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

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public Long getTransferAmt() {
        return transferAmt;
    }

    public void setTransferAmt(Long transferAmt) {
        this.transferAmt = transferAmt;
    }


    public String getPayMeanCd() {
        return payMeanCd;
    }

    public void setPayMeanCd(String payMeanCd) {
        this.payMeanCd = payMeanCd;
    }

    public String getTransferTyCd() {
        return transferTyCd;
    }

    public void setTransferTyCd(String transferTyCd) {
        this.transferTyCd = transferTyCd;
    }

    public String getPayTransferNo() {
        return payTransferNo;
    }

    public void setPayTransferNo(String payTransferNo) {
        this.payTransferNo = payTransferNo;
    }

    public Long getMembMoney() {
        return membMoney;
    }

    public void setMembMoney(Long membMoney) {
        this.membMoney = membMoney;
    }


}


