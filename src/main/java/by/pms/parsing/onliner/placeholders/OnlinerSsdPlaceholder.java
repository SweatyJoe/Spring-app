package by.pms.parsing.onliner.placeholders;

import by.pms.entity.SsdEntity;
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
        SsdEntity entity = urlConvertToToEntity();
    }

    private SsdEntity urlConvertToToEntity() {
        String[] ssdEntityTmp = new String[19];
        Document doc = Jsoup.parse(WebDriverStarter.start(url));
        Elements allTables = doc.select("td");
        if (allTables == null) return null;
        for (int j = 0; j < 19; j++) {
            ssdEntityTmp[j] = "0";
            for (int i = 0; i < allTables.size(); i++) {
                if (SSD_SPEC[j].equals(allTables.get(i).text())) {
                    if (allTables.get(i).text().contains(SSD_SPEC[j])) {
                        if (allTables.get(i + 1).attr("class").equals("i-tip") && allTables.get(i + 1) == null) {
                            ssdEntityTmp[j] = "true";
                            break;
                        }
                        ssdEntityTmp[j] = allTables.get(i + 1).text();
                        break;
                    }
                }
            }
        }
        try {
            return new SsdEntity(
                    ssdName,
                    Double.parseDouble(ssdEntityTmp[0].replace(" ГБ", "")),
                    ssdEntityTmp[1],
                    ssdEntityTmp[2],
                    ssdEntityTmp[3],
                    ssdEntityTmp[4],
                    Integer.parseInt(ssdEntityTmp[5]),
                    ssdEntityTmp[6],
                    ssdEntityTmp[7],
                    Integer.parseInt(ssdEntityTmp[8].replace(" МБайт/с", "").replace(" ", "")),
                    Integer.parseInt(ssdEntityTmp[9].replace(" МБайт/с", "").replace(" ", "")),
                    Integer.parseInt(ssdEntityTmp[10].replace(" IOps", "").replace(" ", "")),
                    Integer.parseInt(ssdEntityTmp[11].replace(" IOps", "").replace(" ", "")),
                    Double.parseDouble(ssdEntityTmp[12].replace(" Вт", "")),
                    Double.parseDouble(ssdEntityTmp[13].replace(" Вт", "")),
                    Integer.parseInt(ssdEntityTmp[14].replace("ч", "").replace(" ", "")),
                    Double.parseDouble(ssdEntityTmp[15].replace(" мм", "")),
                    Boolean.parseBoolean(ssdEntityTmp[16]),
                    Boolean.parseBoolean(ssdEntityTmp[17])
            );
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
