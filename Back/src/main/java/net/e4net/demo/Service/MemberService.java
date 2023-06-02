package net.e4net.demo.Service;

import lombok.RequiredArgsConstructor;
import net.e4net.demo.Config.SecurityUtil;
import net.e4net.demo.DTO.MemberDto;
import net.e4net.demo.Repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {


    private final MemberRepository repository;


    /**
     * 로그인
     */
    @Transactional()
    public MemberDto getMyInfoBySecurity() {
        return repository.findById(SecurityUtil.getCurrentMemberId())
                .map(MemberDto::of)
                .orElseThrow(() -> new RuntimeException("로그인 유저 정보가 없습니다"));
    }

}


