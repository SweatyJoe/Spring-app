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
            "sistemy_ohlazhdeniya"
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
                if (controlIterator == 8) {
                    tmpUrl += "/?priceFrom=1&priceTo=2010&fp%5B119942%5D%5B%5D=кулер+для+процессора";
                    System.out.println("pref added");
                }
                Document doc = Jsoup
                        .connect(tmpUrl)
                        .timeout(5000)
                        .get();
                Elements content = doc.select("div.catalog-item-title");
                Elements costs = doc.select("div.catalog-item-pricemini");

                for (int j = 0; j < content.size(); j++) {
                    String textContent = content.get(j).text();
                    if (!textContent.contains("Кулер для процессора") && controlIterator == 8)
                        textContent = "Кулер для процессора " + textContent;
                    if (!textContent.contains("Оперативная память") && controlIterator == 7
                            && !textContent.contains("Модуль памяти") && !textContent.contains("Память оперативная"))
                        textContent = "Оперативная память " + textContent;
                    if (!textContent.contains("Материнская плата") && controlIterator == 6)
                        textContent = "Материнская плата " + textContent;
                    if (!textContent.contains("Корпус") && controlIterator == 5) textContent = "Корпус " + textContent;
                    if ((!textContent.contains("Жесткий диск") || !textContent.contains("Гибридный")) && controlIterator == 4)
                        textContent = "Жесткий диск " + textContent;
                    if (!textContent.contains("Видеокарта") && controlIterator == 3)
                        textContent = "Видеокарта " + textContent;
                    if (controlIterator == 2 && !textContent.contains("Блок питания"))
                        textContent = "Блок питания " + textContent;
                    if (!textContent.contains("SSD") && controlIterator == 1) textContent = "SSD " + textContent;
                    if (!textContent.contains("Процессор") && controlIterator == 0)
                        textContent = "Процессор " + textContent;
                    elements.add(new Components(textContent
                            .replace("Модуль памяти", "Оперативная память")
                            .replace("Память оперативная", "Оперативная память")
                            .replace("Память", "Оперативная память")
                            .replace("Накопитель ", ""),
                            content.get(j).child(0).attributes().get("href"),
                            Float.parseFloat(costs.get(j).text().replace(",", ".").
                                    replace(" руб*", "").replace(" ", ""))));
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
