//
//  Join.swift
//  example
//
//  Created by net e4 on 2022/12/20.
//

import SwiftUI

//문제1. 핑크 컬러로 덮어씌우고 싶음
//문제2. 회원가입 버튼 화면 안에 넣기
//문제3. 이메일 뒤에 주소 체크박스

struct JoinView: View {
    
    
    // 바인딩을 통해서 이전 페이지의 정보를 가져와서 사용
    @Binding var isJoinView: Bool
    
    @State var isZipCodeView = false
    
    // 회원가입 실패 시 alert창 띄우기
    @State var showingAlert = false
    
    //휴대폰 번호 입력하지 않았을 시 alert창 띄우기
    @State var showingAlertPhoneNum = false
    //인증번호 불일치 시 alert창 띄우기
    @State var showingAlertNum = false
    //인증번호가 입력되지 않았을 때 alert
    @State var showingAlertNumNull = false
    
    //비밀번호 보이고 안 보이고
    @State  var showPassword = false
    @State var showCheckPassword = false
    
    @State var showingValidateCheck = false
    
    let memberType = ["일반회원","판매회원"]
    
    
    
    @State var membCls = ""
    @State var selectedEmail2 = ""
    @State var username = ""
    @State var pwd = ""
    @State var pwdcheck = ""
    @State var name = ""
    @State var email = ""
    @State var phoneNum = ""
    @State var checkPhone = ""
    @State var confirmCode = ""
    
    
    @EnvironmentObject var postCodeModel : PostCodeModel
    
    @State var zipcode = ""
    @State var address = ""
    @State var detailAddress = ""
    @State var member = 0
    
