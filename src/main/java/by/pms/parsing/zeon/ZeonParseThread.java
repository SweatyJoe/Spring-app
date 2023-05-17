package by.pms.parsing.zeon;

import by.pms.parsing.Components;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;


public class ZeonParseThread implements Runnable {
    private final String[] urlPrefix = {
            "processory",
            "ssd",
            "bloki_pitaniya",
            "videokarty",
            "zhestkie_diski",
            "korpusa",
            "materinskie_platy",
            "operativnaya_pamyat",
            //"sistemy_ohlazhdeniya/?priceFrom=1&priceTo=1107&fp%5B119942%5D%5B%5D=%D0%BA%D1%83%D0%BB%D0%B5%D1%80+%D0%B4%D0%BB%D1%8F
            // +%D0%BF%D1%80%D0%BE%D1%86%D0%B5%D1%81%D1%81%D0%BE%D1%80%D0%B0" //отдельный парсер под охлад
    };
    private final List<Components> elements = new ArrayList<>();
    private final int controlIterator;

    public ZeonParseThread(int iterator) {
        controlIterator = iterator;
    }

    private void getAllCpus(String url) throws SocketTimeoutException {
        try {
            for (int i = 1; true; i++) {
                String tmpUrl = url + i;
                Document doc = Jsoup
                        .connect(tmpUrl)
                        .timeout(4000)
                        .get();
                Elements content = doc.select("div.catalog-item-title");
                Elements costs = doc.select("div.catalog-item-pricemini");

                for (int j = 0; j < content.size(); j++) {
                    elements.add(new Components(content.get(j).text(),
                            content.get(j).child(0).attributes().get("href"),
                            Float.parseFloat(costs.get(j).text().replace(",", ".").
                                    replace(" руб*", ""))));
                }
                Elements hasNextPageElements = doc.select(".next_page");
                if (hasNextPageElements.size() == 1 && i > 2) break;
                else hasNextPageElements.clear();
            }
        } catch (IOException | NullPointerException e) {
            e.printStackTrace();
        }
    }

    public List<Components> getElements() {
        return elements;
    }

    private String URLGenerator(int iterator) {
        String url = "https://www.777555.by/kompyutery_i_seti/kompyutery_i_komplektuyuschie/";
        url = url + urlPrefix[iterator] + "/page";
        return url;
    }

    @Override
    public void run() {
        try {
            getAllCpus(URLGenerator(controlIterator));
        } catch (SocketTimeoutException e) {
            System.out.println("\n Ошибка загрузки сайта!");
            e.printStackTrace();
        }
    }
}
