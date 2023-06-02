import axios from 'axios';
import React ,{useState} from 'react';

import Modal from './Modal';

  
function ModalFrame(props){

  const membSn = sessionStorage.getItem("membSn");
  const [modalOpen, setModalOpen] = useState(false);
  
  const {basketAmt, basketCount,basketNm,basketGoodsNo}= props
 
  const openModal = () => {

    console.log(membSn,basketCount,basketAmt);
    console.log(typeof membSn);
    console.log(typeof basketNm,basketNm);
    console.log(typeof basketCount);
    console.log(typeof basketAmt,basketAmt);

    //장바구니 db에 넣기
    if(props.basketNm !=null){
    axios({
      url: global.ipAddress+":8888/money/basket",
      method: "post",
      headers: {"Content-Type" : "application/json",
      "Authorization" : "Bearer" + sessionStorage.getItem("AccessToken")},
      data:{
        membSn: parseInt(membSn),
        basketNm: basketNm,
        basketCount: basketCount,
        basketAmt: basketAmt,
        membSn: membSn,
        basketGoodsNo: basketGoodsNo
      }})
      .then((res)=>{
        console.log(`res.data => ${JSON.stringify(res.data)}`)
        // alert('장바구니 성공');
      })
      .catch((error) => {
          console.log("error", error);
        });
    setModalOpen(true);
  }
  else{
    alert('선택이 완료되지 않았습니다.');
  }
    }



  const closeModal = (props) => {
    setModalOpen(false);

    //  Modal 창에서 쇼핑계속하기/ 장바구니 이동 둘 중 어느 것을 누르더라도 
    // 누르는 순간, 다시 ==선택== , count :0,  결제금액 : 0 으로 리셋되어야한다.
    // 그걸 다시 pay.js 에 넘겨줘야 함.
  }


  
return(
  <>
  <button onClick={openModal}>장바구니 담기</button>
  <Modal open={modalOpen} close={closeModal} header="결제하기">
   {/* Modal.js <main> {props.children} </main>에 내용이 입력된다. 리액트 함수형 모달 */}
   <div className="modalWrapper">
      <div className="modalContainer">
        <div>
          <p className="text">
          <br />
            선택하신 상품이
            <br /> 장바구니에 추가 되었습니다.
          </p>
          </div>
      </div>
      <span className="background" />
    </div>
  </Modal>
</>
);
  }


export default ModalFrame ;