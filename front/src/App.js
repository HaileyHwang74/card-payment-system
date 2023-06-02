import 'bootstrap/dist/css/bootstrap.min.css';
import './App.css';
import './Join/pages/Join';
import {Route, Routes} from 'react-router-dom';
import Join from "./Join/pages/Join";
import JoinUpdate from './Join/pages/JoinUpdate';
import Charge from "./Charge/pages/Charge";
import Pay from "./Pay/pages/Pay";
import History from "./History/pages/History";
import Login from './Login/pages/Login';
import Logout from './Login/pages/Logout';
import Basket from './Pay/pages/Basket';
import GoodsSignUp from './Merchant/GoodsSignUp';


function App() {

    // let ipAddress = "http://192.168.8.53";
    let ipAddress = "http://192.168.200.142";

    global.ipAddress = ipAddress;

    return (
        <div className="App">
            <Routes>
                <Route path="/join" element={<Join/>}/>
                <Route path="/joinUpdate" element={<JoinUpdate/>}/>
                <Route path='/' element={<Login/>}/>
                <Route path='/logout' element={<Logout/>}/>
                <Route path='/basket' element={<Basket/>}/>
                <Route path="/charge" element={<Charge/>}/>
                <Route path="/pay" element={<Pay/>}/>
                <Route path="/history" element={<History/>}/>
                <Route path="/goodsSignup" element={<GoodsSignUp/>}/>
            </Routes>
        </div>
    );
}

export default App;
