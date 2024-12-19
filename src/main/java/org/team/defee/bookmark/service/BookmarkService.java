package org.team.defee.bookmark.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.team.defee.bookmark.entity.Bookmark;
import org.team.defee.bookmark.repository.BookmarkRepository;
import org.team.defee.member.entity.Member;
import org.team.defee.member.service.MemberService;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BookmarkService {
    private final BookmarkRepository bookmarkRepository;
    private final MemberService memberService;

    public String createBookmark(String bookmarkName, String memberEmail){
        Member member = memberService.findOneByEmail(memberEmail);
        Bookmark bookmark = new Bookmark();
        bookmark.setFolderName(bookmarkName);
        bookmark.setMember(member);
        bookmarkRepository.save(bookmark);
        return bookmarkName;
    }
}
