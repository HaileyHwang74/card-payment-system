//
//  ZipCodeView.swift
//  example
//
//  Created by net e4 on 2023/01/02.
//

import SwiftUI

struct ZipCodeView: View {
    
    @ObservedObject var viewModel = WebViewModel()
        
    var body: some View {
        
        VStack{
            WebView(url: "https://ruixianhwang.github.io/zipCode/", viewModel: viewModel , token: "", membSn: "", membCls:"")
        }
        
    }
    
    
    
    
    
    
    
}



//struct ZipCodeView_Previews: PreviewProvider {
//    static var previews: some View {
//        ZipCodeView()
//    }
//}
