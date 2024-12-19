package org.team.defee.bookmark.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.team.defee.bookmark.service.BookmarkService;
import org.team.defee.bookmark.dto.CreateBookmarkDto;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/bookmark")
public class BookmarkController {
    private final BookmarkService bookmarkService;

    @PostMapping("/create")
    public ResponseEntity<String> createBookmark(@RequestBody CreateBookmarkDto createBookmarkDto) {
        String bookmarkName = bookmarkService.createBookmark(createBookmarkDto.getBookmark(), createBookmarkDto.getEmail());
        return ResponseEntity.status(HttpStatus.CREATED).body(bookmarkName +"폴더 추가 완료");
    }

}
