//
//  ChargeModel.swift
//  example
//
//  Created by net e4 on 2023/01/02.
//

import Foundation

struct ChargeModel: Codable {
    
    let accessToken: String
    let membSn: String   //나중에 Integer로 바꿔서 해보기
    let membCls: String
}
