package org.team.defee.post.service;

import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
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

        System.setProperty("webdriver.chrome.driver", "chromedriver-win64"); // chromedriver 경로 설정
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless"); // 브라우저를 보이지 않게 실행
        WebDriver driver = new ChromeDriver(options);

        try {
//            List<Post> posts = new ArrayList<>();
//            Document doc = Jsoup.connect("https://velog.io/").get();

            driver.get("https://velog.io/");

            System.out.println("test for crawling");
            List<WebElement> listItems = driver.findElements(By.cssSelector("ul.PostCardGrid_block__AcTqY > li"));            // Elements listItems = doc.getElementsByClass("PostCardGrid_block__AcTqY");

            for (WebElement listItem: listItems) {
                System.out.println(listItem);
//                String title = element.select("h4").text();
//                String url = element.select("a").attr("href");
//                String author = element.select(".subinfo.profile span").text();
//                String dateTimeString = element.select("time").attr("datetime");
//                LocalDateTime date = ZonedDateTime.parse(dateTimeString).toLocalDateTime();
//
//                String score = element.select("span").text();
//                String thumbnail = element.select(".thumbnail-wrapper img").attr("src");

//                Post post = new Post();
//                post.setTitle(title);
//                post.setUrl(url);
//                post.setAuthor(author);
//                post.setPlatfrom(Platfrom.VELOG);
//                post.setDate(date);
//                post.setScore(1.0f);
//                post.setThumbnailUrl(thumbnail);
//
//                postRepository.save(post);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
