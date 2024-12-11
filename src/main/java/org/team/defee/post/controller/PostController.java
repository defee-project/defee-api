package org.team.defee.post.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.team.defee.post.entity.Post;
import org.team.defee.post.service.PostService;
import org.team.defee.post.service.VelogWebCrawler;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts")
public class PostController {
    private final PostService postService;
    private final VelogWebCrawler velogWebCrawler;

    @GetMapping("/test")
    public String test() {
        return "hello";
    }

    @GetMapping("/headline")
    public List<Post> getKeywordPosts(
            @RequestParam(value = "keyword", required = false) String keyword,
            @RequestParam(value = "page", defaultValue = "0") int page
    ) {
        if (page < 0) {
            throw new IllegalArgumentException("page는 0이거나 커야합니다.");
        }
        return postService.findPostsByKeyword(keyword, page);
    }

    @GetMapping()
    public List<Post> getBookmarkPosts(
            @RequestParam(value = "bookmark", required = false) String bookmark,
            @RequestParam(value = "page", defaultValue = "0") int page
    ) {
        if (page < 0) {
            throw new IllegalArgumentException("page는 0이거나 커야합니다.");
        }
        return postService.findPostsByBookmark(bookmark, page);
    }

    @PostMapping("/crawl")
    public ResponseEntity<String> crawlPosts() {
        try {
            VelogWebCrawler.crawlPosts();
            return ResponseEntity.ok("크롤링 성공");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("크롤링 실패");
        }
    }
}
