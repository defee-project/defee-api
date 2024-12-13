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
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Component;
import org.team.defee.post.entity.Post;
import org.team.defee.post.enums.Platfrom;
import org.team.defee.post.repository.PostRepository;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

@Component
@RequiredArgsConstructor
public class VelogWebCrawler {
    private final PostService postService;
    private final PostRepository postRepository;

    public void crawlPosts() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless"); // 브라우저를 보이지 않게 실행
        WebDriver driver = new ChromeDriver(options);

        driver.get("https://velog.io/");
        System.out.println("test for crawling");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector("h4.PostCard_h4__6dTLA")));

        List<WebElement> listItems = driver.findElements(By.cssSelector("ul.PostCardGrid_block__AcTqY > li"));

        // 각 li 요소 내에서 h4 태그를 찾아서 제목 출력
        for (int i = 0; i < listItems.size() - 1; i++) {
            WebElement listItem = listItems.get(i);
            try {
                // li 요소 내의 h4 태그를 찾기
                WebElement titleElement = listItem.findElement(By.cssSelector("h4.PostCard_h4__6dTLA"));
                WebElement urlElement = listItem.findElement(By.cssSelector("a.VLink_block__Uwj4P"));
                WebElement authorElement = listItem.findElement(By.cssSelector("b"));
//                WebElement dateElement = listItem.findElement(By.cssSelector("PostCard_subInfo__KqVkC > span"));
                WebElement thumbnailElement = listItem.findElement(By.cssSelector("div.RatioImage_block__iDP9y > img"));
                WebElement likeElement = listItem.findElement(By.cssSelector("div.PostCard_likes___lp_I"));

                double score = this.calculateScore(Integer.valueOf(likeElement.getText()));
                LocalDateTime date = LocalDateTime.now();
                ;
                Post post = new Post();
                post.setTitle(titleElement.getText());
                post.setUrl(urlElement.getAttribute("href"));
                post.setAuthor(authorElement.getText());
                post.setPlatfrom(Platfrom.VELOG);
                post.setDate(date);
                post.setScore(score);
                post.setThumbnailUrl(thumbnailElement.getAttribute("src"));

                postRepository.save(post);

            } catch (Exception e) {
                // h4 태그가 없는 경우 예외 처리
                System.out.println("No title found for this item.");
            }
        }
    }

    private double calculateScore(int hearts) {
        double score = 0.0;
        if (hearts > 100) {
            score = 10.0;
        } else {
            score = hearts / 10.0;
        }
        return score;
    }
}
