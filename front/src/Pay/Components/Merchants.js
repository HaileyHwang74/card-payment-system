import React ,{useEffect, useState,useCallback} from 'react';
import axios from 'axios';
function Merchant(props){
    
    const [merchantList, setMerchantLists] = useState([]);
    const[merchantSn, setMerchantSn]=useState("");

    const setMerchant = useCallback((e)=>{
        setMerchantSn(e.target.value);
    },[])


    props.setMerchantSn(merchantSn);
    console.log(merchantSn);

    useEffect(()=>{
        axios.get(global.ipAddress+":8888/merchant")
          .then((res)=>{
              console.log(res.data)
              setMerchantLists(res.data)
          })
          console.log(merchantList)
          
       },[merchantList.merchantSn])



    return (
    <>
    <select onChange={setMerchant}>
    <option>==선택==</option>
    {merchantList !== undefined && merchantList.map((merchant)=>(
    <option key={merchant.merchantSn} value={merchant.merchantSn}>{merchant.merchantNm}</option>
    )
    )}  
    </select>
    </>
    )

}
export default Merchant;