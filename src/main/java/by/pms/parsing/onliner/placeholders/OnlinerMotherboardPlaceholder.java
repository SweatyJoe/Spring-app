package by.pms.parsing.onliner.placeholders;

import by.pms.entity.baseEntity.MotherboardEntity;
import by.pms.parsing.WebDriverStarter;
import by.pms.repository.MotherboardRepository;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class OnlinerMotherboardPlaceholder {
    private static String[] MOTHERBOARD_SPEC = {
        "",
        "",
    };
    private final String name;
    private final String url;
    private final MotherboardRepository repository;

    public OnlinerMotherboardPlaceholder(String name, String url, MotherboardRepository repository) {
        this.name = name;
        this.url = url;
        this.repository = repository;
        fill();
    }

    private void fill() {
        if (repository.findByNameLikeIgnoreCase(name).isEmpty()) {
            MotherboardEntity entity = urlConvertToEntity();

        }

    }

    private MotherboardEntity urlConvertToEntity() {
        String[] motherboardTmp = new String[59];
        Document doc = Jsoup.parse(WebDriverStarter.start(url));
        Elements allTables = doc.select("td");
        for (int j = 0; j < 59; j++) {
            for(int i = 0;i< allTables.size();i++){
                if(allTables.get(i).text().contains(MOTHERBOARD_SPEC[j])){

                }
            }
            if(motherboardTmp[j] == null) motherboardTmp[j] = "0";
        }
        return new MotherboardEntity();
    }
}
