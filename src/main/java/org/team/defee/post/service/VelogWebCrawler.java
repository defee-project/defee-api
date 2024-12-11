package org.team.defee.post.service;

import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;
import org.team.defee.post.entity.Post;
import org.team.defee.post.enums.Platfrom;
import org.team.defee.post.repository.PostRepository;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
@RequiredArgsConstructor
public class VelogWebCrawler {
    private final PostService postService;
    private final PostRepository postRepository;

    public void crawlPosts() {
        try {
            List<Post> posts = new ArrayList<>();
            Document doc = Jsoup.connect("https://velog.io/").get();

            Elements elements = doc.select(".PostCard_block");
            for (Element element: elements) {
                String title = element.select("h4").text();
                String url = element.select("a").attr("href");
                String author = element.select(".subinfo.profile span").text();
                String dateTimeString = element.select("time").attr("datetime");
                LocalDateTime date = ZonedDateTime.parse(dateTimeString).toLocalDateTime();

                String score = element.select("span").text();
                String thumbnail = element.select(".thumbnail-wrapper img").attr("src");

                Post post = new Post();
                post.setTitle(title);
                post.setUrl(url);
                post.setAuthor(author);
                post.setPlatfrom(Platfrom.VELOG);
                post.setDate(date);
                post.setScore(1.0f);
                post.setThumbnailUrl(thumbnail);

                postRepository.save(post);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
