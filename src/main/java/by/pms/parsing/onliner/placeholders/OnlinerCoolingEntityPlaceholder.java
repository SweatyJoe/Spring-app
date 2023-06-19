package by.pms.parsing.onliner.placeholders;

import by.pms.entity.baseEntity.CoolingEntity;
import by.pms.parsing.WebDriverStarter;
import by.pms.repository.CoolingRepository;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.transaction.TransactionSystemException;

public class OnlinerCoolingEntityPlaceholder {
    private final String[] COOLING_SPEC = {
            "Охлаждение ",
            "Сокет ",
            "Материал радиатора ",
            "Диаметр вентилятора ",
            "Количество вентиляторов ",
            "Минимальная скорость вращения",
            "Максимальная скорость вращения",
            "Максимальный воздушный поток ",
            "Контроль скорости вращения (PWM)",
            "Тип подключения ",
            "Тип подключения подсветки",
            "LED-подсветка",
            "Максимальный уровень шума ",
            "Ширина ",
            "Глубина ",
            "Высота (толщина)",
            "Рассеиваемая мощность "
    };
    private final String[] POMP_SPEC = {
            "Минимальная скорость вращения",
            "Максимальная скорость вращения",
            "LED-подсветка",
    };

    private final String name;
    private final String url;
    private final CoolingRepository repository;

    public OnlinerCoolingEntityPlaceholder(String name, String url, CoolingRepository repository) {
        this.name = name;
        this.url = url;
        this.repository = repository;
        fill();
    }

    private void fill() {
        if (repository.findByNameLikeIgnoreCase(name).isEmpty()) {
            CoolingEntity entity = urlConvertToEntity();
            try {
                if (entity != null) repository.save(entity);
            } catch (TransactionSystemException e) {
                System.out.println("Transaction exception with " + name);
            }
        }
    }

    private CoolingEntity urlConvertToEntity() {
        String[] coolingTmp = new String[17];
        String[] pompTmp = new String[3];
        Document doc = Jsoup.parse(WebDriverStarter.start(url, "c"));
        Elements elem = doc.select("tbody");
        Elements pompTd = new Elements(), otherTd = new Elements();
        for (var tbody : elem) {
            if (tbody.text().contains("Помпа")) {
                pompTd = tbody.select("td");
            } else {
                try {
                    otherTd.addAll(tbody.select("td"));
                } catch (NullPointerException ignore) {
                }
            }
        }
        for (int j = 0; j < 17; j++) {
            for (int i = 0; i < otherTd.size(); i++) {
                if (otherTd.get(i).text().contains(COOLING_SPEC[j])) {
                    try {
                        if (!otherTd.get(i + 1).getElementsByClass("i-tip").isEmpty()) {
                            if (otherTd.get(i + 1).text().isBlank()) {
                                coolingTmp[j] = "1";
                                break;
                            } else coolingTmp[j] = otherTd.get(i + 1).text();
                        } else {
                            if (otherTd.get(i + 1).text() != null || !otherTd.get(i + 1).text().isEmpty()) {
                                coolingTmp[j] = otherTd.get(i + 1).text();
                            } else coolingTmp[j] = "0";
                        }
                    } catch (IndexOutOfBoundsException ignore) {
                    }
                }
            }
            if (coolingTmp[j] == null) coolingTmp[j] = "0";
        }
        for (int j = 0; j < 3; j++) {
            for (int i = 0; i < pompTd.size(); i++) {
                if (pompTd.get(i).text().contains(POMP_SPEC[j])) {
                    if (!pompTd.get(i + 1).getElementsByClass("i-tip").isEmpty()) {
                        if (pompTd.get(i + 1).text().isEmpty()) {
                            pompTmp[j] = "1";
                        } else pompTmp[j] = pompTd.get(i + 1).text();
                    } else {
                        if (pompTd.get(i + 1).text() != null || !pompTd.get(i + 1).text().isEmpty()) {
                            pompTmp[j] = pompTd.get(i + 1).text();
                        } else pompTmp[j] = "0";
                    }
                }
            }
            if (pompTmp[j] == null) pompTmp[j] = "0";
        }

        try {
            return new CoolingEntity(
                    name,
                    coolingTmp[0],
                    coolingTmp[1],
                    coolingTmp[2],
                    Integer.parseInt(coolingTmp[3].replace(" мм", "")
                            .replaceAll("\\(.+\\)", "").replace(" ", "")),
                    Integer.parseInt(coolingTmp[4].replaceAll("\\(.+\\)", "")
                            .replaceAll("\\(.+\\)", "").replace(" ", "")),
                    Integer.parseInt(coolingTmp[5].replace(" об/мин", "")
                            .replaceAll("\\(.+\\)", "").replace(" ", "")),
                    Integer.parseInt(coolingTmp[6].replace(" об/мин", "")
                            .replaceAll("\\(.+\\)", "").replace(" ", "")),
                    Double.parseDouble(coolingTmp[7].replace(" CFM", "")
                            .replaceAll("\\(.+\\)", "").replace(" ", "")),
                    Boolean.parseBoolean(coolingTmp[8].equals("1") ? "true" : "false"),
                    coolingTmp[9],
                    coolingTmp[10],
                    coolingTmp[11],
                    Double.parseDouble(coolingTmp[12].replace(" дБ", "")
                            .replaceAll("\\(.+\\)", "").replace(" ", "")),
                    Integer.parseInt(pompTmp[0].replace(" об/мин", "")
                            .replace(" ", "")),
                    Integer.parseInt(pompTmp[1].replace(" об/мин", "")
                            .replaceAll("\\(.+\\)", "").replace(" ", "")),
                    Boolean.parseBoolean(pompTmp[2].equals("1") ? "true" : "false"),
                    Double.parseDouble(coolingTmp[13].replace(" мм", "")
                            .replaceAll("\\(.+\\)", "").replace(" ", "")),
                    Double.parseDouble(coolingTmp[14].replace(" мм", "")
                            .replaceAll("\\(.+\\)", "").replace(" ", "")),
                    Double.parseDouble(coolingTmp[15].replace(" мм", "")
                            .replaceAll("\\(.+\\)", "").replace(" ", "")),
                    Integer.parseInt(coolingTmp[16].replace(" Вт", "")
                            .replaceAll("\\(.+\\)", "").replace(" ", ""))
            );
        } catch (Exception e) {
            System.out.println("Exception in CoolingPlaceholder, id = " + url);
            e.printStackTrace();
        }
        return null;
    }
}
