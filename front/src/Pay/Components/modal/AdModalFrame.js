import React ,{useState} from 'react';
import AdModal from './AdModal';

  
function ModalFrame(props){
  const membSn= sessionStorage.getItem("membSn");
  const [modalOpen, setModalOpen] = useState(true);
  
  const closeModal = () => {
    setModalOpen(false);
  }

  function auto_close(){
    setTimeout({closeModal}, 3000);
  }

  return(
  <>
    <AdModal open={modalOpen} close={closeModal} selfClose={auto_close} >
    {/* Modal.js <main> {props.children} </main>에 내용이 입력된다. 리액트 함수형 모달 */}
    <div className="modalWrapper">
    <div className="modalWrapper">
      <span className="background" />
    </div>
        <img  src="https://cdn.lean2u.net/news/photo/202009/20200902_1_bodyimg_332.png" style={{maxWidth: "100%" ,height:"auto"}} alt='이포넷'/>

        <span className="background" />
      </div>
    </AdModal>
  </>
  );
}


export default ModalFrame ;