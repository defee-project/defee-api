package org.team.defee.member.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.team.defee.member.dto.LoginDto;
import org.team.defee.member.dto.RegisterDto;
import org.team.defee.member.entity.Member;
import org.team.defee.member.service.AuthService;
import org.team.defee.member.service.MemberService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members")
public class MemberController {
    private final MemberService memberService;
    private final AuthService authService;

    @GetMapping("/test")
    public String test(){
        return "hello";
    }

    @GetMapping
    public ResponseEntity<List<Member>> getAllMembers(){
        try {
            List<Member> users = memberService.findMembers();
            return ResponseEntity.status(HttpStatus.OK).body(users);
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping
    public ResponseEntity<String> register(@RequestBody RegisterDto dto){
        try {
            Long userId = memberService.register(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body("회원가입 성공: " + userId);
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PostMapping("/login")
    // ? -> 변경 예정
    public ResponseEntity<?> login(@RequestBody LoginDto dto){
        try {
            String token = authService.login(dto);
            return ResponseEntity.status(HttpStatus.OK).body(new JwtResponse(token));
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    public static class JwtResponse {
        private String token;

        public JwtResponse(String token){
            this.token = token;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }
    }
}
