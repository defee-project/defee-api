package org.team.defee.bookmark.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.team.defee.bookmark.dto.AddBookmarkDto;
import org.team.defee.bookmark.service.BookmarkService;
import org.team.defee.bookmark.dto.CreateBookmarkDto;
import org.team.defee.member.entity.Member;
import org.team.defee.member.service.AuthService;
import org.team.defee.post.entity.Post;

import java.util.List;
import java.util.Objects;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/bookmark")
@Tag(name = "Bookmark", description = "북마크 API")
public class BookmarkController {
    private final BookmarkService bookmarkService;
    private final AuthService AuthService;

    @PostMapping("/create")
    @Operation(summary = "북마크 생성 api", description = "유저가 북마크를 생성하는 api 입니다.")
    public ResponseEntity<String> createBookmark(@Parameter(description = "JWT Token", required = true)
                                                 @RequestHeader("Authorization") String authorizationHeader, @RequestBody CreateBookmarkDto createBookmarkDto) {
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } else {
            String token = Objects.requireNonNull(authorizationHeader).substring(7);
            Member member = AuthService.checkJwtToken(token);
            if (member == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
            String bookmarkName = bookmarkService.createBookmark(createBookmarkDto.getBookmark(), member.getEmail());
            if (bookmarkName == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("해당 북마크가 이미 존재합니다.");
            }
            return ResponseEntity.status(HttpStatus.CREATED).body(bookmarkName + "폴더 추가 완료");
        }

    }

    @PostMapping("/add")
    @Operation(summary = "북마크 추가 api", description = "유저가 게시글에 북마크를 추가하는 api 입니다.")
    public ResponseEntity<String> addBookmark(@Parameter(description = "JWT Token", required = true)
                                              @RequestHeader("Authorization") String authorizationHeader, @RequestBody AddBookmarkDto addBookmarkDto) {
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } else {
            String token = Objects.requireNonNull(authorizationHeader).substring(7);
            Member member = AuthService.checkJwtToken(token);
            if (member == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
            String result = bookmarkService.addBookmark(member.getEmail(), addBookmarkDto.getBookmark(), addBookmarkDto.getPostId());
            if (result == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("해당 북마크에 게시글이 이미 존재합니다.");
            }
            return ResponseEntity.status(HttpStatus.CREATED).body("북마크 추가 완료");

        }
    }

    @GetMapping()
    @Operation(summary = "북마크 조회 api", description = "유저가 추가한 북마크의 게시글을 보여주는 api 입니다.")
    public ResponseEntity<List<Post>> getBookmark(@Parameter(description = "JWT Token", required = true)
                                                  @RequestHeader("Authorization") String authorizationHeader, @RequestParam String bookmark) {
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } else {
            String token = Objects.requireNonNull(authorizationHeader).substring(7);
            Member member = AuthService.checkJwtToken(token);
            if (member == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
            List<Post> postList = bookmarkService.getPostsByBookmark(member.getEmail(), bookmark);
            return ResponseEntity.status(HttpStatus.OK).body(postList);

        }
    }
}
