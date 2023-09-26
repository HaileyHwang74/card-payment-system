//
//  WebViewModel.swift
//  example
//
//  Created by net e4 on 2022/12/20.
//

import Foundation
import Combine

// ObservableObject: 개체가 변경되기 전에 내보내는 게시자가 있는 개체 유형입니다.
class WebViewModel: ObservableObject {
    // PassthroughSubject: 다운스트림 구독자에게 요소를 브로드캐스트 하는 주제
    
    //a를 주고 b 를 받는다. foo를 주고 bar 를 받는다라고 표현한 것임 .
    var foo = PassthroughSubject<String, Never>()
    var bar = PassthroughSubject<String, Never>()
}
