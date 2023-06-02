import React ,{useState} from 'react';
function PayMethod({getPayMethod}){
    
    const [payMethod, setPayMethod]= useState();

    getPayMethod(payMethod);
    console.log(payMethod);

    return(
        <select onChange={(e)=>{
            setPayMethod(e.target.value);  //payMeanCd 
        }}>
        <option value="">==선택==</option>
        <option value="01">카드</option>
        <option value="02">계좌이체</option>
        <option value="03">선불머니</option>
    </select>
    )
    
}
export default PayMethod;