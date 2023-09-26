//
//  UserModel.swift
//  example
//
//  Created by net e4 on 2022/12/20.
//

import Foundation

class UserModel {
    struct User {
        var userName: String
        var password: String
    }
    //db 연결하기 전에 임의로
    var users: [User] = [
           User(userName: "test", password: "1111"),
           User(userName: "test2", password: "2222")
       ]
    // 아이디 형식 검사.   => db에 존재하는지 아닌지 그거랑 별개로 , 일단 형식검사만 같이 진행
    //리턴을 Bool로 받음
        func isValidEmail(id: String) -> Bool {
            //알파벳, 숫자, 특수문자   , @뒤에는 알파벳, 숫자 . 뒤에는 알파벳 2개 이상
            let emailRegEx = "[A-Z0-9a-z._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}"
            let emailTest = NSPredicate(format: "SELF MATCHES %@", emailRegEx)
            return emailTest.evaluate(with: id)
        }
        
        // 비밀번호 형식 검사
        func isValidPassword(pwd: String) -> Bool {
            //8~12자리,영어,숫자,특수문자 가능 비밀번호
            let passwordRegEx = "^(?=.*[A-Za-z])(?=.*[0-9])(?=.*[!@#$%^&*()_+=-]).{8,12}"
            let passwordTest = NSPredicate(format: "SELF MATCHES %@", passwordRegEx)
            return passwordTest.evaluate(with: pwd)
        }
    }

//Optional a? a!(해제)
