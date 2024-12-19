package org.team.defee.Follow.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.team.defee.Follow.entity.Follow;
import org.team.defee.Follow.repository.FollowRepository;
import org.team.defee.member.entity.Member;
import org.team.defee.member.service.MemberService;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FollowService {
    private final FollowRepository followRepository;
    private final MemberService memberService;

    public String createFollow(String follow, String email) {
        Member member = memberService.findOneByEmail(email);
        Follow followEntity = new Follow();
        followEntity.setFolderName(follow);
        followEntity.setMember(member);
        followRepository.save(followEntity);
        return follow;
    }
}
