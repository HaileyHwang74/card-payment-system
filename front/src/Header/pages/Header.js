import React, { useState } from "react";
import styled from "styled-components";
import { useNavigate , Link } from 'react-router-dom';
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faBars, faUser, faTimes, faCartPlus } from "@fortawesome/free-solid-svg-icons";
import Logout from "../../Login/pages/Logout";

function Header(props) {
 const [isToggled, setIsToggled] = useState(false);
  const [userToggled, setUserToggled] = useState(false);

  const navigate = useNavigate();
  const Header = styled.div`
  // max-width: 1400px;
  margin: 0;
  display: flex;
  justify-content: space-between;
  align-items: center;
  color: white;
  background-color: black;

  .logo {
    margin: 0 1rem;
    font-size: 2rem;
  }

  .header__menulist {
    list-style: none;
    display: flex;
  }

  .header__left {
    display: flex;
  }

  .header__right {
    list-style: none;
    display: flex;
  }

  .header__right div {
    margin: 0 1rem;
  }

  li {
    padding: 0 1rem;
  }

  .toggle {
    display: none;
    font-size: 1.5rem;
    padding: 1rem 1rem;
  }

  .user {
    display: none;
    font-size: 1.5rem;
    padding: 1rem 1rem;
  }

  @media screen and (max-width: 768px) {
    flex-wrap: wrap;

    .header__right {
      display: ${(props) => (props.userToggled ? "flex" : "none")};
      flex-direction: column;
      width: 100%;
      background-color: black;
    }

    .header__menulist {
      display: ${(props) => (props.isToggled ? "flex" : "none")};
      flex-direction: column;
      width: 100%;
      background-color: black;
    }

    .header__menulist li,
    .header__right li {
      margin: 1rem 0;
      padding: 0;
    }

    .toggle {
      display: block;
    }

    .user {
      display: block;
    }
  }
`;

const membSn= sessionStorage.getItem("membSn");
const membCls = sessionStorage.getItem("membCls");


  return (
  <Header isToggled={isToggled} userToggled={userToggled}>
  {/* 햄버거 버튼(bar) */}
  <div
    className="toggle"
    onClick={() => {
      setIsToggled(!isToggled);
      setUserToggled(false);
    }}
  >
    <FontAwesomeIcon icon={!isToggled ? faBars : faTimes} />
  </div>

  {/* 이포넷 로고 */}
  <div className="logo">

    <img src="https://www.e4net.net/img/logo.png" alt='이포넷'/>
  </div>

  {/* User 버튼 */}
  <div
    className="user"
    onClick={() => {
      setUserToggled(!userToggled);
      setIsToggled(false)
    }}
  >
   {/*  <FontAwesomeIcon icon={!userToggled ? membSn!=null ? faCartPlus : faUser : null} /> */}
  {/*  다른 faUser 로 해서 드롭다운으로 mypage, 장바구니, 회원정보 수정 등 다양하게 넣고 싶지만, 구현한게 적으므로 바로 장바구니 아이콘으로 함 ,
       membSn 관련해서는 나중에 비회원 케이스를 구현할 경우 쓰려고 일부러 수정하지 않음*/}
  {/* <FontAwesomeIcon icon={ membSn!=null ? faCartPlus : faUser} /> */}
  <FontAwesomeIcon icon={ faUser} />
  </div>

  {/* 메뉴 리스트 */}
  <ul className="header__menulist">
    <li>
    <Link to="/charge" style={{textDecorationLine: "none" ,color: 'white', }}>머니충전</Link>
    </li>
    <li>
    <Link to="/pay" style={{textDecorationLine: "none" ,color: 'white' }}>머니결제</Link>
    </li>
    <li>
    <Link to="/history" style={{textDecorationLine: "none" ,color: 'white' }}>결제내역</Link>
    </li>
    { membCls == 'ROLE_SELLER' || membCls == 'ROLE_ADMIN' ? 
    <li>
    <Link to="/goodsSignup" style={{textDecorationLine: "none" ,color: 'white' }}>상품등록</Link>
    </li> : null}
  </ul>


  {/* logout 하는거 chargeActivity 에서 membSn 되면  intent 로 loginActivity 로 이동하는 거 하기  */}
  {/* User 메뉴 리스트 */}
 <ul className="header__right">
  <li><Link to="/basket" style={{textDecorationLine: "none" ,color: 'white' }}>  <FontAwesomeIcon icon={ membSn!=null ? faCartPlus : faUser} /></Link></li>
    <li>
      {/* <div onClick={()=>{sessionStorage.setItem("membSn",""); console.log("membSn null"); navigate('/')} } style={{textDecorationLine: "none" ,color: 'white' }}>Logout</div> */}
       {/* ios용 */}
      <div onClick={()=>{Logout()} } style={{textDecorationLine: "none" ,color: 'white' }}>Logout</div>
    </li>
  </ul>
</Header>
    );
}

export default Header;