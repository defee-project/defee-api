package org.team.defee.bookmark.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.team.defee.bookmark.entity.Bookmark;
import org.team.defee.bookmark.repository.BookmarkRepository;
import org.team.defee.member.entity.Member;
import org.team.defee.member.service.MemberService;
import org.team.defee.post.entity.Post;
import org.team.defee.post.repository.PostRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BookmarkService {
    private final BookmarkRepository bookmarkRepository;
    private final MemberService memberService;
    private final PostRepository postRepository;

    @Transactional
    public String createBookmark(String bookmarkName, String memberEmail){
        Member member = memberService.findOneByEmail(memberEmail);
        Bookmark duplicateBookmark = bookmarkRepository.findUserBookmark(member.getId(), bookmarkName);
        if (duplicateBookmark != null) {
            return null;
        }
        Bookmark bookmark = new Bookmark();
        bookmark.setFolderName(bookmarkName);
        bookmark.setMember(member);
        bookmarkRepository.save(bookmark);
        return bookmarkName;
    }

    @Transactional
    public String addBookmark(String memberEmail, String bookmarkName, Long postId){
        Member member = memberService.findOneByEmail(memberEmail);
        Long memberId = member.getId();

        Bookmark bookmark = bookmarkRepository.findUserBookmark(memberId, bookmarkName);
        Post post = postRepository.findById(postId);

        // 이미 북마크에 동일한 포스트가 존재하는지 확인
        if (bookmark.getPosts().contains(post)) {
            return null;
        }

        bookmark.getPosts().add(post);
        post.getBookmarks().add(bookmark);
        bookmarkRepository.save(bookmark);
        return bookmarkName;
    }

    public List<Post> getPostsByBookmark(String memberEmail, String bookmarkName){
        Member member = memberService.findOneByEmail(memberEmail);
        Bookmark bookmark = bookmarkRepository.findUserBookmark(member.getId(), bookmarkName);

        return bookmark.getPosts();
    }

}
