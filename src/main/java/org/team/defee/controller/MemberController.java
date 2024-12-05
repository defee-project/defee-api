package org.team.defee.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.team.defee.domain.Member;
import org.team.defee.service.MemberService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/member")
public class MemberController {
    private final MemberService memberService;

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
    public ResponseEntity<String> register(@RequestBody Member member){
        try {
            Long userId = memberService.register(member);
            return ResponseEntity.status(HttpStatus.CREATED).body("Member registered with id: " + userId);
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
