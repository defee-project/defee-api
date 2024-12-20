package org.team.defee.bookmark.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.team.defee.bookmark.dto.AddBookmarkDto;
import org.team.defee.bookmark.service.BookmarkService;
import org.team.defee.bookmark.dto.CreateBookmarkDto;
import org.team.defee.post.entity.Post;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/bookmark")
public class BookmarkController {
    private final BookmarkService bookmarkService;

    @PostMapping("/create")
    public ResponseEntity<String> createBookmark(@RequestBody CreateBookmarkDto createBookmarkDto) {
        System.out.println(createBookmarkDto.getBookmark());
        String bookmarkName = bookmarkService.createBookmark(createBookmarkDto.getBookmark(), createBookmarkDto.getEmail());
        return ResponseEntity.status(HttpStatus.CREATED).body(bookmarkName +"폴더 추가 완료");
    }

    @PostMapping("/add")
    public ResponseEntity<String> addBookmark(@RequestBody AddBookmarkDto addBookmarkDto) {
        bookmarkService.addBookmark(addBookmarkDto.getEmail(), addBookmarkDto.getBookmark(), addBookmarkDto.getPostId());
        return ResponseEntity.status(HttpStatus.CREATED).body("북마크 추가 완료");
    }

    @GetMapping()
    public ResponseEntity<List<Post>> getBookmark(@RequestParam String email, @RequestParam String bookmark) {
        List<Post> postList = bookmarkService.getPostsByBookmark(email,bookmark);
        return ResponseEntity.status(HttpStatus.OK).body(postList);
    }
}
