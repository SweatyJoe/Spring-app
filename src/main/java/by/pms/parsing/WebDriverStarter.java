package by.pms.parsing;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.time.Duration;

/**
 * Class that contain one method for open url
 * and return all page as String.
 */
public class WebDriverStarter {
    public static String start(String url) {
        System.setProperty("webdriver.chrome.driver", "selenium\\chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*"); //, "--no-startup-window"

        WebDriver wDriver = new ChromeDriver(options);
        Duration duration = Duration.ofMillis(8000);
        wDriver.manage().timeouts().pageLoadTimeout(duration);
        wDriver.get(url);
        String result = wDriver.getPageSource();
        wDriver.quit();
        return result;
    }
}
