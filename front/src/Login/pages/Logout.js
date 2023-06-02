import React, {useEffect, useState,useCallback} from 'react';
import { useNavigate } from 'react-router-dom';

function Logout(){
    sessionStorage.setItem("membSn","");
    const sendData = {action: "logout"}
   window.webkit.messageHandlers.setLoginInfo.postMessage(sendData);
  }
  export default Logout;