import React, {useState, useRef, useEffect} from "react";
import "../css/login.css";
import { useNavigate} from "react-router-dom";
import {Form} from "react-bootstrap";
import axios from "axios";

function Login(){
    const navigate = useNavigate();
    // const loginRef = useRef([]);
    const idRef = useRef("");


    const [login, setLogin] =useState({
            membId :"",
            membPwd: ""
    });

    const {membId, membPwd} = login;  // 구조분해
    
    // id :     영어 대,소문자 + 숫자  =>  4~12 글자로 입력
    const idRegExp =  /^[A-za-z0-9]{4,8}$/;
    // password:   영어, 숫자, 특수문자 허용 => 4,12 글자로 입력
    const pwdRegExp = /^(?=.*[0-9])(?=.*[a-zA-Z])[a-zA-Z0-9!@#$%^&*()._-]{3,12}$/;

    //유효한 아이디
    const validId = idRegExp.test(membId);
    //유효한 비밀번호
    const validPwd = pwdRegExp.test(membPwd);


    //화면 로딩되자마자 focus
    useEffect(()=>{
        idRef.current.focus();
    },[])


    //login state 값 바꿔주기
    function ChangeValue(e) {
        setLogin({
            ...login,
           [e.target.name] : e.target.value,
            
        });
      }   

      console.log(membId);
      console.log(membPwd);
      

    /**
     * 회원가입 버튼 클릭
     */
    const register =() =>{
        navigate("/join");
    }
  
    /**
     * 로그인 버튼 클릭
     */

    const signIn=()=>{
        setLogin(login);
        axios.post("http://localhost:8888/auth/login", login)
        .then((res)=>{
            console.log(res.data)
            //session에 넣기
            sessionStorage.setItem("ID", login.membId)
            sessionStorage.setItem("AccessToken",res.data.accessToken)
            sessionStorage.setItem("membSn",res.data.membSn) 
            sessionStorage.setItem("membCls", res.data.membCls)
            navigate('/charge');
        })
        .catch((error)=>{
            if (error.response) {
                // The request was made and the server responded with a status code
                // that falls out of the range of 2xx
                console.log(error.response.data);
                console.log(error.response.status);
                console.log(error.response.headers);
                alert('아이디와 비밀번호를 다시 입력해주시기 바랍니다.');
              } else if (error.request) {
                // The request was made but no response was received
                // `error.request` is an instance of XMLHttpRequest in the browser and an instance of
                // http.ClientRequest in node.js
                console.log(error.request);
              } else {
                // Something happened in setting up the request that triggered an Error
                console.log('Error', error.message);
              }
        })

    }
    


    return(
        <div className="home_container">
        <div className="home_info">
            <h1>E4. Pay Service</h1>
            <div>서비스 이용을 위해서는 로그인이 필요합니다.</div>
        </div>
        <div className="login_container">
                <Form>
                    <h5>아이디</h5>
                    {/* <input ref ={(el) => (loginRef.current[0] = el )} */}
                    <input ref ={idRef} type="text" maxLength="12" name="membId"value={membId} onChange={ChangeValue}/>
                    <h5>비밀번호</h5>
                    <input type="password" maxLength="12" name="membPwd" value={membPwd} onChange={ChangeValue}/>
                    <button type="button" className="login_signInButton"onClick={signIn}   disabled={!validId && !validPwd}> 로그인</button>
                    <button type="button" onClick={register} className="login_signInButton"> 회원가입</button>
                </Form>
        </div>
    </div>
       
    )
}

export default Login;