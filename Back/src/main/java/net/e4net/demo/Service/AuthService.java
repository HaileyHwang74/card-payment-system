package net.e4net.demo.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.e4net.demo.DTO.MemberDto;
import net.e4net.demo.DTO.TokenDto;
import net.e4net.demo.Entity.LoginHistory;
import net.e4net.demo.Entity.Member;
import net.e4net.demo.Entity.Money;
import net.e4net.demo.JWT.TokenProvider;
import net.e4net.demo.Repository.LoginHistoryRepository;
import net.e4net.demo.Repository.MemberRepository;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static net.e4net.demo.Entity.Member.createMember;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class AuthService {
    private final AuthenticationManagerBuilder managerBuilder;
    private final MemberRepository repository;
    private final PasswordEncoder passwordEncoder;

    private final LoginHistoryRepository loginHistoryRepository;
    private final TokenProvider tokenProvider;

    /**
     * 회원가입
     */
    @Transactional()
    public MemberDto signup(MemberDto dto) {
        if (repository.existsByMembId(dto.getMembId())) {
            throw new RuntimeException("이미 가입되어 있는 유저입니다");
        }

        Member member = dto.toMember(passwordEncoder);   //entity
        //swift용
//        if(dto.getMembCls() == "판매회원"){
//
//        }
        dto.of(repository.save(member)); //dto
        log.debug("dto=>{}",dto.getMembSn());

        Money.createMoney(repository.save(createMember(member)));
        return dto;
    }

    /**
     * 아이디 중복체크
     */
    @Transactional
    public Integer validateCheck(String membId) {
        if (repository.existsByMembId(membId)) {
            return 1;
        }
        return 0;
    }

    /**
     * 비밀번호 변경(안드로이드용)
     */
    @Transactional
    public Integer changePassword(String membId, String changePassword) {
        Optional<Member> updateMember = repository.findByMembId(membId);

        String finalPassword = passwordEncoder.encode(changePassword);
        log.debug("{찾은 member의 membSn,=>{}", updateMember.get().getMembSn());

        updateMember.get().setPassWord(finalPassword);
        log.debug("{찾은 member의 membSn,=>{}", updateMember.get().getMembSn());


        /**
         *         그대로 save 해주면 나머지 값들이 null로 들어가는 것으로 확인 됨. 그래서 다시 createMember해줌  => 나중에 다시 확인 할 것!
         *         updateMember.get().builder.setPassword
         *         log.debug("member 보자=>{}", updateMember.get().getMembS)
         *         repository.save(updateMember.get());
         */

        Member member = createMember(updateMember.get());

        log.debug("고쳐졌나..=>{}", member.getEmailAddr());
        log.debug("고쳐졌나..=>{}", member.getMembPwd());
        repository.save(member);

        return 1;
    }


    /**
     * 로그인
     */
    @Transactional()
    public TokenDto login(MemberDto dto, String connectIp) {
//        Long MembSn = dto.getMembSn();
//        => 안 나온 이유 :  dto에는 당연히 안 담겨있고, entity에서 가져와야해서 repository에서 찾아서 가져온다.
        log.debug("새로 바뀐 memberDto의 membPwd 제대로 나오나,=>{}", dto.getMembPwd());

        UsernamePasswordAuthenticationToken authenticationToken = dto.toAuthentication();
        log.debug("token 입니다.=>{}", authenticationToken);

        Authentication authentication = managerBuilder.getObject().authenticate(authenticationToken);
        log.debug("authentication 입니다=>{} ", authentication);

        Optional<Member> member = repository.findByMembId(dto.getMembId());
        log.debug("ㅇㅇㅇㅇㅇ", "111");

        Long membSn = member.get().getMembSn();
        log.debug("membSn 나오나.. =>{}", membSn);


        String membCls = String.valueOf(member.get().getMembCls());
        log.debug("membCls 어떻게 나오려나..=>{}", membCls);


        loginHistoryRepository.save(LoginHistory.builder()
                .member(Member.builder().membSn(membSn).build())
                .connectIp(connectIp)
                .build());

        return tokenProvider.generateTokenDto(authentication, membSn, membCls);
    }


}