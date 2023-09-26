//
//  URLInfo.swift
//  example
//
//  Created by net e4 on 2022/12/20.
//

import Foundation
import Alamofire
import SwiftUI

class URLInfo {
    
    //상수 => 전역에서 써야되는지 확인 후 , 방법 알아보기
   static var ipAddress: String = "http://192.168.8.53"
      
    //회원가입
    static func join()-> String{
        return ipAddress + ":8888/auth/signup"
    }
    
    //로그인
    static func login() -> String {
        return  ipAddress + ":8888/auth/login"
    }
    
    //핸드폰번호
    static func getCheckNum() -> String {
        return ipAddress + ":8888/send-one"
    }
    
    //아이디 중복확인
    static func checkId(membId: String) -> String{
        return ipAddress + ":8888/auth/validateCheck/\(membId)"
    }
    
    //웹뷰 잔액
    static func showBalance(membSn: Int) -> String{
        return ipAddress + ":8888/money/checkBalance/\(membSn)"
    }
    
    
}
