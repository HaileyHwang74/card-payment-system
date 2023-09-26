//
//  HttpClient.swift
//  example
//
//  Created by net e4 on 2022/12/20.
//

import Foundation
import Alamofire

class HttpClient<T: Decodable> : ObservableObject {
    
    
    //useEffect 랑 같다고 볼 수 있다.
    //들어오는 인자가 t 이면 success
    typealias onSuccess = (T) -> ()
    typealias onFailure = () -> ()
    
    let headers: HTTPHeaders = [
        //요청할때 어떤 형태로 줘라(json)
        .accept("application/json"),
        //응답할 때 서버에 이런 형태로 던져라
        .contentType("application/json")
    ]

    //회원가입 인증
    func join(url: String, joinInfo: JoinModel,  onSuccess :@escaping onSuccess , onFailure: @escaping onFailure){
        
        
        //nill 아니면 return 해서 탈출
        guard let sessionUrl = URL(string: url)else{
            print("Invalid URL")
            return
        }
        AF.request(sessionUrl,
                   method: .post,
                   parameters: joinInfo,
                   encoder: JSONParameterEncoder.default)
            .validate(statusCode: 200..<300)
            .responseDecodable(of: T.self){ response in
//            .response { response in
                print("response: \(response)")
                switch response.result {
                case .success(let value):
                    onSuccess(value)
                case .failure(let error):
                    print(error)
                    onFailure()
                }
                
            }
    }
    
    
    
    //로그인 인증
    func login(url: String, loginInfo: LoginModel, onSuccess :@escaping onSuccess , onFailure: @escaping onFailure){
        
        
        //nill 아니면 return 해서 탈출
        guard let sessionUrl = URL(string: url)else{
            print("Invalid URL")
            return
        }
        AF.request(sessionUrl,
                   method: .post,
                   parameters: loginInfo,
                   encoder: JSONParameterEncoder.default)
            .validate(statusCode: 200..<300)
            .responseDecodable(of: T.self){ response in
//            .response { response in
                print("response: \(response)")
                switch response.result {
                case .success(let value):
                    onSuccess(value)
                case .failure(let error):
                    print(error)
                    onFailure()
                }
                
            }
    }
    
    
    // 잔액
    func checkBalance(url: String, onSuccess :@escaping onSuccess , onFailure: @escaping onFailure){
        
        //nill 아니면 return 해서 탈출
        guard let sessionUrl = URL(string: url)else{
            print("Invalid URL")
            return
        }
        print("sessionURL : \(sessionUrl)")
        AF.request(sessionUrl,
                   method: .get,
                   parameters: nil,
                   encoding: URLEncoding.default,
                   headers: headers)
            .validate(statusCode: 200..<300)
            .responseDecodable(of: T.self){ response in
                print("response: \(response)")
                switch response.result {
                case .success(let value):
                    onSuccess(value)
                case .failure(let error):
                    print(error)
                    onFailure()
                }
            }
    }
    
    
    
    
    
    //회원가입//
    
    //중복아이디 체크
    func validateIdCheck(url: String, onSuccess :@escaping onSuccess , onFailure: @escaping onFailure){
        
        //nill 아니면 return 해서 탈출
        guard let sessionUrl = URL(string: url)else{
            print("Invalid URL")
            return
        }
        print("sessionURL : \(sessionUrl)")
        AF.request(sessionUrl,
                   method: .get,
                   parameters: nil,
                   encoding: URLEncoding.default,
                   headers: headers)
            .validate(statusCode: 200..<300)
            .responseDecodable(of: T.self){ response in
                print("response: \(response)")
                switch response.result {
                case .success(let value):
                    onSuccess(value)
                case .failure(let error):
                    print(error)
                    onFailure()
                }
            }
    }
    

    
    
    
    //핸드폰인증
    func sendOne(url: String, phoneNum: String, onSuccess :@escaping onSuccess , onFailure: @escaping onFailure){
        //nill 아니면 return 해서 탈출
        guard let sessionUrl = URL(string: url)else{
            print("Invalid URL")
            return
        }
        AF.request(sessionUrl,
                   method: .post,
                   parameters: phoneNum,
                   encoder: JSONParameterEncoder.default)
        
            .validate(statusCode: 200..<300)
            .responseDecodable(of: T.self){ response in
//            .response { response in
                print("response: \(response)")
                switch response.result {
                case .success(let value):
                    onSuccess(value)
                case .failure(let error):
                    print(error)
                    onFailure()
                }
                
            }
    }
    
    
  
    
}

