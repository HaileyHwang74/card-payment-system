import React, {useMemo,useState,useEffect} from 'react';
import "../css/charge.css";
import Table from 'react-bootstrap/Table';
import '../../Sidebar/css/sidebar.css';
import axios from 'axios';
import Payment from '../../Pay/Components/Payment';
import Header from '../../Header/pages/Header';
import Footer from '../../Header/pages/Footer';
import AdModalFrame from '../../Pay/Components/modal/AdModalFrame';



function Charge(props) {

    const membSn= sessionStorage.getItem("membSn");
 
    const [balance, setBalance] = useState(0);

    const [charge, setCharge] = useState(0);

    const changeCharge = (e) => {
    setCharge((e.target.value));
    }
    

    useEffect(()=>{
        showCharge();
    },[balance]); 
   
    //잔액 보여주기
    const showCharge = ()=>
         axios.get(global.ipAddress+":8888/money/checkBalance/" +membSn)
        .then((res)=>{
            console.log(res.data)
            setBalance(res.data)
        }
        );       


    const result = balance + parseInt(charge);
    console.log(typeof result,result);

    return (
        <>
        <Header/>
        <AdModalFrame/>
        <div className="charge_box">
            <div className="charge_container">
                <div className="charge_wrap" style={{marginTop:"160px" , marginLeft:"30px", marginRight:"30px"}}>
                    <Table style={{border: "3px solid gray"}}>
                        <thead>
                        <tr style={{backgroundColor: "lightgray", opacity: "0.5"}}>
                            <th>#</th>
                            <th>금액</th>
                        </tr>
                        </thead>
                        <tbody>
                        <tr>
                            <td>머니잔액</td>
                            <td>
                                <div>{balance}원</div>
                            </td>
                        </tr>
                        <tr>
                            <td>머니충전액</td>
                            <td>
                                <input  type="text" value={charge} onChange={changeCharge}
                                       style={{border: "none",width:"50px",fontSize:"17px"}}/>원
                            </td>
                        </tr>
                        <tr>
                            <td>충전결과예정액</td>
                            <td>
                                <div style={{border:"50px"}}>
                                {result}원
                                </div>
                            </td>
                        </tr>
                        </tbody>
                    </Table>
                  <Payment payMethod="01" charge={charge} result={result} name="01"/>  {/* 충전(01), 카드(01) name = 충전이므로 무조건01*/ }
                </div>
            </div>

        </div>
        <Footer/>
        </>
    );

}

export default Charge;