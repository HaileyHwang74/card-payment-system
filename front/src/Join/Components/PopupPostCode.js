import React from 'react';
import DaumPostcode from "react-daum-postcode";
// import '../css/join.css'

const PopupPostCode = (props) => {
    // 우편번호 검색 후 주소 클릭 시 실행될 함수, data callback 용
    const handlePostCode = (data) => {
        let address = data.address;
        let zoneCode = data.zonecode;
        let extraAddress = '';

        if (data.addressType === 'R') {
            if (data.bname !== '') {
                extraAddress += data.bname;
            }
            if (data.buildingName !== '') {
                extraAddress += (extraAddress !== '' ? `, ${data.buildingName}` : data.buildingName);
            }
            address += (extraAddress !== '' ? ` (${extraAddress})` : '');
        }
        console.log(data)
        console.log(address)
        console.log(data.zonecode)
        props.onClose()

        props.getData(zoneCode,address);
    }





    return(
        <div className="postCodeStyle">
            <DaumPostcode  onComplete={handlePostCode} />
            <button type='button' onClick={() => {props.onClose()}} className='postCode_btn'>닫기</button>
        </div>
    )
}

export default PopupPostCode;