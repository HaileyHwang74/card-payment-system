import React, {useState,useCallback,useMemo} from 'react';
import "../css/history.css";
import Table from 'react-bootstrap/Table';
import { useEffect } from 'react';
import axios from 'axios';
import Pagination from './Pagination';
import Header from '../../Header/pages/Header';
import Footer from '../../Header/pages/Footer';

function History(props) {

     const membSn = sessionStorage.getItem("membSn");
     const [payMeanCd, setPayMeancd] = useState("d");
     const [startDate, setStartDate] = useState("");
     const [endDate, setEndDate]= useState("");

     

     const [translist, settranslist] = useState([]);  // 리스트에 나타낼 아이템들
     const [count, setCount] = useState(0); // 아이템 총 개수 //총 카운트로 대체
     const [currentPage, setCurrentPage] = useState(1); // 현재 페이지. default 값으로 1
     const [searchCurrentPage, setSearchCurrentPage] = useState(1);   //검색용
     const [postPerPage,setpostPerPage] = useState(3); // 한 페이지에 보여질 아이템 수 
     const [indexOfLastPost, setIndexOfLastPost] = useState(0); 
 
    //전체 목록 불러오기
    const pageAll=()=>{
        axios
            .post(global.ipAddress+":8888/moneyHistory/pagination/"+ membSn+ "/" +indexOfLastPost +  "?page=" + (currentPage-1) + "&size=" + postPerPage )
            .then((res) => {
              settranslist(res.data.content);
              console.log(res);
              console.log(res.data.content);
              setCount(res.data.totalElements);
              console.log("aaaaa"+currentPage);
            })
            .catch((error) => {
              console.log("error", error);
            });  
    };

    //결제수단 선택 목록 불러오기
    const pageByPayMeanCd=()=>{
        axios
        .post(global.ipAddress+":8888/moneyHistory/search/"+ membSn+ "/" +indexOfLastPost  + "?page=" + (setSearchCurrentPage-1) + "&size=" + postPerPage +"&payMeanCd="+ parseInt(payMeanCd))
        .then((res) => {
          console.log('넘어와짐')
          settranslist(res.data.content);
          console.log(payMeanCd);
          console.log(res.data);
          console.log(res.data.content);
          setCount(res.data.totalElements);
          console.log("aaaaa"+currentPage);
          
        })
        .catch((error) => {
            console.log('카드 넘기는 거 실패');
          console.log("error", error);
        });
    }

    //날짜 선택 & 결제수단 목록 불러오기
    const pageByDatePayMeanCd=() =>{
        axios.post(global.ipAddress+":8888/moneyHistory/findDatePayMeancd/"+ membSn+"/" +indexOfLastPost  + "?page=" + (setSearchCurrentPage-1) + "&size=" + postPerPage +"&payMeanCd="+ parseInt(payMeanCd)+"&startDate="+startDate +"&endDate=" +endDate )
        .then((res) => {
            /* alert('성공') */
            settranslist(res.data.content);
            console.log(res);
            console.log(res.data);
            setCount(res.data.totalElements);
            console.log("aaaaa"+currentPage);
    
            })
            .catch((error) => {
            console.log("error", error);
            });           
    }

       //처음 & 전체보기 선택 시 결제 페이지 그 자체 랜더링
      useEffect(() => {
        console.log('aaaa' + indexOfLastPost);
        if(payMeanCd =="d"){
           pageAll();
        }
      },[currentPage]); 

      //결제내역 랜더링 - 날짜 & 결제수단
      useEffect(()=>{
        if(payMeanCd !="d" && startDate =="" ){
                console.log("1");
                console.log(payMeanCd,startDate,endDate);
                 pageByPayMeanCd(); 
                console.log('결제수단 거래내역-데이트')
            }
        else if(payMeanCd !="d"&& startDate !="" && endDate !="" ){
                
                console.log(startDate, endDate);
                pageByDatePayMeanCd();
                console.log('결제수단 거래내역-데이트 , 결제수단');
        }
      },[searchCurrentPage])


      const setPage = (e) => {
        setCurrentPage(e)
        if(e == 1 ){
            setIndexOfLastPost(0)
        }else{
            setIndexOfLastPost((e - 1) * 3)
        };
      };

      const searchSetPage =(e)=>{
        setSearchCurrentPage(e)
        if(e ==1 ){
            setIndexOfLastPost(0)
        }else{
            setIndexOfLastPost((e-1)*3)
        };
      };


    //결제수단 선택
    const searchPayMeanCd =((e)=>{
        setPayMeancd(e.target.value);
         });
    console.log(payMeanCd);

     //처음날짜 선택
    const searchStartDate =(e)=>{
       setStartDate(e.target.value);
         }
         console.log(startDate);

    //끝나는 날짜 선택    
    const searchEndDate =(e)=>{
      setEndDate(e.target.value);
    }
    console.log(endDate);

    
    //버튼 조회
    const onClickSearch =() =>{
        console.log(endDate);
        console.log(payMeanCd);
        if(payMeanCd =="d"){
           pageAll();
        }else if(payMeanCd !="d" && startDate =="" && endDate ==""){
            console.log('결제수단 화면에 뿌려져야함')
            console.log(startDate,endDate);
             pageByPayMeanCd();
        }else if(payMeanCd !=""&&startDate !="" && endDate !=""){
           
            console.log("날짜 , 결제수단 화면에 같이 뿌려져야함")
            console.log(startDate,endDate);
            console.log(payMeanCd);
            pageByDatePayMeanCd();
           
        }
        searchSetPage(1);
         }  

    return (
        <>
        <Header/>
        <div className="history_box">
            <div className="history_container" style={{marginLeft:"30px", marginRight:"30px"}}>
                        <Table className="history_table" style={{border: "3px solid gray",marginTop:"160px", fontSize:"12px"}} size="sm">
                            <thead>
                            <tr>
                                <td style={{margin:"0px"}}>거래기간</td>
                                 <td style={{paddingRight: "0px"}}>
                                    <input type="date" name={startDate} value={startDate} onChange={searchStartDate}/>
                                </td>
                                <td style={{width: "1px"}}>-</td>
                                 <td style={{paddingLeft: "0px"}}>
                                    <input type="date" name={endDate} value={endDate} onChange={searchEndDate} /> 
                                 </td>
                                </tr>
                                <tr>
                                <td>결제수단</td>
                                <td>
                                    <select style={{height:"23px", width:"120px"}} onChange={searchPayMeanCd}>
                                    <option value="d">===전체보기===</option>
                                    <option value="7">카드</option>
                                    <option value="9">선불머니</option>
                                </select></td>
                                <td colSpan= "2">
                                    <button className="history_button" style={{width:"50px"}} onClick={onClickSearch}>조회</button>
                                </td>
                                {/* <td></td> */}
                            </tr>
                            </thead>
                        </Table>
                        <Table striped style={{border: "3px solid gray", fontSize:"10px"}} size="sm">
                            <thead>
                            <tr>
                                <th>일자</th>
                                <th>처리구분</th>
                                <th>결제수단</th>
                                <th>상품명</th>
                                <th>가맹점명</th>
                                <th>처리금액</th>
                                <th>처리상태</th>
                            </tr>
                            </thead>

                            {translist.map((list,idx) =>(
                                <>
                                <tbody key={idx}>
                                <tr>
                                    <td>
                                        {(list.firstRegistDt).substring(0,10)}
                                    </td>
                                    <td>
                                        {list.transferType == "4" ? "충전" :"사용" }
                                    </td>
                                    <td>
                                    {list.payMeanCd == "7" ? "카드" :"선불머니" }
                                    </td>
                                    <td>
                                        <div>{list.goodsNm}</div>
                                    </td>
                                    <td>
                                        <div>{list.merchantNm}</div>
                                    </td>
                                    <td>
                                        <div>{list.transferAmt}</div>
                                    </td>
                                    <td>
                                        <div>정상</div>
                                    </td>

                                </tr>
                                </tbody>
                                </>
                            ))}
                        </Table>
                        { payMeanCd =="d"
                           ? <Pagination page={currentPage} count={count} setPage={setPage} /> :
                        <Pagination page={searchCurrentPage} count={count} setPage={searchSetPage} /> 
                        }
                </div>
            </div>
            <Footer/>
        </>
    );

}

export default History;