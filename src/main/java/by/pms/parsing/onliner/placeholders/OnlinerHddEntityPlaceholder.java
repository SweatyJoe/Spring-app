package by.pms.parsing.onliner.placeholders;

import by.pms.entity.baseEntity.HddEntity;
import by.pms.parsing.WebDriverStarter;
import by.pms.repository.HddRepository;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class OnlinerHddEntityPlaceholder {
    private final String[] HDD_SPEC = {
            "Объём",
            "Форм-фактор",
            "Интерфейс",
            "Скорость вращения шпинделя",
            "Буфер",
            "Аппаратное шифрование",
            "Размер сектора",
            "Уровень шума при чтении/записи",
            "Уровень шума в режиме ожидания",
            "Ударная нагрузка при работе",
            "Ударная нагрузка в нерабочем состоянии",
            "Энергопотребление (чтение/запись)",
            "Энергопотребление (ожидание)",
            "Толщина"
    };

    public OnlinerHddEntityPlaceholder(String name, String url, HddRepository repository) {
        try {
            HddEntity entity = urlConverterToEntity(name, url);
            if (entity != null) repository.save(entity);
        } catch (Exception ignore) {
        }
    }

    private HddEntity urlConverterToEntity(String name, String url) {
        String[] hddTmp = new String[14];
        Document doc = Jsoup.parse(WebDriverStarter.start(url, "c"));
        Elements elements = doc.select("tbody");
        Elements secondElements = elements.select("td");
        for (int i = 0; i < 14; i++) {
            for (int j = 0; j < secondElements.size(); j++) {
                if (secondElements.get(j).text().contains(HDD_SPEC[i])) {
                    try {
                        if (!secondElements.get(j + 1).text().isEmpty()) {
                            hddTmp[i] = secondElements.get(j + 1).text();
                        } else {
                            if (!secondElements.get(j + 1).getElementsByClass("i-tip").isEmpty()) {
                                hddTmp[i] = "1";
                            } else hddTmp[i] = "0";
                        }
                    } catch (Exception ignore) {
                    }
                }
            }
            if (hddTmp[i] == null)
                hddTmp[i] = "0";
        }
        if (hddTmp[0].contains("ТБ")) {
            hddTmp[0] = String.valueOf(Integer.valueOf(hddTmp[0].replace(" ТБ", "")) * 1000);
        }
        try {
            return new HddEntity(
                    name,
                    Integer.parseInt(hddTmp[0].replace(" ГБ", "")),
                    hddTmp[1],
                    hddTmp[2],
                    Integer.parseInt(hddTmp[3].replace(" об/мин", "").replace(" ", "")),
                    Integer.parseInt(hddTmp[4].replace(" МБ", "")),
                    Boolean.parseBoolean(hddTmp[5]),
                    hddTmp[6],
                    Double.parseDouble(hddTmp[7].replace(" дБ", "")),
                    Double.parseDouble(hddTmp[8].replace(" дБ", "")),
                    Integer.parseInt(hddTmp[9].replace(" G", "")),
                    Integer.parseInt(hddTmp[10].replace(" G", "")),
                    Double.parseDouble(hddTmp[11].replace(" Вт", "")),
                    Double.parseDouble(hddTmp[12].replace(" Вт", "")),
                    Double.parseDouble(hddTmp[13].replace(" мм", ""))
            );
        } catch (Exception e) {
            return null;
        }
    }
}
