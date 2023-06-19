package by.pms.parsing;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/**
 * Class that contain one method for open url
 * and return all page as String.
 */
public class WebDriverStarter {
    public static String start(String url, String priority) {
        System.out.println("URL selenium : " + url);
        System.setProperty("webdriver.chrome.driver", "selenium\\chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*", "--window-size=800,800");
        WebDriver wDriver = new ChromeDriver(options);
        Duration duration = Duration.ofMillis(2000);
        wDriver.manage().timeouts().implicitlyWait(duration);
        wDriver.manage().timeouts().pageLoadTimeout(duration);
        wDriver.manage().timeouts().scriptTimeout(duration);
        try {
            wDriver.get(url);
            if(priority.equals("p")){
                (new WebDriverWait(wDriver, Duration.ofSeconds(2)))
                        .until(ExpectedConditions.presenceOfElementLocated(By.className("js-product-title-link")));
            }
        } catch (Exception e) {
            System.out.println("First exception in web");
            try {
                wDriver.get(url);
            } catch (Exception e2) {
                System.out.println("Second exception in web");
            }
        }
        String result = wDriver.getPageSource();
        wDriver.quit();
        return result;
    }
}
