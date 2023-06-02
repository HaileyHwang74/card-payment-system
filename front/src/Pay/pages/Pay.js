import React, {useEffect, useState} from 'react';
import "../../Charge/css/charge.css";
import Table from 'react-bootstrap/Table';
import '../../Sidebar/css/sidebar.css'
import axios from 'axios';
import Goods from '../Components/Goods';
import Merchants from '../Components/Merchants';
import ModalFrame from '../Components/modal/ModalFrame';
import Header from '../../Header/pages/Header';
import Footer from '../../Header/pages/Footer';


function Pay(props) {

    const membSn= sessionStorage.getItem("membSn");
    const [balance, setBalance] = useState(0);
    console.log(typeof balance)

    //자식컴포넌트(Merchants)에서 받아오기 
    const [merchantSn, setMerchantSn]=useState("");
    //자식컴포넌트(Goods)에서 받아오기 
    const [goodsAmt, setGoodsAmt]= useState();
    const [goodsNo, setGoodsNo]= useState();
    const [goodsNm,setGoodsNm]= useState();
    const [count, setCount] = useState(1);

    const plusCount = () => {
        if (count < 5) {
            setCount(count + 1);
        } else {
            alert('선택 가능한 수량은 5개입니다. 그 이상은 고객센터로 문의해주세요')
        }
    }

    const MinusCount = () => {
        //0으로 되지 않기 위해 1로 해놓음
        if (count > 1 ) {
            setCount(count - 1);
        }
    }
    

    useEffect(()=>{
        showCharge();
    },[balance]) 

    const showCharge =()=>{
        axios.get("http://localhost:8888/money/checkBalance/" +membSn)
        .then((res)=>{
            console.log(res.data)
            setBalance(res.data)
        }
        )
    }
    let payMoney = parseInt(goodsAmt * count);
    if(isNaN(payMoney)){
        payMoney = 0;
    }

    console.log(goodsNm);
    console.log(count);
    console.log(goodsAmt);
    
    return (
        <>
        <Header/>
        <div className="charge_box">
            <div className="charge_container">
                <div className="charge_wrap"style={{marginTop:"160px" , marginLeft:"30px", marginRight:"30px"}}>
                    <Table style={{border: "3px solid gray"}}>
                        <thead>
                        <tr style={{backgroundColor: "lightgray", opacity: "0.5"}}>
                            <th>#</th>
                            <th>내용</th>
                        </tr>
                        </thead>
                        <tbody>
                        <tr>
                            <td>가맹점</td>
                            <td>
                              <Merchants setMerchantSn={setMerchantSn}/>
                            </td>
                        </tr>
                        <tr>
                            <td>구매물품</td>
                            <td style={{display: "flex",justifyContent: "center"}}>
                                <Goods merchantSn={merchantSn} setGoodsAmt={setGoodsAmt} setGoodsNo ={setGoodsNo} setGoodsNm={setGoodsNm}/> 
                                <div style={{display: "flex"}}>
                                <button onClick={plusCount}
                                        style={{fontSize: "12px", height: "25px", marginRight: "10px" ,alignContent:"center"}}>+</button>
                                <div>{count}</div>
                                <button onClick={MinusCount} style={{fontSize: "12px", height: "25px"}}>-</button>
                                </div>
                            </td>
                        </tr>
                       
                        <tr>
                            <td>결제금액</td>
                            <td>
                                <div>{payMoney}원</div>
                            </td>
                        </tr>
                        </tbody>  
                    </Table>
                  <ModalFrame basketAmt ={goodsAmt} basketCount={count} basketNm = {goodsNm} basketGoodsNo={goodsNo} />
                </div>
            </div>
        </div>
        <Footer />
        </>

    );

}

export default Pay;