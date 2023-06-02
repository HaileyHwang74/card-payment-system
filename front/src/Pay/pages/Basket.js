import React, {useEffect, useState} from 'react';
import "../../Charge/css/charge.css";
import Table from 'react-bootstrap/Table';
import axios from 'axios';
import Header from '../../Header/pages/Header';

import Footer from '../../Header/pages/Footer';
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faPlus } from "@fortawesome/free-solid-svg-icons";
import PayModalFrame from '../Components/modal/PayModalFrame';

  /**
     *  foreign key로 조인되어있기 때문에, goodsNo나 goodsNm에 배열이 들어가면 mapping되지 않는다. => 방법 찾아보기
     * 따라서, 따로 쪼개서 머니 결제내역과 구매내역을 각 물품에 따라 저장하였다.
     * 배송비가 추가되는 경우(총 금액 5000원 이하) 여러 건 중에 배열[0] , 가장 첫번째 구매에 배송비를 더하는 식으로 진행하였다.
     */

  //checkBox 
function Basket(){
  
   const membSn= sessionStorage.getItem("membSn");
   console.log(typeof balance);

   //장바구니 불러오기
   const [basketList, setBasketList]=useState([]);
    const getBasket=()=>{
        axios.get(global.ipAddress+":8888/money/basket/" + membSn)
        .then((res)=>{   
        console.log(res.data);
        setBasketList(res.data);

    })
}
useEffect(()=>{
    getBasket();
  },[])

    useEffect(()=>{
      getBasket();
    },[membSn])



    console.log(`basketList => ${JSON.stringify(basketList)}`);

   const [checkedInputs, setCheckedInputs] = useState([])
   const [checkedCounts, setCheckedCounts] = useState([]);
   const [checkedNo, setCheckedNo] =useState([]);
   const [checkedNum, setCheckedNum] =useState([]);
   const [checkedBasketList, setCheckedBasketList] = useState([]);

   const changeHandler = (checked, id, id2, id3, id4,id5) => {
    if (checked) {
      const arr=[id2,id3, id4];
        //id = id2 * id3 인 듯
      setCheckedInputs([...checkedInputs, id2*id3]);  // 총 판매가
      setCheckedCounts([...checkedCounts, id3]); // 수량
      setCheckedNo([...checkedNo, id4]); // 굿즈 넘버
      setCheckedNum([...checkedNum, id5]); // 바스켓 넘버
      setCheckedBasketList([...checkedBasketList, arr]);  //id2: 금액, id3: 수량 , id4: 굿즈 넘버 

    } else {
      // 체크 해제
      setCheckedInputs(checkedInputs.filter((el) => el !== id ));
      setCheckedNum(checkedNum.filter((el) => el !== id5 ));
      setCheckedNo(checkedNo.filter((el)=> el != id4));
      setCheckedCounts(checkedCounts.filter((el)=> el !== id3))


    }
  };
  console.log("checkedInputs",checkedInputs)
  console.log("checkedbNum",checkedNum)
  console.log("chekcedNo",checkedNo, checkedNo.length);

  console.log(typeof checkedBasketList,checkedBasketList)
  console.log(`checkedBaksetList => ${JSON.stringify(checkedBasketList)}`);


//장바구니 삭제하기
const deleteBasket =()=>{
        const length = checkedNum.length;
        if(length >0){
        for(var i=0; i<length; i++){
            axios.delete(global.ipAddress+":8888/money/basket/" + checkedNum[i])
            .then((res)=>{   
            console.log(res.data);
            getBasket();
            })
            //삭제 한 애들은 체크 해제를 함으로서 총배송비에 포함이 안 되게 함
            setCheckedInputs(checkedInputs.filter((el) => el !== checkedInputs[i]));
            setCheckedNum(checkedNum.filter((el) => el !== checkedNum[i] ));
            setCheckedNo(checkedNo.filter((el)=> el != checkedNo[i]));
            setCheckedCounts(checkedCounts.filter((el)=> el !== checkedCounts))

        }
    }
    //삭제 시 총판매가 0으로 하려고 조건 걸어놓음
    setSum(0);
}





//총판매가
const [sum, setSum] =useState(0);
function getSum(){
    const length = checkedInputs.length;
    console.log(length)
    let sum2 =0;
        for(var i=0; i<length; i++){
            sum2 +=checkedInputs[i];
        }
        console.log(sum2)

        setSum(sum2);
    } 
    useEffect(()=>{
        getSum();
       },[checkedInputs, checkedNum.count])

       console.log(sum)

// 배송비
const [shipping, setShipping]=useState(0);
function setShippingFee(){
    console.log(sum);
        if(sum >= 4000){
            setShipping(0);
        }else{
            setShipping(2500);
        }

}
   useEffect(()=>{
   setShippingFee();
   },[sum])

console.log(shipping)

//총수량
const [count , setCount] =useState(0);
function getCount(){
    const length = checkedCounts.length;
    console.log(length)
    let count2 =0;
        for(var i=0; i<length; i++){
            count2 +=checkedCounts[i];
        }
        console.log(count2)
        setCount(count2);
    } 
    useEffect(()=>{
        getCount();
        },[checkedCounts]);

        console.log(count);


   //총 굿즈넘버
   const [num , setNum] =useState(0);
   function getNum(){
       const length = checkedNo.length;
       console.log(length)
       let num2 =0;
       if(length >0){
        for(var i=0; i<length; i++){
            num2 +=checkedNo[i];
           }
           console.log(num2)
           setNum(num2.substring(0,16));
       }
       }

       useEffect(()=>{
        getNum();
           },[checkedNo]);
           console.log("num222",num); 
           console.log(num);
           
           console.log(num);
   
   return (
       <>
       <Header/>
       <div className="charge_box">
           <div className="charge_container">
               <div className="charge_wrap"style={{marginTop:"70px" , marginLeft:"30px", marginRight:"30px"}}>
               <div style={{display: "flex",justifyContent:"flex-start", marginBottom:"20px" ,fontWeight:"bold" }} >
                  | 장바구니
                  </div>
                   <Table style={{border: "3px solid gray"}}>
                       <thead>
                       <tr style={{backgroundColor: "lightgray", opacity: "0.5"}}>
                           <th><input type='checkbox' name="amount" value=""/></th>
                           <th>상품제목</th>
                           <th>수량</th>
                           <th>가격</th>
                           
                       </tr>
                       </thead>
                       <tbody>
                    {basketList.map((basket,idx)=>(
                        <tr>
                            <td>
                                <div>
                                     <input type='checkbox' key= 
                                     {idx} name={basket.basketNm} value={basket.basketAmt} onChange={(e)=>{
                                      changeHandler(e.currentTarget.checked, basket.basketAmt*basket.basketCount, basket.basketAmt,
                                        basket.basketCount, basket.basketGoodsNo, basket.basketNo)
                                      }}/> 
                       </div>
                            </td>
                            <td>
                                {basket.basketNm} 
                                
                            </td>
                            <td>
                            <div style={{display: "flex",justifyContent: "center"}}>
                                <div style={{display: "flex"}}>
                                    {basket.basketCount}
                                </div>
                            </div>
                            </td>
                            <td>
                                {basket.basketAmt*basket.basketCount}
                            </td>
                        </tr>
                    ))}
                   </tbody>
                  </Table>
                  <div style={{marginBottom:"10px", display:"flex", justifyContent:"flex-end" }}>
                     <button onClick={deleteBasket}>삭제</button> 
                  </div>
                  <Table style={{border: "3px solid gray" }}>
                    <thead>
                       <tr style={{backgroundColor: "lightgray", opacity: "0.5"}}>
                           <th>총 판매가</th>
                           <th></th>
                           <th>배송비</th>
                       </tr>
                    </thead>
                    <tbody>
                        <tr>
                            <td>
                           <div>{sum}</div>
                            </td>
                                <td>
                                    <div>
                                        <FontAwesomeIcon icon={faPlus} />
                                    </div>
                                </td>
                            <td>
                               <div >{shipping}</div>
                            </td>
                        </tr>    
                    </tbody>
                     </Table>
                     <div style={{display: "flex", justifyContent:"flex-end", marginBottom:"20px"}}>
                     <div>
                        총 결제예상금액 : 
                     </div>

                     { sum+shipping ==2500 ? "0원"   :<div style={{marginLeft: "10px"}}>
                      {sum +shipping}원
                     </div> }
                   </div>

                   <PayModalFrame count={count} goodsAmt={sum +shipping} goodsNo={num} 
                   membSn={membSn} checkedBasketList={checkedBasketList} shipping={shipping} checkedNum= {checkedNum}
                   deleteBasket={deleteBasket}/>
               </div>
           </div>
       </div>
       <Footer/>
       </>
   );

}
export default Basket;
