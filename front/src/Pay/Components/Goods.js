import React ,{useEffect, useState, useCallback} from 'react';
import axios from 'axios';
function Goods({merchantSn,setGoodsAmt,setGoodsNo,setGoodsNm}){
    console.log(typeof (merchantSn), merchantSn);
    const [goodList, setGoodList] = useState([]);

    // goodsAmt를 가져오기 위한 axios 에 pathvariable로 들어가기 때문에 
    // 또 부모 컴포넌트에 이미 goodsNo 가 있으므로 goodsNum으로 하나 만들어줌
    const [goodsNum, setGoodsNum] = useState();


    const setOneGood = useCallback((e)=>{
        setGoodsNo(e.target.value);
        setGoodsNum(e.target.value);
    },[])


    useEffect(()=>{
        axios.get(global.ipAddress+":8888/merchant/goods/" + merchantSn)
          .then((res)=>{
              console.log(res.data)
              setGoodList(res.data)
          })
          console.log(goodList)
       },[merchantSn])

    
       //goodsAmt를 위한 axios
    axios.get(global.ipAddress+":8888/merchant/goodsAmt/" + goodsNum)
       .then((res)=>{
        console.log(res.data);
        setGoodsAmt(res.data.goodsAmt);
        setGoodsNm(res.data.goodsNm);
       })


    return (
        <>
        <select onChange={setOneGood}>
        <option value="">==선택==</option>
        {goodList.map((good,idx)=>(
        <>
        <option key={idx} value={good.goodsNo}>{good.goodsNm}</option>
        </>
        )
        )}
        </select>
        </>
)

}
export default Goods;