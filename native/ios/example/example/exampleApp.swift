//
//  exampleApp.swift
//  example
//
//  Created by net e4 on 2022/12/19.
//

import SwiftUI

@main
struct exampleApp: App {
    
    var body: some Scene {
        
        WindowGroup {
            ContentView()
                .environmentObject(PostCodeModel())
        }
    }
}
