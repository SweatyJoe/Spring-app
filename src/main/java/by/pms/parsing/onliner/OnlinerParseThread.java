package by.pms.parsing.onliner;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

public class OnlinerParseThread implements Runnable {
    private static Map<String, String> CPUElements = new HashMap<>();

    private static String urlGen(int pageNumber, String component) {
        return "https://catalog.onliner.by/" + component + "?page=" + pageNumber;
    }

    public Map<String, String> getCPUElements() {
        return CPUElements;
    }

    /*TODO
    *  MAKE web driver solo casting and send this to Thread;
    *  try to dont quit driver and faster-load pages;
    *
    * */
    @Override
    public void run() {
        try {
            int pageIterator = 1;
            int lastPage = 1;
            for (; pageIterator < lastPage + 1; pageIterator++) {
                System.setProperty("webdriver.chrome.driver", "selenium\\chromedriver.exe");

                ChromeOptions options = new ChromeOptions();
                options.addArguments("--remote-allow-origins=*"); //, "--no-startup-window"

                WebDriver wDriver = new ChromeDriver(options);
                Duration duration = Duration.ofMillis(8000);
                wDriver.manage().timeouts().pageLoadTimeout(duration);
                try {
                    wDriver.get(urlGen(pageIterator, "cpu"));
                } catch (TimeoutException ignore) {
                }
                Document doc = Jsoup.parse(wDriver.getPageSource());
                wDriver.quit();
                Elements elements = doc.select("a.js-product-title-link"); //js-product-title-link  //schema-product__group
                Elements elementsIterator = doc.select("a.schema-pagination__pages-link");
                /*if (elementsIterator != null | elementsIterator.size() == 0) {
                    if (lastPage == 2) {
                        lastPage = elementsIterator.size();
                    }
                } else {
                    System.out.println("\n\nCant find elements for page count!\n\n");
                    return;
                }*/
                for (Element s : elements) {
                    CPUElements.put(s.text(), s.attr("href"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error of thread" + Thread.currentThread().getName());
        }
    }
}
