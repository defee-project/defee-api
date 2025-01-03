package org.team.defee.member.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.team.defee.common.util.HashUtil;
import org.team.defee.member.dto.RegisterDto;
import org.team.defee.member.entity.Member;
import org.team.defee.member.repository.MemberRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final HashUtil HashUtil;

    @Transactional
    public Long register(RegisterDto dto){

        validateEmail(dto.getEmail());
        validateUsername(dto.getUsername());

        String hashedPassword = HashUtil.hashPassword(dto.getPassword());

        Member member = new Member();
        member.setEmail(dto.getEmail());
        member.setUsername(dto.getUsername());
        member.setPassword(hashedPassword);

        if (dto.getBlogUrl() != null){
            member.setBlogUrl(dto.getBlogUrl());
        }

        memberRepository.save(member);
        return member.getId();
    }

    public void validateEmail(String email) {
        List<Member> findUsers = memberRepository.findByEmail(email);
        if (!findUsers.isEmpty()){
            throw new IllegalStateException("이미 사용중인 이메일입니다.");
        }
    }

    public void validateUsername(String username) {
        List<Member> findMembers = memberRepository.findByUsername(username);
        if (!findMembers.isEmpty()){
            throw new IllegalStateException("이미 사용중인 아이디입니다.");
        }
    }

    public List<Member> findMembers(){
        return memberRepository.findAll();
    }

    public Member findOne(Long memberId){
        return memberRepository.findOne(memberId);
    }

    public Member findOneByEmail(String email){
        return memberRepository.findByEmail(email).get(0);
    }
}
