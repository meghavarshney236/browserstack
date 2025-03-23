package org.browserstack;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.URL;
import java.util.*;

import static org.browserstack.Utils.saveImage;

public class BrowserStackTest {
    public static final String USERNAME = "meghavarshney_GBkm2q";
    public static final String ACCESS_KEY = "q7PKp7HmzYsy6fzbzFuE";
    public static final String URL = "https://" + USERNAME + ":" + ACCESS_KEY + "@hub-cloud.browserstack.com/wd/hub";

    public static void main(String[] args) {
        String[] browsers = {"Chrome", "Firefox", "Edge", "Safari", "Opera"};

        List<Thread> threads = new ArrayList<>();
        for (String browser : browsers) {
            Thread thread = new Thread(() -> runTestOnBrowser(browser));
            thread.start();
            threads.add(thread);
        }

        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("Cross-browser testing completed!");
    }

    public static void runTestOnBrowser(String browserName) {
        List<Article> articles = new ArrayList<>();
        try {
            // Setup BrowserStack capabilities
            DesiredCapabilities caps = new DesiredCapabilities();
            caps.setCapability("browser", browserName);
            caps.setCapability("os", "Windows");
            caps.setCapability("os_version", "10");
            caps.setCapability("name", "ElPais BrowserStack Test - " + browserName);
            caps.setCapability("build", "Selenium Web Scraping Test");

            // Initialize Remote WebDriver
            WebDriver driver = new RemoteWebDriver(new URL(URL), caps);
            driver.get("https://elpais.com/");
            // Navigate to "Opinión"
            Thread.sleep(3000);
            System.out.println("\n📢 Running test on " + browserName);
            WebElement acceptSection = driver.findElement(By.cssSelector("#didomi-notice-agree-button > span"));
            acceptSection.click();
            Thread.sleep(3000);
            WebElement opinionSection = driver.findElement(By.partialLinkText("Opinión"));
            opinionSection.click();
            Thread.sleep(3000);

            // Fetch first 5 articles
            List<WebElement> articleElements = driver.findElements(By.xpath("//*[@id='main-content']/div/section/div/article/header/h2/a"));
            Map<String, String> articleMap = new HashMap<>();
            for (WebElement webElement : articleElements) {
                String title = webElement.getText();
                String link = webElement.getAttribute("href");
                articleMap.put(title, link);
            }
            int count = 0;

            for (Map.Entry<String, String> entry : articleMap.entrySet()) {

                String title = entry.getKey();
                String link = entry.getValue();
                // Open article page
                driver.get(link);
                Thread.sleep(3000);

                // Extract content
                List<WebElement> paragraphs = driver.findElements(By.tagName("p"));
                StringBuilder content = new StringBuilder();
                for (int j = 0; j < Math.min(paragraphs.size(), 5); j++) {
                    content.append(paragraphs.get(j).getText()).append("\n");
                }

                // Extract image
                String imageName = "No image";
                try {
                    WebElement imageElement = driver.findElement(By.tagName("img"));
                    String imageUrl = imageElement.getAttribute("src");
                    imageName = saveImage(imageUrl, title);
                } catch (NoSuchElementException e) {
                    System.out.println("No image found for " + title);
                }
                System.out.println("Title: " + title);
                System.out.println("Content: " + content);
                articles.add(new Article(title, content.toString(), imageName));
                count++;
                if (count >= 5) break;
            }

            driver.quit();

            List<String> headers = new ArrayList<>();
            for (Article article : articles) {
                String headerInEnglish = GoogleTranslatorUtil.translate(article.getTitle());
                System.out.println("Title Translation to English: " + headerInEnglish);
                headers.add(headerInEnglish);
            }
            Map<String, Integer> analyserMap = WordFrequencyUtil.analyze(headers);
            System.out.println();
            System.out.println();
            System.out.println("------- WordFreuqencyAnalyzer results ------");
            for (Map.Entry<String, Integer> entry : analyserMap.entrySet()) {
                if (entry.getValue() > 2) {
                    System.out.println(entry.getKey() + " - " + entry.getValue());
                }
            }
            System.out.println(articles);

        } catch (Exception e) {
            System.out.println("Test failed on " + browserName + ": " + e.getMessage());
        }


    }
}
