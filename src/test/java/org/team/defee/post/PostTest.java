package org.team.defee.post;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.team.defee.post.entity.Post;
import org.team.defee.post.repository.PostRepository;
import org.team.defee.post.service.PostService;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PostTest {
    @InjectMocks
    private PostService sut;
    @Mock
    private PostRepository repository;

    @Test
    @DisplayName("findAllPosts 테스트")
    public void testFindAllPosts() {
        // given
        Post post1 = new Post();
        post1.setAuthor("test1");
        post1.setTitle("test1");

        Post post2 = new Post();
        post2.setAuthor("test2");
        post2.setTitle("test2");

        List<Post> mockPosts = Arrays.asList(post1, post2);

        when(repository.findAll()).thenReturn(mockPosts);

        // when
        List<Post> posts = sut.findAllPosts();

        // then
        assertEquals(2, posts.size());
        assertEquals(post1, posts.get(0));
        assertEquals(post2, posts.get(1));
        verify(repository, times(1)).findAll();
    }

    @Test
    @DisplayName("searchPosts 테스트")
    public void testSearchPosts() {
        // given
        String title = "test";

        Post post1 = new Post();
        post1.setAuthor("test1");
        post1.setTitle("test1");

        Post post2 = new Post();
        post2.setAuthor("test2");
        post2.setTitle("test2");
        List<Post> mockPosts = Arrays.asList(post1, post2);

        // when
        when(repository.search(title)).thenReturn(mockPosts);

        // then
        List<Post> posts = sut.searchPosts(title);
        assertEquals(2, posts.size());
        assertEquals(post1, posts.get(0));
        verify(repository, times(1)).search(title);
    }

    @Test
    @DisplayName("findPostsByKeyword 테스트")
    public void testFindPostsByKeyword() {
        // given
        String keyword = "new";
        int page = 1;
        Post post1 = new Post();
        post1.setAuthor("test1");
        post1.setTitle("test1");

        Post post2 = new Post();
        post2.setAuthor("test2");
        post2.setTitle("test2");
        List<Post> mockPosts = Arrays.asList(post1, post2);

        when(repository.findNewPosts(page)).thenReturn(mockPosts);

        // when
        List<Post> posts = sut.findPostsByKeyword(keyword, page);

        // then
        assertEquals(2, posts.size());
        assertEquals(post1, posts.get(0));
        assertEquals(post2, posts.get(1));
        verify(repository, times(1)).findNewPosts(page);
    }
}
