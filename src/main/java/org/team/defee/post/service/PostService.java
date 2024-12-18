package org.team.defee.post.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.team.defee.post.entity.Post;
import org.team.defee.post.repository.PostRepository;

import java.util.List;
import java.util.Objects;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;

    public List<Post> findAllPosts () {
        return postRepository.findAll();
    }

    public List<Post> findPostsByKeyword(String keyword, int page) {
        if (Objects.equals(keyword, "new")){
            return postRepository.findNewPosts(page);
        }else {
            return postRepository.findPostsByKeyword(keyword, page);
        }
    }

    public List<Post> findPostsByBookmark(String bookmark, int page) {
        return postRepository.findPostsByBookmark(bookmark, page);
    }
}
