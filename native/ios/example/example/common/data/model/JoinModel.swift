//
//  JoinModel.swift
//  example
//
//  Created by net e4 on 2022/12/29.
//

import Foundation

struct JoinModel : Codable {
    //    private Long membSn;

    var membCls : String   // 회원유형 : 판매자, 일반회원
    var membStatusCdNum :Int   // 가입시 변하지 않으므로 let으로 함
    var membId : String
    var membPwd : String
    var membNm : String
    var mobileNo : String
    var emailAddr : String
    var zipCd : String
    var zipAddr : String
    var detailAddr : String
    
}


