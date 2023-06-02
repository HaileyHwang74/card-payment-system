import React, {memo ,useState, useEffect} from "react";
import "../Charge/css/charge.css"
import 'bootstrap/dist/css/bootstrap.min.css';
import axios from "axios";


function UploadImage({setFile}){
  //파일 미리볼 url을 저장해줄 state
  const [fileImage, setFileImage] = useState("");
  const [fileName, setFileName] = useState("");

  // 파일 저장
  const saveFileImage = (e) => {
    const img = e.target.files[0];
    setFileImage(URL.createObjectURL(img));
    const formData = new FormData();
    formData.append('img' , img);
    console.log(formData);
    for(const keyValue of formData) {
    console.log(keyValue);  // ["img", File]

    setFileName(e.target.files[0].name);    // "대박이.jpeg" 

    console.log(e.target.files[0])
    console.log(typeof e.target.files[0])
    setFile(() => e.target.files[0]);

  };
  }
  console.log(fileImage);
  console.log(fileName);     

  // 파일 삭제   => formData 고민
  const deleteFileImage = () => {
    URL.revokeObjectURL(fileImage);
    setFileImage("");
  };

  return (
          <>
               <div>
                {fileImage && (
                  <img
                    alt="sample"
                    src={fileImage}
                    style={{ margin: "auto", width: "80x", height:"50px" , alignItems:"center"}}
                  />
                )}
                <div
                  style={{
                    alignItems: "center",
                    justifyContent: "center",
                  }}
                >
                  <input
                    name="imgUpload"
                    type="file"
                    accept="image/*"
                    onChange={saveFileImage}
                  />

                  {/* <button
                    style={{
                      backgroundColor: "gray",
                      color: "white",
                      width: "55px",
                      height: "40px",
                      cursor: "pointer",
                    }}
                    type="button"
                    onClick={() => deleteFileImage()}
                  >
                    삭제
                  </button> */}
                </div>
              </div>
    </>
  );
}

export default UploadImage;