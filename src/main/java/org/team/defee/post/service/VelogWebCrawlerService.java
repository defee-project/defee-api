package org.team.defee.post.service;

import jakarta.annotation.PostConstruct;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.team.defee.post.entity.Post;
import org.team.defee.post.enums.Platfrom;
import org.team.defee.post.repository.PostRepository;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class VelogWebCrawlerService {

    private final PostRepository postRepository;
    private WebDriver driver;
    private ChromeOptions options;

    private final String WEB_DRIVER_ID = "webdriver.chrome.driver";
    private final String WEB_DRIVER_PATH = "/usr/local/bin/chromedriver";  // chromedriver 경로

    private String base_url;

    // 생성자에서 의존성 주입 받기
    @Autowired
    public VelogWebCrawlerService(PostRepository postRepository) {
        this.postRepository = postRepository;
        initCrawler();
    }

    // 크롤러 초기화 메서드
    @PostConstruct
    private void initCrawler() {
        // ChromeDriver 설정
        System.setProperty(WEB_DRIVER_ID, WEB_DRIVER_PATH);

        options = new ChromeOptions();
        options.addArguments("--disable-popup-blocking");
        options.addArguments("--disable-default-apps");
        options.addArguments("--disable-notifications");
        options.addArguments("--disable-blink-features=AutomationControlled");
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--single-process");
        options.addArguments("--headless");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--ignore-ssl-errors=yes");
        options.addArguments("--ignore-certificate-errors");
        options.addArguments("--remote-debugging-port=9222");

        base_url = "https://velog.io";
        driver = new ChromeDriver(options);
        driver.get(base_url);
    }

    // 크롤링 시작 메서드
    public void crawlPosts() {
        // 대기 조건 설정 (10초)
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector("h4.PostCard_h4__6dTLA")));

        // 크롤링할 목록 항목들
        List<WebElement> listItems = driver.findElements(By.cssSelector("ul.PostCardGrid_block__AcTqY > li"));

        // 각 항목을 순차적으로 처리
        for (int i = 0; i < listItems.size() - 1; i++) {
            WebElement listItem = listItems.get(i);
            try {
                WebElement titleElement = listItem.findElement(By.cssSelector("h4.PostCard_h4__6dTLA"));

                // 제목이 이미 존재하면 건너뛰기
                if (!postRepository.findPostByTitle(titleElement.getText()).isEmpty()) {
                    continue; // 이미 존재하는 포스트는 저장하지 않음
                }

                WebElement urlElement = listItem.findElement(By.cssSelector("a.VLink_block__Uwj4P"));
                WebElement authorElement = listItem.findElement(By.cssSelector("b"));
                WebElement thumbnailElement = listItem.findElement(By.cssSelector("div.RatioImage_block__iDP9y > img"));
                WebElement likeElement = listItem.findElement(By.cssSelector("div.PostCard_likes___lp_I"));

                // 좋아요 수로 점수 계산
                double score = this.calculateScore(Integer.parseInt(likeElement.getText()));
                LocalDateTime date = LocalDateTime.now();

                // Post 객체 생성 및 저장
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
                System.out.println("Error processing post item: " + e.getMessage());
            }
        }
    }

    // 좋아요 수로 점수 계산
    private double calculateScore(int hearts) {
        if (hearts > 100) {
            return 10.0;
        } else {
            return hearts / 10.0;
        }
    }

    // 크롤러 종료 처리 (자원 정리)
    public void shutdown() {
        if (driver != null) {
            driver.quit(); // WebDriver 종료
        }
    }
}
