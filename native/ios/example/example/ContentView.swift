//
//  ContentView.swift
//  example
//
//  Created by net e4 on 2022/12/19.
//

//Optional 어떻게 쓰나

import SwiftUI
import Alamofire


struct ContentView: View {
    
    
    @State var username: String = ""
    @State var pwd: String = ""
    
    //navigationLink 성공 시 Active로 바꿔줌
    @State var isJoinView = false
    @State var isTwebView = false
    @State  var showPassword = false
    
    //자동로그인
    @State private var isOn = false
    
    //웹뷰(리액트)에 token, membSn 전달을 위한 변수
    @State var token = ""
    @State var membSn = ""
    @State var membCls = ""
    
    //alert창 띄우기
    @State var showingAlert = false
    
    
    //위치 여기로 고정할 것
    @ObservedObject var viewModel = WebViewModel()
    
    //token, membSn 따로 responseModel로 뺴서 써보려고
    @ObservedObject var responseModel = ReponseModel()
    
    
    //자동로그인 토글
    init() {
        UISwitch.appearance().onTintColor = .gray
        UISwitch.appearance().thumbTintColor = .white
    }
    
    var body: some View {
        
        NavigationView {
            ZStack{
                Color.peach.edgesIgnoringSafeArea(.all)
                
                VStack{
                    AppTitle()
                    HStack {
                        Image(systemName: "person")
                            .resizable()
                            .frame(width: 20, height: 20)
                        TextField("아이디", text: $username)
                        //첫 글자를 소문자로 강제 적용 (force lowercase)
                            .textInputAutocapitalization(.never)
                            .frame(width: 270, height: 20)
                            .padding()
                            .background(RoundedRectangle(cornerRadius: 10).strokeBorder())
                         
                        
                    }
                    HStack {
                        Image(systemName: "lock")
                            .resizable()
                            .frame(width: 20, height: 20)
                        HStack{
                            if showPassword {
                                TextField("비밀번호", text: $pwd)
                                    .textInputAutocapitalization(.never)
                            } else {
                                SecureField("비밀번호", text: $pwd)
                            }
                            Button(action: {
                                self.showPassword.toggle()
                            }, label: {
                                Image(systemName: "eye")
                                    .foregroundColor(.secondary)
                            })
                        }
                        .frame(width: 270, height: 20)
                        .padding()
                        .background(RoundedRectangle(cornerRadius: 10).strokeBorder())
                        
                    }
                    //자동로그인
                    VStack{
                        LoginAction(isOn : $isOn)
                    }
                    
                    //로그인 성공하면 navigation으로 웹뷰 이동
                    HStack {
                        NavigationLink("",destination: TWebView(token: $token, membSn: $membSn, membCls: $membCls),isActive: $isTwebView )
                            
                        Button(action: {
                            
                            login()
                            
                            // isOn => 자동로그인 토글
                            if isOn {
                                print("자동로그인: \(isOn)")
                            }else {
                                
                                //리셋을 해놓아야 로그아웃 후 다시 로그인 화면으로 돌아왔을 때, 아이디,비밀번호가 저장되어있지 않다.
                                username = ""
                                pwd = ""
                                print("자동로그인: \(isOn)")
                            }
                            
                        })
                        {
                            Text("로그인")
                                .foregroundColor(.black)
                                .font(.system(size: 13)
                                        .weight(.bold))
                                .padding()
                                .buttonStyle(.bordered)
                                .frame(width: 110, height: 30, alignment: .center)
                            
                        }.background(Color.white)
//                            .opacity(0.7)
                            .cornerRadius(15)
                        
                        
                        NavigationLink("",destination: JoinView(isJoinView: $isJoinView), isActive: $isJoinView)
                        Button(action: {
                            isJoinView.toggle()
                        })
                        {
                            Text("회원가입")
                                .foregroundColor(.black)
                                .font(.system(size: 13)
                                        .weight(.bold))
                                .padding()
                                .buttonStyle(.bordered)
                                .frame(width: 110, height: 30, alignment: .center)
                            
                        }.background(Color.white)
//                            .opacity(0.7)
                            .cornerRadius(15)
                            .frame(width: 110, height: 8, alignment: .center)
                        
                        
                    }.padding()     //Hstack끝
                    
                }
            }
            
        }
        //로그인 실패 시 alert창 띄우기
        .alert("로그인 실패", isPresented: $showingAlert) {
            Button("확인") {}
        } message: {
            Text("아이디와 비밀번호를 다시 입력해주시기 바랍니다.")
        }
    }
    
    //이포넷 로고
    struct AppTitle: View {
        var body: some View {
            VStack {
                Image("e4net")
                    .frame(width: 200, height: 200, alignment: .center)
                    .background(Color.white)
                    .clipShape(Circle())
                    .overlay{
                        Circle().stroke(.white, lineWidth: 4)
                        
                    }
                    .shadow(radius: 7)
            }
            .frame(maxWidth: 200,maxHeight: 250, alignment: .center)
        }
    }
    
    //로그인
    //로그인 값 받아와서 token까지 해결되면 isActive =>True로 바뀜
    func login(){
        let login = LoginModel(membId: username, membPwd: pwd)
        print("login :\(login)")
        HttpClient<TokenModel>().login(
            url: URLInfo.login(),
            loginInfo: login,
            onSuccess: { (resData) in
                print("토큰ㅁㅁㅁ: \(type(of: resData))")
                print("토큰 : \(resData.accessToken)")
                
                token = resData.accessToken
                membSn = String(resData.membSn)
                membCls = resData.membCls
                
                
                //Binding 말고 model 쓰기 위한
                responseModel.token = resData.accessToken
                responseModel.membSn = String(resData.membSn)
                
                
                isTwebView.toggle()
            },
            onFailure: {
                showingAlert = true
            }
        )
    }
    
}    // view 끝



//자동로그인 (그냥 다른 뷰 그 자체임)
struct LoginAction :View {
    @Binding var isOn :Bool
    var body: some View {
        HStack {
            Toggle(isOn : $isOn){
                Text("자동로그인")
            }
            .frame(width: 140, height: 40)
        }
    }
}



//struct ContentView_Previews: PreviewProvider {
//    static var previews: some View {
//        ContentView()
//    }
//}
