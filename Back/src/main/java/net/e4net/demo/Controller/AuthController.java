package net.e4net.demo.Controller;

import lombok.RequiredArgsConstructor;
import net.e4net.demo.DTO.MemberDto;
import net.e4net.demo.DTO.TokenDto;
import net.e4net.demo.Service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<MemberDto> signup(@RequestBody MemberDto dto) {
        return ResponseEntity.ok(authService.signup(dto));
    }

    @PostMapping("/login")
    public ResponseEntity<TokenDto> login(@RequestBody MemberDto dto, HttpServletRequest request) {

        return ResponseEntity.ok(authService.login(dto, request.getRemoteAddr()));
    }

    @GetMapping("/validateCheck/{membId}")
    public ResponseEntity<Integer> validateCheck(@PathVariable String membId){
//        System.out.println(membId);
        return ResponseEntity.ok(authService.validateCheck(membId));
    }

    //안드로이드용 비밀번호 찾기
    @PutMapping ("/changePassword/{membId}")
    public ResponseEntity<Integer> changePassword(@PathVariable String membId, @RequestParam("changePassword") String changePassword){
        return ResponseEntity.ok( authService.changePassword(membId, changePassword));
    }




}
