import React from 'react';
import '../../css/modal.css';
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faTimes } from "@fortawesome/free-solid-svg-icons";

function ApModal(props){
  // 열기, 닫기, 모달 헤더 텍스트를 부모로부터 받아옴
  const { open, close, header,selfClose} = props;

  return (  
    // 모달이 열릴때 openModal 클래스가 생성된다.
    <div className={open ? 'openModal modal' : 'modal'}>
      {open ? (
     <main style={{backgroundColor:"white",opacity:"0.8"}}> 
              <div style={{display:"flex", justifyContent:"flex-end"}}>
              <button className="close" style={{backgroundColor:"white" ,color:"black"}} onClick={close}>
              <FontAwesomeIcon icon={faTimes} />
              </button> 
              </div>
          {props.children}
         </main>
      ) : null}
    </div>
    
  );
};


export default ApModal;