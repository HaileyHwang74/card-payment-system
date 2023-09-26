//
//  TokenModel.swift
//  example
//
//  Created by net e4 on 2022/12/21.
//

import Foundation
import Combine

//get으로 받아서 읽어와야하니까 Decodable일 거 같은데

class TokenModel : Decodable,ObservableObject {
    //여기에 TokenDto와 동일하게 담아야하나..?
    var membSn : Int
    var accessToken : String
    var grantType : String
    var tokenExpiresIn : Int
    var membCls: String
}

