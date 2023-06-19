package by.pms.parsing.onliner;

import by.pms.parsing.WebDriverStarter;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

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
            "fan?type_fan%5B0%5D=cpu&type_fan%5Boperation%5D=union"
    };
    private static final Map<String, String> CPUElements = new HashMap<>();
    private int componentId = 1;

    public OnlinerParseThread(int componentId) {
        this.componentId = componentId;
    }

    private static String urlGen(int pageNumber, String component) {
        if(component.equals("fan?type_fan%5B0%5D=cpu&type_fan%5Boperation%5D=union")){
            return "https://catalog.onliner.by/" + component + "&page=" + pageNumber;
        }
        return "https://catalog.onliner.by/" + component + "?page=" + pageNumber;
    }

    public Map<String, String> getCPUElements() {
        return CPUElements;
    }

    @Override
    public void run() {
        try {
            int pageIterator = 1;
            int lastPage = 2;
            for (; pageIterator < lastPage + 1; pageIterator++) {
                Document doc = Jsoup.parse(WebDriverStarter.start(urlGen(pageIterator, components[componentId]), "p"));
                Elements images = doc.select("div.schema-product__image");
                Elements elements = doc.select("a.js-product-title-link"); //js-product-title-link  //schema-product__group
                Elements elementsIterator = doc.select("a.schema-pagination__pages-link");
                if (elementsIterator != null || elementsIterator.size() == 0) {
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
