package net.e4net.demo.Controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.e4net.demo.DTO.MemberDto;
import net.e4net.demo.Service.MemberService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

//@ResponseBody 자바객체를 json 타입으로 변환시켜줌
//@RequestBody json을 자바객체로 변환
// json은 문자열이므로 parsing 필요함

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/members")

public class MemberController {

    private final MemberService memberService;
    @GetMapping("")
    public ResponseEntity<MemberDto> getMyMemberInfo() {
        MemberDto myInfoBySecurity = memberService.getMyInfoBySecurity();
        return ResponseEntity.ok((myInfoBySecurity));
    }

}

