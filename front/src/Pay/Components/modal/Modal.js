import React from 'react';
import '../../css/modal.css';
import { Link } from 'react-router-dom';


function Modal(props){
  // 열기, 닫기, 모달 헤더 텍스트를 부모로부터 받아옴
  const { open, close, header } = props;

  return (
    // 모달이 열릴때 openModal 클래스가 생성된다.
    <div className={open ? 'openModal modal' : 'modal'}>
      {open ? (
        <section>
         <main>   
          {props.children}
         </main>
          <footer>
            <div style={{display:"flex" , justifyContent:"center"}}>
            <button className="modalBtn shoppingBtn" onClick={close} style={{marginRight:"80px"}}>쇼핑계속하기</button>
            <Link to="/basket">
              <button className="modalBtn cartBtn">장바구니 이동</button>
            </Link>
            </div>
          </footer>
        </section>
      ) : null}
    </div>
  );
};


export default Modal;