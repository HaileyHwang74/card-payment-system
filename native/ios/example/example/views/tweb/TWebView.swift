//
//  TWebView.swift
//  example
//
//  Created by net e4 on 2022/12/20.
//

import SwiftUI


struct TWebView: View {
    
    
    @State var bar: Int = 0
    @ObservedObject var viewModel = WebViewModel()
    
    
    //이것도 ObservedObject 
    @Binding var token: String
    @Binding var membSn: String
    @Binding var membCls: String
    
    
    @State var token2 = ""
    @State var membSn2 = ""
    @State var membCls2 = ""
    
    var body: some View {
        VStack {
            let token2 = $token
            let membSn2 = $membSn
            let membCls2 = $membCls
            
            let token3 = token2.wrappedValue
            let membSn3 = membSn2.wrappedValue
            let membCls3 = membCls2.wrappedValue
            
            WebView(url: "http://192.168.8.53:3000/charge", viewModel: viewModel, token: token3, membSn: membSn3, membCls: membCls3)

        }
        .onReceive(self.viewModel.bar.receive(on: RunLoop.main)) {
            value in self.bar = UserDefaults.standard.integer(forKey: "membSn")
        }.navigationBarBackButtonHidden(true)
    }
    
    
}


//struct TWebView_Previews: PreviewProvider {
//    static var previews: some View {
//        TWebView()
//    }
//}
