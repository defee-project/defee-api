package org.team.defee.member.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name="Member", description = "회원 통합 API")
public class MemberController {
    private final MemberService memberService;
    private final AuthService authService;

    @GetMapping("/test")
    public String test(){
        return "hello";
    }

    @GetMapping
    @Operation(summary = "회원 조회 api", description = "모든 회원 조회 api입니다.")
    public ResponseEntity<List<Member>> getAllMembers(){
        try {
            List<Member> users = memberService.findMembers();
            return ResponseEntity.status(HttpStatus.OK).body(users);
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping
    @Operation(summary = "회원가입 api", description = "회원가입 api입니다.")
    @Parameters({
            @Parameter(name = "email", description = "이메일", example = "test@test.com"),
            @Parameter(name = "password", description = "패스워드", example = "1234"),
            @Parameter(name = "username", description = "유저이름", example = "테스터"),
            @Parameter(name = "blogUrl", description = "블로그주소", example = "https://velog.io")
    })
    public ResponseEntity<String> register(@RequestBody RegisterDto dto){
        try {
            Long userId = memberService.register(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body("회원가입 성공: " + userId);
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PostMapping("/login")
    @Operation(summary = "로그인 api", description = "로그인 api입니다.")
    @Parameters({
            @Parameter(name = "email", description = "이메일", example = "test@test.com"),
            @Parameter(name = "password", description = "패스워드", example = "1234")
    })
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
