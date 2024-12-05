package org.team.defee.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.team.defee.domain.Member;
import org.team.defee.repository.MemberRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    
    public Long register(Member member){

        validateEmail(member);
        validateUsername(member);

        memberRepository.save(member);
        return member.getId();
    }

    private void validateEmail(Member member) {
        List<Member> findUsers = memberRepository.findByEmail(member.getEmail());
        if (!findUsers.isEmpty()){
            throw new IllegalStateException("이미 사용중인 이메일입니다.");
        }
    }

    private void validateUsername(Member member) {
        List<Member> findMembers = memberRepository.findByUsername(member.getUsername());
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
}
