package org.team.defee.keyword.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.team.defee.keyword.service.KeywordService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/keyword")
public class KeywordController {
    private final KeywordService keywordService;

}
