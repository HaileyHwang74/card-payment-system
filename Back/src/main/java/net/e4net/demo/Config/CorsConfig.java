package net.e4net.demo.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {
    @Bean
    public CorsFilter corsFilter() {

        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("http://localhost:3000"); // React 의 경우 포트가 다르므로 따로 허용해줘야함.
//        config.addAllowedOrigin("http://192.168.8.27:8888"); // React 의 경우 포트가 다르므로 따로 허용해줘야함.\
        config.addAllowedOrigin("http://192.168.8.53:3000"); // React 의 경우 포트가 다르므로 따로 허용해줘야함.\
//        config.addAllowedOrigin("http://192.168.8.45:8888/**");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        config.addExposedHeader("Authorization");
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}

//html
//React app.js 에 설정해놓기 => 전역에서 쓸 수있도록
//Device.open
//Device.close
//const sendData = {
//action: "pong"
//        param: {
//    count :count
//        }


//swift
//switch action {
//case: "pong"
//if let