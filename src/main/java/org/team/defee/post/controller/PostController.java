package org.team.defee.post.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.team.defee.post.entity.Post;
import org.team.defee.post.service.PostService;
import org.team.defee.post.service.VelogWebCrawlerService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts")
@Tag(name = "Post", description = "게시글 통합 API")
public class PostController {
    private final PostService postService;
    private final VelogWebCrawlerService velogWebCrawlerService;

    @GetMapping("/test")
    @Operation(summary = "게시글 테스트 api", description = "테스트용 api")
    public String test() {
        return "hello";
    }

    @GetMapping("/headline")
    @Operation(summary = "헤드라인 게시글 조회 api", description = "헤드라인 페이지에서 사용하는 게시글 조회 api입니다.")
    @Parameters({
            @Parameter(name = "keyword", description = "키워드", example = "new"),
            @Parameter(name = "page", description = "페이지", example = "0")
    })
    public ResponseEntity<List<Post>> getKeywordPosts(
            @RequestParam(value = "keyword", required = false) String keyword,
            @RequestParam(value = "page", defaultValue = "0") int page
    ) {
        if (page < 0) {
            throw new IllegalArgumentException("page는 0이거나 커야합니다.");
        }
        List<Post> postList = postService.findPostsByKeyword(keyword, page);

        return ResponseEntity.status(HttpStatus.OK).body(postList);
    }

    @GetMapping()
    @Operation(summary = "모든 게시글 조회 api", description = "모든 게시글 조회 api입니다.")
    public ResponseEntity<List<Post>> getAllPosts() {
        List<Post> postList = postService.findAllPosts();
        return ResponseEntity.status(HttpStatus.OK).body(postList);
    }

//    @GetMapping()
//    @Operation(summary = "북마크 게시글 조회 api", description = "북마크 페이지에서 사용하는 게시글 조회 api입니다.")
//    @Parameters({
//            @Parameter(name = "bookmark", description = "북마크", example = ""),
//            @Parameter(name = "page", description = "페이지", example = "0")
//    })
//    public ResponseEntity<List<Post>> getBookmarkPosts(
//            @RequestParam(value = "bookmark", required = false) String bookmark,
//            @RequestParam(value = "page", defaultValue = "0") int page
//    ) {
//        if (page < 0) {
//            throw new IllegalArgumentException("page는 0이거나 커야합니다.");
//        }
//        List<Post> postList = postService.findPostsByBookmark(bookmark, page);
//        return ResponseEntity.status(HttpStatus.OK).body(postList);
//
//    }

    @PostMapping("/crawl")
    public ResponseEntity<String> crawlPosts() {
        try {
            velogWebCrawlerService.crawlPosts();
            return ResponseEntity.ok("크롤링 성공");
        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.badRequest().body("크롤링 실패");
        }
    }
}
