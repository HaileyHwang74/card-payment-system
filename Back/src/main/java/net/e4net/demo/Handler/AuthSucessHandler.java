//package net.e4net.demo.Handler;
//
//
//
//import net.e4net.demo.Repository.MemberRepository;
//import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
//import org.springframework.security.core.Authentication;
//import org.springframework.stereotype.Component;
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import lombok.RequiredArgsConstructor;
//
//@RequiredArgsConstructor
//@Component
//public class AuthSucessHandler extends SimpleUrlAuthenticationSuccessHandler {
//    private final MemberRepository memberRepository;
//
////    @Override
////    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
////        memberRepository.updateMemberLastLogin(authentication.getName());
////        setDefaultTargetUrl("/board");
////        super.onAuthenticationSuccess(request, response, authentication);
////    }
//}