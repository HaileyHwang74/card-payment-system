//
//  LoginModel.swift
//  example
//
//  Created by net e4 on 2022/12/21.
//

import Foundation

//post일 때는 Encodable, get일때는 decodable해야한느 듯

//Identifiable은 Id가 있을 때 쓸 수 있는 듯..?
struct LoginModel : Encodable {
    var membId :String
    var membPwd: String
}


