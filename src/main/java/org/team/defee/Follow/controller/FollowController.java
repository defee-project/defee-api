package org.team.defee.Follow.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.team.defee.Follow.dto.CreateFollowDto;
import org.team.defee.Follow.service.FollowService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/follow")
public class FollowController {
    private final FollowService keywordService;

    @PostMapping("/create")
    public ResponseEntity<String> createFollow(@RequestBody CreateFollowDto createFollowDto) {
        String follow = keywordService.createFollow(createFollowDto.getFollow(), createFollowDto.getEmail());
        return ResponseEntity.status(HttpStatus.CREATED).body(follow + "키워드 추가 완료");
    }
}
