package org.team.defee.post.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.team.defee.post.entity.Post;
import org.team.defee.post.repository.PostRepository;
import org.team.defee.redis.RedisService;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final RedisService redisService;
    private final ObjectMapper jacksonObjectMapper;

    public List<Post> findAllPosts () {
        String cacheKey = "postList";
        String cachedData = redisService.getValues(cacheKey);

        if (!"false".equals(cachedData)){
            return deserializePosts(cachedData);
        }

        List<Post> postList = postRepository.findAll();
        redisService.setValues(cacheKey, serializePosts(postList));
        return postList;
    }

    public List<Post> searchPosts(String word) {
        return postRepository.search(word);
    }

//    public List<Post> findPostsByKeyword(String keyword, int page) {
//        if (Objects.equals(keyword, "new")){
//            return postRepository.findNewPosts(page);
//        }else {
//            return postRepository.findPostsByKeyword(keyword, page);
//        }
//    }
//
//    public List<Post> findPostsByBookmark(String bookmark, int page) {
//        return postRepository.findPostsByBookmark(bookmark, page);
//    }

    private String serializePosts(List<Post> posts) {
        try {
            return jacksonObjectMapper.writeValueAsString(posts);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error serializing posts", e);
        }
    }

    private List<Post> deserializePosts(String data) {
        try {
            return Arrays.asList(jacksonObjectMapper.readValue(data, Post[].class));
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error deserializing posts", e);
        }
    }
}
