package by.pms.parsing.onliner;

import by.pms.repository.CpuRepository;

public class OnlinerCostsRecorder {
    public OnlinerCostsRecorder(CpuRepository repository) {
        /*Iterable<CpuEntity> entities = repository.findAll();
        for (var s : entities) {
            try {
                Document doc = Jsoup.parse(WebDriverStarter.start(s.getUrl() + "/prices?region=gomel&order=price%3Aasc", "c"));
                Elements elements = doc.select(".offers-list__item");
                for (var item : elements) {
                    System.out.println(item.getElementsByClass("offers-list__part_price").text());
                }
            } catch (NullPointerException e) {
                e.getMessage();
            }
        }*/
    }
}
