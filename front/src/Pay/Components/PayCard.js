import React from "react";
import axios from 'axios';
import { useNavigate } from 'react-router-dom';

function PayCard({count,goodsAmt,goodsNo,checkedNum}){
  
/** 이슈 : 54번째 => buyAmt 관련
 * 일단, goodsAmt가 다 합친 sum값으로 왔기 때문에, 이거는 따로 분리해서 goodsAmt(1000,3000) 을 적어주지 못 한다.
 *  => 아님 for문 똑같이 돌려서 여러개 넣어야할 듯 */


 const navigate = useNavigate();
  const membSn = sessionStorage.getItem("membSn");

  
  //결제한 물품=>장바구니에서 지우기
  const deleteBasket =()=>{
    const length = checkedNum.length;
     for(var i=0; i<length; i++){
        console.log('hello')
    axios.delete(global.ipAddress+":8888/money/basket/" + checkedNum[i])
    .then((res)=>{   
    console.log(res.data);
  })}};

  const onClickPayment = () => {
    console.log(count,goodsAmt,goodsNo)
       const { IMP } = window;
      IMP.init("imp65121421"); // 결제 데이터 정의
      if(goodsAmt >2500){
       const data = {
         pg: "danal_tpay",
         payMethod: "01",
         name: "card",
         amount: goodsAmt,
         buyer_email: "hellociice@gmail.com",
         buyer_name: "황서현",
        buyer_tel: "010-4242-4242",
         buyer_addr: "서울특별시 강남구 신사동",
         buyer_postcode: "01181"
       };
       IMP.request_pay(data, callback);
      }else{
          alert('구매하실 내역을 선택해주세요')
      }
     }
    

  const callback = (response) => {
    const {success, error_msg, imp_uid, merchant_uid,name, pay_method, paid_amount, status} = response;
    console.log(name);
    console.log(typeof (membSn),membSn);
    console.log(paid_amount)
    console.log(imp_uid)
    console.log(status)
    if (success) {
      alert(' 성공');
      axios({
        url: global.ipAddress+":8888/money/pay/card",
        method: "post",
        headers: {"Content-Type" : "application/json",
                  "Authorization" : "Bearer" + sessionStorage.getItem("AccessToken")},
        data:{
          memb: parseInt(membSn),
          goods:  parseInt(goodsNo), 
          goodsAmt: goodsAmt,
          buyQtt: count,
          buyAmt: goodsAmt   
        }})
        .then((res)=>{
          console.log(res.data)
          navigate('/history');
          deleteBasket();
        })  
    }
    else {
      alert(`결제 실패 : ${error_msg}`);
    }
  }

  return (
    <>
      <button onClick={onClickPayment}>카드 결제하기</button>
    </>
  );
}


export default PayCard;