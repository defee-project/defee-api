package org.team.defee.bookmark.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.team.defee.bookmark.service.BookmarkService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/bookmark")
public class BookmarkController {
    private final BookmarkService bookmarkService;
}
