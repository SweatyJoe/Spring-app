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

/**
 * Класс-поток, занимается парсингом, генерацией ссылок.
 */
public class OnlinerParseThread implements Runnable {
    private static final String[] components = {
            "cpu",
            "ssd",
            "powersupply",
            "videocard",
            "hdd",
            "chassis",
            "motherboard",
            "dram",
            "fan?type_fan%5B0%5D=cpu&type_fan%5Boperation%5D=union&order=price:asc&"
    };
    private static final Map<String, String> CPUElements = new HashMap<>();
    private int componentId = 1;

    public OnlinerParseThread(int componentId) {
        this.componentId = componentId;
    }

    private static String urlGen(int pageNumber, String component) {
        return "https://catalog.onliner.by/" + component + "?page=" + pageNumber;
    }

    public int getComponentId() {
        return componentId;
    }

    public Map<String, String> getCPUElements() {
        return CPUElements;
    }

    /*TODO
     *  MAKE web driver "solo casting" and send this to Thread;
     *  try to dont quit driver and faster-load pages;
     *  сделать массив из component
     * */
    @Override
    public void run() {
        try {
            int pageIterator = 1;
            int lastPage = 2;
            for (; pageIterator < lastPage + 1; pageIterator++) {
                System.setProperty("webdriver.chrome.driver", "selenium\\chromedriver.exe");

                ChromeOptions options = new ChromeOptions();
                options.addArguments("--remote-allow-origins=*"); //, "--no-startup-window"

                WebDriver wDriver = new ChromeDriver(options);
                wDriver.manage().timeouts().pageLoadTimeout(Duration.ofMillis(8000));
                try {
                    wDriver.get(urlGen(pageIterator, components[componentId]));
                } catch (TimeoutException ignore) {
                }
                Document doc = Jsoup.parse(wDriver.getPageSource());
                wDriver.quit();
                Elements elements = doc.select("a.js-product-title-link"); //js-product-title-link  //schema-product__group
                Elements elementsIterator = doc.select("a.schema-pagination__pages-link");
                if (elementsIterator != null | elementsIterator.size() == 0) {
                    if (lastPage == 2) {
                        lastPage = elementsIterator.size();
                    }
                } else {
                    System.out.println("\n\nCant find elements for page count!\n\n");
                    return;
                }
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
