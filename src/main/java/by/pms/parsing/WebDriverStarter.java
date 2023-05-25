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
        //options.setHeadless(true);
        options.addArguments("--remote-allow-origins=*", "--window-size=700,200"); //, "--no-startup-window"  "--window-size=300,300"
        WebDriver wDriver = new ChromeDriver(options);
        wDriver.manage().timeouts().pageLoadTimeout(Duration.ofMillis(10000));
        wDriver.manage().timeouts().scriptTimeout(Duration.ofMillis(1000));
        wDriver.get(url);
        String result = wDriver.getPageSource();
        System.out.println("Page " + url);
        wDriver.quit();
        return result;
    }
}
