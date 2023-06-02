import React, {memo, useCallback, useEffect, useState} from "react";
import "../css/join.css";
import 'bootstrap/dist/css/bootstrap.min.css';
import Table from 'react-bootstrap/Table';
import {Form} from "react-bootstrap";

import PopupDom from "../Components/PopupDom";
import PopupPostCode from "../Components/PopupPostCode";
import {useNavigate} from "react-router-dom";
import axios from "axios";

function JoinUpdate(){

    const navigate = useNavigate();

    /** useState 안의 default 값 안 주면 안 되나..?*/
    const [val, setVal] = useState({
        membId:"",
        membPwd:"",
        membNm: "",
        detailAddr: "",
        membCls:"",
        membStatusCdNum:"1"
    })

    const ChangeValue = (e) => {
        console.log(e.target.name, e.target.value);
        setVal({
            ...val, [e.target.name]: e.target.value,
        });
            console.log(val.membId);
    }

    /**비밀번호 확인 */
    const [checkPassword, setCheckPassword] = useState("");

    /**문제*/
    //콘솔로그 여러번 찍히는 문제//
    // 비밀번호 한 번만 쳐도 바로 같지 않다고 뜨는 것//
    const checkPw = useCallback((e) => {
        setCheckPassword(e.target.value);
        console.log(checkPassword);
    }, []);


    /**우편번호 팝업창*/
    
    // 팝업창 상태 관리
    const [isPopupOpen, setIsPopupOpen] = useState(false)
    // 팝업창 열기
    const openPostCode = () => {
        setIsPopupOpen(true)
        console.log("팝업창 오픈")
    }
    console.log(isPopupOpen)

    // 팝업창 닫기
    const closePostCode = () => {
        setIsPopupOpen(false)
    }

    function getData(zoneCode, address) {
        console.log(zoneCode, address);
        console.log(typeof address);
        setVal({
            ...val, zipCd: zoneCode, zipAddr: address,
        })
    }


    /** 핸드폰번호 입력 */
    // 핸드폰 앞 번호 //
    const [countryNo, setCountryNo] = useState("");

    const changeCountryNo=useCallback((e) =>{
        e.preventDefault();
        setCountryNo(e.target.value);
    },[])

    const [phone1, setPhone1] = useState("");
    const secondPhone =useCallback((e) =>{
        e.preventDefault();
        setPhone1(e.target.value);
    },[])

    /**핸드폰 뒷번호 */
    const [phone2, setPhone2] = useState("");
    const lastPhone =useCallback((e) =>{
        e.preventDefault();
        setPhone2(e.target.value);
    },[])
    
    /** 최종 핸드폰 번호*/
    const mobileNo = countryNo + phone1 + phone2;
    console.log(typeof mobileNo, mobileNo); //ex) 01089408679


    useEffect((e) => {
        setVal({
            ...val,
            mobileNo:mobileNo,
        })
        console.log( typeof mobileNo, mobileNo)
    }, [mobileNo]);
        

   /**email 입력 */
    const [email1, setEmail1] = useState("");
    /**email 앞의 메인 주소 */
    const firstEmail = useCallback((e) => {
        e.preventDefault();
        setEmail1(e.target.value);
    }, []);

    /** email 뒤 주소 */
    const [email2, setEmail2] = useState("");
    const secondEmail = useCallback((e) => {
        setEmail2(e.target.value); // ex) @naver.com
    }, []);

    /** 최종 email 주소*/
    const emailAddr = email1 + email2;
    console.log(emailAddr); //ex) hello@naver.com


    useEffect(() => {
            setVal({
                ...val,
                emailAddr: emailAddr,
            })
        }
        , [emailAddr]);

    /**회원가입 , member 추가*/
    const addMember = (e) => {
        e.preventDefault();
        axios.post("http://localhost:8888/auth/signup",val)
            .then((res) => {
                if(res.data==null){
                    alert("회원가입에 실패하였습니다. 빈칸없이 작성해주시기 바랍니다.");
                    console.log('추가실패');
                 
                }else{
                    alert("회원가입을 축하드립니다! 로그인페이지로 이동합니다");
                    console.log("추가성공");
                    console.log(res.data);
                    navigate("/", {state:  {membId: val.membId}});
                }
                }
            )
    }


 /*    const addCertificationNm =(e)=>{
        e.preventDefault();
        console.log(mobileNo)
         axios.post("http://localhost:8888/send-one", mobileNo) 
        .then((res)=>{
            alert('인증번호가 전송되었습니다.');
            console.log(mobileNo);
            console.log(res.data);
            setConfirmMobile(res.data);
        })
    }  */
    
    const [confirmMobile, setConfirmMobile] =useState("");

    //axios 말고 fetch 쓰자 . json말고 string을 받아야하니까 
    const addCertificationNm=()=>{
        console.log(mobileNo)
        fetch("http://localhost:8888/send-one",{
            method: "POST",
            headers: {
                "Content-Type": "application/json",
                charset: "utf-8",
            },
            body: mobileNo,
        }) .then((res) => res.json())
        .then((res) => {
            alert('인증번호가 전송되었습니다.');
            console.log(mobileNo);
            console.log(res);
            setConfirmMobile(res);
            })
    } 


    /**인증번호 확인 */
    const [certifyNo, setCertifyNo] =useState("");
    const changeCerticyNo=(e)=>{
        setCertifyNo(e.target.value);
}

    /**인증번호 확인 로직 */
    const validateMobile=()=>{
        if(certifyNo != confirmMobile){
            alert('인증번호를 재입력해주시기 바랍니다.')
            setCertifyNo("");
        }else{
            alert('핸드폰번호 인증이 성공했습니다.');
        }

    }


    /**Role 정하기(seller, user) */
    const checkOnlyOne = (checkThis) => {
        const checkboxes = document.getElementsByName('membCls')
        for (let i = 0; i < checkboxes.length; i++) {
          if (checkboxes[i] !== checkThis) {
            checkboxes[i].checked = false
          }else{
            setVal({
                ...val,
                membCls: checkThis.value
            })
          }
        }
      }
      console.log(val);

    

  /**중복체크*/
    const validateDuplicate=()=>{
        axios.get("http://localhost:8888/auth/validateCheck/" + val.membId)
            .then((res) =>{
                 if(res.data ==0){
                    alert("아이디" + val.membId+ "를 사용하실 수 있습니다.");
                 }
                 else{
                    alert("이미 존재하는 아이디입니다.");
                 }
                console.log(typeof res,res)
            })
            .catch(function(error){
                if(error.response.status <500){
                    alert('아이디가 중복됩니다.');
                }
            })
    }

    


    return (
    <div className="join_container">
        <div className="join_wrap">
            <div className="member_info">&gt;회원정보</div>
            <div className="join_border">
                <Form className="joinForm" onSubmit={addMember}>
                    <Table className="join_table"size="sm" >
                        <tbody>
                        <tr>
                            <td>아이디(*)</td>
                            <td><input type="text" name="membId" value={val.membId} onChange={ChangeValue}
                                       autoComplete="uesrname"
                                       placeholder="아이디를 입력하세요"/>
                                <button className="join_button" type="button" onClick={validateDuplicate}>중복확인</button>
                            </td>
                        </tr>
                        <tr>
                            <td>비밀번호(*)</td>
                            <td><input type="password" name="membPwd" value={val.membPwd}
                                       autoComplete="new-password"
                                       onChange={ChangeValue}
                                       maxLength="12" placeholder="비밀번호를 입력하세요"/></td>
                        </tr>
                        <tr>
                            <td>비밀번호 확인(*)</td>
                            <td><input type="password" name="checkPassword" value={checkPassword}
                                       autoComplete="new-password"
                                       onChange={checkPw}
                                       maxLength="12" placeholder="비밀번호를 입력하세요"/></td>
                        </tr>
                        <tr>
                            <td></td>
                            <td>{val.membPwd !== checkPassword && checkPassword !== "" &&
                                <p style={{color: "red"}}>비밀번호가 일치하지 않습니다.</p>}
                            </td>
                        </tr>
                        <tr>
                            <td>성명(*)</td>
                            <td><input type="text" name="membNm" value={val.membNm} onChange={ChangeValue}
                                       placeholder="이름을 입력하세요"/></td>
                        </tr>
                        <tr>
                            <td rowSpan="2">휴대폰 번호(*)</td>
                            <td>
                                <select name="countryNo" onChange={changeCountryNo}>
                                    <option value="">==선택==</option>
                                    <option value="010">대한민국+82</option>
                                    <option value="088">중국+88</option>
                                </select>
                                &nbsp; -&nbsp;
                                <input type="text" name="phone1" value={val.phone1} onChange={secondPhone}/>
                                &nbsp; -&nbsp;
                                <input type="text" name="phone2" value={val.phone2} onChange={lastPhone}/>
                                <button className="join_button" type="button"s
                                onClick={addCertificationNm} >인증번호 전송</button>
                            </td>
                        </tr>
                        <tr>
                            <td><input type="text" placeholder="인증번호를 입력하세요"
                                name="certifyNo" value={certifyNo} onChange={changeCerticyNo} />
                                <button className="join_button" type="button" onClick={validateMobile}>인증번호 확인</button>
                            </td>
                        </tr>
                        <tr>
                            <td>우편번호(*)</td>
                            <td>
                                <input type="text" value={val.zipCd} name="zipCd"/>
                                <button className="join_button" type="button" onClick={openPostCode}>우편번호 검색
                                </button>
                            </td>
                        </tr>

                        <tr>
                            <td>주소(*)</td>
                            <td><input type="text" value={val.zipAddr} name="zipAddr"
                                       placeholder="주소를 입력하세요"/></td>
                        </tr>
                        <tr>
                            <td>상세주소</td>
                            <td><input type="text" value={val.detailAddr} name="detailAddr"
                                       onChange={ChangeValue}
                                       placeholder="주소를 입력하세요"/></td>
                        </tr>
                        <tr>
                        <td>회원유형</td>
                        <td><input type='checkbox' name="membCls" value="ROLE_SELLER" onChange={(e)=>checkOnlyOne(e.target)}/>판매자입니다.</td>
                        </tr>
                        <tr>
                        <td></td>
                        <td><input type='checkbox' name="membCls" value="ROLE_USER" onChange={(e)=>checkOnlyOne(e.target)}/>일반회원입니다.</td>
                        </tr>
                        </tbody>
                    </Table>
                    <button className="member_join" type="submit">가입하기</button>
                </Form>
            </div>
        </div>
        <div id="popupDom">
            {isPopupOpen && (<PopupDom>
                <PopupPostCode onClose={closePostCode} getData={getData}/>
            </PopupDom>)}
        </div>
    </div>)
}
export default JoinUpdate;