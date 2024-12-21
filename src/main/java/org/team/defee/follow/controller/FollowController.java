package org.team.defee.follow.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.team.defee.follow.dto.CreateFollowDto;
import org.team.defee.follow.service.FollowService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/follow")
@Tag(name="Follow", description = "키워드 API")
public class FollowController {
    private final FollowService keywordService;

    @PostMapping("/create")
    @Operation(summary = "키워드 생성 api", description = "키워드 생성 api 입니다..")
    public ResponseEntity<String> createFollow(@RequestBody CreateFollowDto createFollowDto) {
        String follow = keywordService.createFollow(createFollowDto.getFollow(), createFollowDto.getEmail());
        return ResponseEntity.status(HttpStatus.CREATED).body(follow + "키워드 추가 완료");
    }
}
