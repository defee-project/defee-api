package org.team.defee.config;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.team.defee.bookmark.entity.Bookmark;
import org.team.defee.bookmark.repository.BookmarkRepository;
import org.team.defee.keyword.entity.Keyword;
import org.team.defee.post.entity.Post;
import org.team.defee.post.enums.Platfrom;
import org.team.defee.post.repository.PostRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class MockDataConfig {

    @Bean
    CommandLineRunner initDatabase(PostRepository postRepository, BookmarkRepository bookmarkRepository) {
        return args -> {
            // Creating mock data for Bookmarks and Keywords
//            List<Bookmark> mockBookmarks = new ArrayList<>();
//            for (int i = 1; i <= 5; i++) {
//                Bookmark bookmark = new Bookmark();
//                bookmark.setFolderName("Bookmark " + i);
//                mockBookmarks.add(bookmarkRepository.save(bookmark));
//            }

//            List<Keyword> mockKeywords = new ArrayList<>();
//            for (int i = 1; i <= 10; i++) {
//                Keyword keyword = new Keyword();
//                keyword.setKeyword("Keyword " + i);
//                mockKeywords.add(keywordRepository.save(keyword));
//            }

            // Creating mock data for Posts
            for (int i = 1; i <= 23; i++) {
                Post post = new Post();
                post.setTitle("Post Title " + i);
                post.setUrl("https://example.com/post-" + i);
                post.setAuthor("Author " + i);
                post.setPlatfrom(Platfrom.values()[i % Platfrom.values().length]); // Cycle through enum values
                post.setDate(LocalDateTime.now().minusDays(i));
                post.setScore((Math.random() * 10)); // Random score between 0 and 10
                post.setThumbnailUrl("https://example.com/thumbnail-" + i);

                // Assign random Bookmarks and Keywords
//                post.setBookmarks(mockBookmarks.subList(0, (i % 5) + 1));
//                post.setKeywords(mockKeywords.subList(0, (i % 10) + 1));

                postRepository.save(post);
            }

            System.out.println("Mock data created successfully!");
        };
    }
}
