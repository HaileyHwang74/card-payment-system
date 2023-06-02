import React from 'react';
import '../../css/modal.css';
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faTimes } from "@fortawesome/free-solid-svg-icons";
import PayCard from '../PayCard';
import PayMoney from '../PayMoney';


function PayModal(props){
  // 열기, 닫기, 모달 헤더 텍스트를 부모로부터 받아옴
  const { open, close, header,data } = props;
//   console.log(data);

 console.log(data.membSn);
 console.log(data.checkedBasketList)
 console.log(data.shipping)


  return (
    // 모달이 열릴때 openModal 클래스가 생성된다.
    <div className={open ? 'openModal modal' : 'modal'}>
      {open ? (
        <section>
         <main>
              <div style={{display:"flex", justifyContent:"flex-end"}}>
              <button className="close" style={{backgroundColor:"white"}} onClick={close}>
              <FontAwesomeIcon icon={faTimes} />
              </button> 
              </div>
          {props.children}
         </main>
          <footer>
            <div style={{display:"flex" , justifyContent:"space-between" ,marginLeft:"50px",marginRight:"50px" }}>
            <PayCard count={data.count} goodsAmt={data.goodsAmt} goodsNo={data.goodsNo} checkedNum={data.checkedNum}/>
            <PayMoney membSn={data.membSn} checkedBasketList={data.checkedBasketList} shipping={data.shipping} checkedNum={data.checkedNum}/>
            </div>
          </footer>
        </section>
      ) : null}
    </div>
  );
};


export default PayModal;