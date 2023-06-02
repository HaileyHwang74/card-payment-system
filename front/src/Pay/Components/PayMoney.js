import React from 'react';
import axios from 'axios';

import { useNavigate } from 'react-router-dom';



function PayMoney({membSn,checkedBasketList,shipping, checkedNum}){

  console.log(membSn)

  const navigate = useNavigate();
  console.log(checkedNum)

  //결제한 물품=>장바구니에서 지우기
  const deleteBasket =()=>{
    const length = checkedNum.length;
     for(var i=0; i<length; i++){
        console.log('hello')
    axios.delete(global.ipAddress+":8888/money/basket/" + checkedNum[i])
    .then((res)=>{   
    console.log(res.data);
  })}};


  const buttonClick=()=>{
    console.log("checkedNum",checkedNum)
    const length = checkedBasketList.length;
    console.log(length)
    if(checkedBasketList.length !=0){
    for(var i=0; i<length; i++){
        if(i==0){
          //여기에서 맨 앞의 물품의 거래이력을 남길 때, 4000원이 안 되었을 때 넘어온 shipping( 2500원)이 더해짐
            const amount = checkedBasketList[i][0] * checkedBasketList[i][1] + shipping
            console.log("amount",amount);
            console.log("배송비", shipping);

            axios({
                url: global.ipAddress+":8888/money/pay/money",
                method: "post",
                headers: {"Content-Type" : "application/json",
                          "Authorization" : "Bearer" + sessionStorage.getItem("AccessToken")},
                 data:   
                 {
                  memb: parseInt(membSn),
                  goodsAmt: checkedBasketList[i][0],
                  buyQtt:  checkedBasketList[i][1],
                  goods: checkedBasketList[i][2],
                  buyAmt: amount
                }})
                .then((res)=>{
                  console.log(res.data)
                  alert('머니 결제 성공');
                  navigate('/history');
                  console.log(amount)
                  deleteBasket();
                })
                .catch((error) => {
                    console.log("error", error);
                    alert('잔고가 부족한지 확인 후, 충전해주시기 바랍니다.');
                  });
        }else{
            const amount = checkedBasketList[i][0] * checkedBasketList[i][1]
            console.log(amount);
            axios({
                url: global.ipAddress+":8888/money/pay/money",
                method: "post",
                headers: {"Content-Type" : "application/json",
                          "Authorization" : "Bearer" + sessionStorage.getItem("AccessToken")},
                 data:   
                 {
                  memb: parseInt(membSn),
                  goodsAmt: checkedBasketList[i][0],
                  buyQtt:  checkedBasketList[i][1],
                  goods: checkedBasketList[i][2],
                  buyAmt: amount
                }})
                .then((res)=>{
                  console.log(res.data)
                  navigate('/history');
                  deleteBasket();
                })
                .catch((error) => {
                    console.log("error", error);
                    alert('잔고가 부족한지 확인 후, 충전해주시기 바랍니다.');
                  });
        }
       }}
     }
 
     return (
        <>
          <button onClick={buttonClick}>선불머니 결제하기</button>
        </>
      );
}
export default PayMoney;