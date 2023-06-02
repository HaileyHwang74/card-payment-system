//package net.e4net.demo.Config;
//
//import lombok.RequiredArgsConstructor;
//import net.e4net.demo.Domain.Member;
//import net.e4net.demo.Repository.MemberRepository;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.domain.AuditorAware;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.stereotype.Component;
//
//import javax.validation.constraints.NotNull;
//import java.util.Optional;
//
////@EnableJpaAuditing
//@Configuration
//@Component
//public class AuditorConfig implements AuditorAware<Member> {
//
//    @NotNull
//    @Override
//    public Optional<Long> getCurrentAuditor() {
//        Authentication authentication = Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication())
//                .orElseThrow(() -> new RuntimeException("로그인되지 않았습니다."));
//
//        if (!authentication.isAuthenticated()) {
//            return Optional.empty();
//        }
//        //security에서 유저정보를 꺼내온다.
//        //거기에 유저 sn이 있을것이다.
//        //그걸반환한다.
//        return Optional.of(11L);
//    }
//
//}
