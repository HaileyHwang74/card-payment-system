import React, {memo ,useState, useEffect} from "react";
import "../Charge/css/charge.css";
import 'bootstrap/dist/css/bootstrap.min.css';
import Table from 'react-bootstrap/Table';
import Header from "../Header/pages/Header";
import Footer from "../Header/pages/Footer";

import {useNavigate} from "react-router-dom";
import axios from "axios";
import UploadImage from "./UploadImage";


//상품 등록 페이지

function GoodsSignUp() {

    const membSn = sessionStorage.getItem("membSn");
    const membCls = sessionStorage.getItem("membCls");

    const navigate = useNavigate();

    const [merchantNm, setMerchantNm] = useState("");
    const [merchantSn, setMerchantSn]=useState("");
    const [file,setFile] = useState(null);

    //상품목록 
    const [goods, setGoods] = useState({
        // merchant : "",
        goodsNm : "",
        // goodsModelNo : "",    //이거 db에서 merchatnSn 가지고 와서 처리해야하나
        goodsAmt : "",
        goodsQtt: "",
        // goodsSellQtt : "",     //이거 일단 0으로 픽스( back단)
        // goodsClsDt: "",        //이것도 백단에서 넣어야 될 듯 (일단 pass)
        // goodsImgPath: "",
        goodsDesc: ""
    })

    //1.가맹점 정보 불러오기 => membSn으로 찾아가지고 불러오기
    const getMerchantName = () =>{
        axios.get(global.ipAddress +":8888/merchant/oneMerchant/" + membSn)
        .then((res) => {
            console.log(res.data)
            setMerchantNm(res.data.merchantNm);
            setMerchantSn(res.data.merchantSn) 
        })
    }

    console.log(merchantNm)

    useEffect(()=>{
        getMerchantName();
        // setGoods({
        //     ...goods,
        //     merchant : merchantNm
        // })
       },[membSn])



    //상품 관련 onChange
    function ChangeValue(e) {
        setGoods({
            ...goods,
           [e.target.name] : e.target.value,
            
        });
      }   

      console.log(goods)
  

    //상품등록
    const goodsSignUp =()=>{
        console.log(merchantNm)
        setGoods({
            ...goods,
            merchant : merchantNm,
            merchantSn: merchantSn
        })
        console.log(goods)

        let formData = new FormData();
        console.log(`file => ${file}`)
        formData.append("file", file);
        formData.append("data", new Blob([JSON.stringify(goods)], {type: "application/json"}))
        axios.post(global.ipAddress + ":8888/merchant/goodsUpload", formData, 
        {
            headers:{
                "Content-Type": "multipart/form-data",
                // "Authorization": `Bearer ${sessionStorage.getItem("accessToken")}`
            }
        })
        .then((res) =>{
            console.log(res.data)
            console.log("상품등록")
            //성공하면 pay 로 이동
            navigate('/pay')
        })    .catch((error) => {
            console.log("상품등록 실패");
            console.log(goods)
          console.log("error", error);
        });
    }



    return (
        <>
        <Header/>
        <div className="charge_box">
            <div className="charge_container" >
                <div className="charge_wrap"style={{ width: "70px", marginTop:"100px"}}>
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
                              {merchantNm}
                           </td>
                        </tr>
                        <tr>
                            <td>상품이름</td>
                            <td >
                            <input type="text" name="goodsNm" value={goods.goodsNm} onChange={ChangeValue}
                                        placeholder="상품을 등록하세요"/>
                            </td>
                         </tr>
                       
                         <tr>
                            <td >상품수량</td>
                         <td >
                         <input type="text" name="goodsQtt" value={goods.goodsQtt} onChange={ChangeValue}
                                      placeholder="상품수량을 등록하세요"/> 개
                            </td>
                         </tr>
                         <tr>
                             <td>상품가격</td>
                             <td>
                             <input type="text" name="goodsAmt" value={goods.goodsAmt} onChange={ChangeValue}
                                        placeholder="상품가격을 등록하세요"/> 원
                             </td>
                         </tr>
                       <tr>
                             <td>상품이미지등록</td>
                             <td style={{ textAlign : "center"}}>
                                 <UploadImage  setFile={setFile}/>
                             </td>
                         </tr>
                     <tr>
                        <td>상품설명</td>
                         <td>
                         {/* textarea */}
                             <input type="text" name="goodsDesc" value={goods.goodsDesc} onChange={ChangeValue}
                                        placeholder="상품설명을 등록하세요"/>
                            </td>
                        </tr>
                        </tbody>  
                    </Table>
                    
                </div>
                <button className="goods_signup" type="button"  onClick={goodsSignUp}>상품등록하기</button>
            </div>
        </div>
        {/* <Footer /> */}
        </>
    );

}

export default memo(GoodsSignUp);