    //picker를 위한
    @State private var email2 = "@naver.com"
    @State private var selectedIndex = "ROLE_USER"
    
    
    //아이디 중복여부 체크
    @State var validateCheck = ""
    //비밀번호 일치여부 체크
    @State var pwdCheckCheck = false
    
    
    var body: some View {
        
        //        ZStack{
        Form {
            
            Text("회원가입").listRowBackground(Color.clear)
                .font(.system(size: 30, weight: .bold))
            
            //아이디 + 비밀번호
            Group{
                //아이디
                Section(header: Text("아이디")){
                    HStack{
                        TextField("아이디를 입력해주세요", text: $username)
                            .textInputAutocapitalization(.never)
                            .keyboardType(.default) //키보드를 기본 키보드로 보여줍니다.
                        
                        Button(action: {
                            HttpClient<Int>().validateIdCheck(
                                url: URLInfo.checkId(membId: username),
                                onSuccess: { (resData) in
                                    print("resData : \(resData)")  //중복 : 1, 존재하지 않으면 :0 나온다.
                                    //중복됨
                                    if(resData == 1 ){
                                        validateCheck = "중복된 아이디입니다."
                                        username = ""
                                    }else {
                                        validateCheck = "사용가능한 아이디입니다."
                                    }
                                    
                                    showingValidateCheck = true
                                },
                                onFailure: {
                                    username = ""
                                } //값 반환되지 않음 = onFailure
                            )
                        }) {
                            Text("중복확인").foregroundColor(.gray)
                        }
                    }  //hstack 끝
                }//                    .listRowBackground(Color.clear)  //section 끝
                
                // 중복된 아이디입니다. 사용가능한 아이디입니다. 텍스트로 나타내줌
                if showingValidateCheck {
                    Text("\(validateCheck)")
                        .foregroundColor(.blue)
                        .listRowBackground(Color.clear)
                }
                
                //비밀번호
                Section(header: Text("비밀번호")){
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
                    //                    .keyboardType(.default) //키보드를 기본 키보드로 보여줍니다.
                }
                //                    .listRowBackground(Color.clear)
                
                //비밀번호확인
                Section(header: Text("비밀번호 확인")){
                    VStack{
                        HStack{
                            if showCheckPassword {
                                TextField("비밀번호 재입력", text: $pwdcheck)
                                    .textInputAutocapitalization(.never)
                            } else {
                                SecureField("비밀번호 재입력", text: $pwdcheck)
                            }
                            Button(action: {
                                
                                self.showCheckPassword.toggle()
                            }, label: {
                                Image(systemName: "eye")
                                    .foregroundColor(.secondary)
                            })
                        }
                        .onChange(of: pwdcheck) { V in
                            if pwd == pwdcheck {
                                pwdCheckCheck = true
                            }else{
                                pwdCheckCheck = false
                            }
                            
                        }
                        //                            .keyboardType(.default) //키보드를 기본 키보드로 보여줍니다.
                        
                    }
                }
                //                    .listRowBackground(Color.clear).
                //section 끝
                if pwd.count > 0 && pwdcheck.count > 0{
                    Text((pwdCheckCheck ? "일치합니다." : "일치하지 않습니다.")).foregroundColor(pwdCheckCheck ? .blue : .red)
                        .foregroundColor(.blue)
                        .listRowBackground(Color.clear)
                }
            }    //Group 끝
            
            
            //이름
            Section(header: Text("성명")){
                TextField("이름을 입력해주세요", text: $name)
                    .keyboardType(.default) //키보드를 기본 키보드로 보여줍니다.
            }
            //                .listRowBackground(Color.clear)
            
            
            //휴대폰번호
            Section(header: Text("핸드폰번호")){
                VStack{
                    HStack{
                        TextField("핸드폰번호를 입력해주세요", text: $phoneNum)
                            .keyboardType(.default) //키보드를 기본 키보드로 보여줍니다.
                        Spacer()
                        
                        
                        Button("인증번호 전송"){
                            if phoneNum.count > 0 {
                                HttpClient<Int>().sendOne(
                                    url: URLInfo.getCheckNum(),
                                    phoneNum: phoneNum,
                                    onSuccess: { (resData) in
                                        print("resData : \(resData)")
                                        confirmCode = String(resData)
                                        print("phone Count:\(phoneNum.count)")
                                    },
                                    onFailure: {
                                        phoneNum = ""
                                    }
                                )
                            }
                            else {
                                showingAlertPhoneNum = true
                                phoneNum = ""
                            }
                        }
                        .foregroundColor(.gray)
                    }  .alert("", isPresented: $showingAlertPhoneNum) {
                        Button("확인") {}
                    } message: {
                        Text("핸드폰 번호가 제대로 입력되지 않았습니다.")
                    }
                    
                    
                    HStack{
                        TextField("인증번호를 입력해주세요", text: $checkPhone)
                            .keyboardType(.default) //키보드를 기본 키보드로 보여줍니다.
                        Button("확인"){
                            if checkPhone != "" {
                                
                                if(checkPhone != confirmCode){
                                    checkPhone = ""
                                    print(checkPhone.count)
                                    showingAlertNum = true
                                    print(showingAlertNum)
                                }
                            }else{
                                showingAlertNumNull = true
                                showingAlertNum = false
                            }
                            
                        }.foregroundColor(.gray)
                    }
                    //인증번호 일치하지 않을 때 alert 띄우기
                    .alert("", isPresented: $showingAlertNum) {
                        Button("확인") {}
                    } message: {
                        Text("인증번호가 일치하지 않습니다. 다시 입력해주시기 바랍니다.")
                    }
                }
            }
            
            //                .listRowBackground(Color.clear)
            
            
            
            //이메일
            Section(header: Text("이메일")){
                HStack{
                    TextField("이메일을 입력해주세요", text: $email)
                        .textInputAutocapitalization(.never)
                        .keyboardType(.default) //키보드를 기본 키보드로 보여줍니다.
                    Picker("", selection: $email2){
                        Text("@naver.com").tag("@naver.com")
                        Text("@google.com").tag("@google.com")
                        Text("@daum.net").tag("@daum.net")
                    }.pickerStyle(.menu)
                    
                }
                
            }
            //                .listRowBackground(Color.clear)
            
            
            
            //우편번호 + 주소 + 상세주소 : Group
            Group {
                
                //우편번호
                Section(header: Text("우편번호")){
                    HStack{
                        
                        TextField("우편번호를 입력하세요", text: $postCodeModel.zipcode)
                        NavigationLink("",destination: ZipCodeView(),isActive: $isZipCodeView)
                            .navigationBarBackButtonHidden(true)
                        
                        Button(action: {
                            isZipCodeView.toggle()
                            print(isZipCodeView.toggle())
                        })
                        {
                            Text("우편번호 찾기")
                                .foregroundColor(.gray)
                        }
                    }.foregroundColor(.black)
                    
                }
                //                    .listRowBackground(Color.clear)
                
                //주소
                Section(header: Text("주소")){
                    
                    TextField("주소를 입력하세요", text:$postCodeModel.adderss)
                }
                //                    .listRowBackground(Color.clear)
                
                //상세주소
                Section(header: Text("상세주소")){
                    TextField("상세주소를 입력해주세요", text: $detailAddress)
                        .keyboardType(.default) //키보드를 기본 키보드로 보여줍니다.
                }
                //                    .listRowBackground(Color.clear)
                
            }   //Group 끝
            
            // 회원유형
            Section(header: Text("회원유형")){
                Picker("", selection: $selectedIndex){
                    Text("판매회원").tag("ROLE_SELLER")
                    Text("일반회원").tag("ROLE_USER")
                }.pickerStyle(SegmentedPickerStyle())
                
            }
            .listRowBackground(Color.clear) //여기는 무조건 살려놓기
            
            
            Button(action: {
                boo()
            })
            {
                Text("회원가입")
                    .foregroundColor(.black)
                    .font(.system(size: 13)
                            .weight(.bold))
                    .padding()
                    .buttonStyle(.bordered)
                
            }.background(Color.gray)
                .opacity(0.7)
                .cornerRadius(15)
                .frame(width: 300, height: 3, alignment: .center)
                .listRowBackground(Color.clear)
            //button 끝
            
            
            //나중에 지우기
            NavigationLink("로그인화면으로 돌아가기",destination: ContentView())
            
        }
        //여기까지가 form 끝
        .listRowBackground(Color.clear)
        .accentColor(.black)
        .foregroundColor(Color.black)
        .onAppear(perform: {
            UITableView.appearance().backgroundColor = UIColor.clear
            UITableViewCell.appearance().backgroundColor = UIColor.clear
            UITableView.appearance().sectionFooterHeight = 0   //section 사이의 공간 없애기
        })
        .edgesIgnoringSafeArea(.all)
        .background(Color.ivory)    //peach
        
        //        }
        //zstack
        //고민: zstack, vstack 이런 거 하나 더 만들어서 싸놓아야하나 아님 그냥 form 으로도 되나
        
        
        .alert("회원가입 실패", isPresented: $showingAlert) {
            Button("확인") {}
        } message: {
            Text("다시 입력해주시기 바랍니다.")
        }
        
        
        // 위에 back 버튼 삭제 (나중에 주석풀기)
        .navigationBarBackButtonHidden(true)
        
    }
    //body : view 끝
    
    
    func boo(){
        let join = JoinModel(membCls: selectedIndex,membStatusCdNum: 1, membId: username, membPwd: pwd, membNm: name,
                             mobileNo: phoneNum, emailAddr: email + email2, zipCd: zipcode, zipAddr: address, detailAddr: detailAddress)
        print("join :\(join)")
        //다른 방법 찾아보기
        if(username != "" && pwd != "" && name != "" && phoneNum != "" && email != "" && email2 != "" && zipcode != ""  && address != "" && detailAddress != "" && checkPhone != ""){
            HttpClient<JoinModel>().join(
                url: URLInfo.join(),
                joinInfo: join,
                onSuccess: { (resData) in
                    print("resData : \(resData)")
                    isJoinView.toggle()  //toggle이 false 가 되어서 다시 로그인 화면으로 돌아갈 수 있게 해줌
                },
                onFailure: {
                    showingAlert = true
                }
            )
        }else{
            showingAlert = true
        }
        
        
    }    //func boo
}




//struct JoinView_Previews: PreviewProvider {
//    static var previews: some View {
//        JoinView()
//    }
//}


