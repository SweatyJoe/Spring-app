package by.pms.parsing.onliner.placeholders;

import by.pms.entity.baseEntity.SsdEntity;
import by.pms.parsing.WebDriverStarter;
import by.pms.repository.SsdRepository;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class OnlinerSsdPlaceholder {
    private static final String[] SSD_SPEC = {
            "Объём",
            "Форм-фактор",
            "Интерфейс",
            "Тип микросхем Flash",
            "Контроллер",
            "Размеры устройств M.2",
            "Ресурс записи",
            "Аппаратное шифрование",
            "Скорость последовательного чтения",
            "Скорость последовательной записи",
            "Средняя скорость случайного чтения",
            "Средняя скорость случайной записи",
            "Энергопотребление (чтение/запись)",
            "Энергопотребление (ожидание)",
            "Время наработки на отказ (МТBF)",
            "Толщина",
            "Охлаждение",
            "Подсветка"
    };
    private final String ssdName;
    private final String url;
    private final SsdRepository repository;

    public OnlinerSsdPlaceholder(String ssdName, String url, SsdRepository repository) {
        this.ssdName = ssdName;
        this.url = url;
        this.repository = repository;
        fill();
    }

    private void fill() {
        if (repository.findByNameLikeIgnoreCase(ssdName).isEmpty()) {
            SsdEntity entity = urlConvertToToEntity();
            try {
                repository.save(entity);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private SsdEntity urlConvertToToEntity() {
        String[] ssdEntityTmp = new String[18];
        Document doc = Jsoup.parse(WebDriverStarter.start(url));
        Elements allTables = doc.select("td");
        if (allTables == null) return null;
        for (int j = 0; j < 18; j++) {
            for (int i = 0; i < allTables.size(); i++) {
                if (allTables.get(i).text().contains(SSD_SPEC[j])) {
                    if (!allTables.get(i + 1).getElementsByClass("i-tip").isEmpty() && allTables.get(i + 1) == null) {
                        ssdEntityTmp[j] = "true";
                        break;
                    }
                    if (allTables.get(i + 1).text().isEmpty()) {
                        ssdEntityTmp[j] = "0";
                    } else {
                        if (allTables.get(i + 1).text().contains("ТБ")) {
                            ssdEntityTmp[j] = String.valueOf(Double.parseDouble(allTables.get(i + 1)
                                    .text().replace(" ТБ", "")) * 1000);
                            break;
                        }
                        ssdEntityTmp[j] = allTables.get(i + 1).text();
                    }
                    break;
                }
            }
            if (ssdEntityTmp[j] == null) ssdEntityTmp[j] = "0";
        }
        try {
            return new SsdEntity(
                    ssdName,
                    Double.parseDouble(ssdEntityTmp[0].replace(" ГБ", "")
                            .replace(".", "").replaceAll("\\(.+\\)", "")),
                    ssdEntityTmp[1],
                    ssdEntityTmp[2],
                    ssdEntityTmp[3],
                    ssdEntityTmp[4],
                    Integer.parseInt(ssdEntityTmp[5]),
                    ssdEntityTmp[6],
                    ssdEntityTmp[7],
                    Integer.parseInt(ssdEntityTmp[8].replace(" МБайт/с", "")
                            .replace(" ", "").replaceAll("\\(.+\\)", "")),
                    Integer.parseInt(ssdEntityTmp[9].replace(" МБайт/с", "")
                            .replace(" ", "").replaceAll("\\(.+\\)", "")),
                    Integer.parseInt(ssdEntityTmp[10].replace(" IOps", "")
                            .replace(" ", "").replaceAll("\\(.+\\)", "")),
                    Integer.parseInt(ssdEntityTmp[11].replace(" IOps", "")
                            .replace(" ", "").replaceAll("\\(.+\\)", "")),
                    Double.parseDouble(ssdEntityTmp[12].replace(" Вт", "")
                            .replaceAll("\\(.+\\)", "")),
                    Double.parseDouble(ssdEntityTmp[13].replace(" Вт", "")
                            .replaceAll("\\(.+\\)", "")),
                    Integer.parseInt(ssdEntityTmp[14].replace("ч", "")
                            .replace(" ", "")),
                    Double.parseDouble(ssdEntityTmp[15].replace(" мм", "")
                            .replaceAll("\\(.+\\)", "")),
                    Boolean.parseBoolean(ssdEntityTmp[16]),
                    Boolean.parseBoolean(ssdEntityTmp[17])
            );
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
