import React, { useEffect, useState } from 'react';
import { useNavigate,useLocation } from "react-router-dom";
import axios from 'axios';


const Payment=({charge,result,name,payMethod})=>{


  const membSn = sessionStorage.getItem("membSn");
  const navigate = useNavigate();


  console.log(result);
  console.log(charge);
  console.log(payMethod);
  
const onClickPayment = () => {
  const { IMP } = window;
  IMP.init("imp65121421"); // 결제 데이터 정의
  const data = {
    pg: "danal_tpay",
    payMethod: payMethod,
    name: name,
    amount: charge,
    buyer_email: "hellociice@gmail.com",
    buyer_name: "황서현",
    buyer_tel: "010-8940-8679",
    buyer_addr: "서울특별시 강남구 신사동",
    buyer_postcode: "01181"
    // m_redirect_url : '{결제 완료 후 리디렉션 될 URL}' // 예: https://www.my-service.com/payments/complete

  };
  IMP.request_pay(data, callback);
}

  const callback = (response) => {
    const {success, error_msg, imp_uid, merchant_uid,name, pay_method, paid_amount, status} = response;
    console.log(result); 
    console.log(name);
    console.log(typeof (membSn),membSn);
    console.log(paid_amount)
    console.log(payMethod)
    console.log(imp_uid)
    if (success) {
      alert(' 성공');

   axios({
      url: global.ipAddress+":8888/money/chargeMoney",
      method: "post",
      headers: {"Content-Type" : "application/json"},
      data:{
        moneySn: parseInt(membSn),
        moneyBlce: result,      //남은금액
        transferType:name,       // 04: 충전, 05: 사용 
        transferAmt:paid_amount,    //쓴 금액
        payMeanCd: payMethod,   //01: 카드, 02: 계좌이체, 03: 머니사용
        imp_uid: imp_uid,
      }})
      .then((res)=>{
        console.log(res.data)
        navigate('/pay')
      }) 
    }
    else {
      alert(`결제 실패 : ${error_msg}`);
    }
  }

  return (
    <>
      <button onClick={onClickPayment}>결제하기</button>
      {/* <button>{membSn}</button> */}
    </> 
   );
}
  
  export default Payment;