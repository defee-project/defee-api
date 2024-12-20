package org.team.defee.member.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.team.defee.common.util.HashUtil;
import org.team.defee.common.util.JwtUtil;
import org.team.defee.member.dto.LoginDto;
import org.team.defee.member.entity.Member;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final MemberService memberService;
    private final HashUtil HashUtil;


    public String login(LoginDto dto){
        Member member = memberService.findOneByEmail(dto.getEmail());
        if (member == null){
            throw new IllegalStateException("존재하지 않는 이메일입니다.");
        }

        if (!HashUtil.verifyPassword(dto.getPassword(), member.getPassword())){
            throw new IllegalStateException("비밀번호가 일치하지 않습니다.");
        }
        return JwtUtil.generateToken(member.getEmail());
    }

    public Member checkJwtToken(String jwtToken){
        if (JwtUtil.validateToken(jwtToken)){
            String email = JwtUtil.extractEmail(jwtToken);
            return memberService.findOneByEmail(email);
        } else{
            return null;
        }

    }

}
