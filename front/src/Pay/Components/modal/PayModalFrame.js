import React ,{useState} from 'react';
import PayModal from './PayModal';
import '../../css/modal.css';
  
function PayModalFrame(props){

  const [modalOpen, setModalOpen] = useState(false);
 
    console.log(JSON.stringify(props.checkedBasketList));
// 
  const openModal = () => {
    setModalOpen(true);
    }



  const closeModal = () => {
    setModalOpen(false);
  }


  
return(
  <>
  <div style={{display:"flex", justifyContent:"flex-end"}}>
  <button onClick={openModal} className="payButton" >결제하기</button>
  </div>
 
  <PayModal open={modalOpen} close={closeModal} header="결제하기" data={props}>
   {/* Modal.js <main> {props.children} </main>에 내용이 입력된다. 리액트 함수형 모달 */}
   <div className="modalWrapper">
      <div className="modalContainer">
        <div>
          <p className="text">
          <br />
           결제수단을 선택하시겠습니까?
          </p>
          </div>
      </div>
      <span className="background" />
    </div>
  </PayModal>
</>
);
  }


export default PayModalFrame ;