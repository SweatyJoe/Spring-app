package by.pms.parsing.onliner.placeholders;

import by.pms.entity.baseEntity.VideoCardEntity;
import by.pms.parsing.WebDriverStarter;
import by.pms.repository.VideoCardRepository;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.transaction.TransactionSystemException;

public class OnlinerVideoCardPlaceholder {
    private final String[] VIDEO_SPEC = {
            "Интерфейс ",
            "Производитель графического процессора ",
            "Графический процессор ",
            "«Разогнанная» версия ",
            "Трассировка лучей ",
            "Защита от майнинга (LHR) ",
            "Внешняя видеокарта ",
            "Базовая (референсная) частота графического процессора ",
            "Максимальная частота графического процессора ",
            "Количество потоковых процессоров ",
            "Количество RT-ядер ",
            "Видеопамять ",
            "Тип видеопамяти ",
            "Эффективная частота памяти ",
            "Пропускная способность памяти ",
            "Ширина шины памяти ",
            "Поддержка DirectX ",
            "Поддержка SLI/CrossFire ",
            "Разъёмы питания",
            "Энергопотребление",
            "Рекомендуемый блок питания ",
            "Охлаждение ",
            "Толщина системы охлаждения ",
            "Количество вентиляторов ",
            "Длина видеокарты ",
            "Высота видеокарты ",
            "Толщина видеокарты",
            "Низкопрофильная (Low Profile) ",
            "Функциональные особенности ",
            "VGA (D-Sub) ",
            "DVI ",
            "HDMI ",
            "Версия HDMI",
            "mini HDMI ",
            "DisplayPort ",
            "Версия DisplayPort",
            "mini Display Port ",
            "VESA Stereo ",
            "USB Type-C "
    };
    private final String name;
    private final String url;
    private final VideoCardRepository repository;

    public OnlinerVideoCardPlaceholder(String name, String url, VideoCardRepository repository) {
        this.name = name;
        this.url = url;
        this.repository = repository;
        fill();
    }

    private void fill() {
        if (repository.findByNameLikeIgnoreCase(name).isEmpty()) {
            VideoCardEntity entity = urlConvertToEntity();
            try {
                if (entity != null) repository.save(entity);
            } catch (TransactionSystemException e) {
                System.out.println("Transaction exception in Video Card.");
            }
        }
    }

    private VideoCardEntity urlConvertToEntity() {
        String[] videoCardTmp = new String[39];
        Document doc = Jsoup.parse(WebDriverStarter.start(url, "c"));
        Elements allTables = doc.select("td");
        try {
            for (int j = 0; j < 39; j++) {
                for (int i = 0; i < allTables.size(); i++) {
                    if (allTables.get(i).text().contains(VIDEO_SPEC[j])) {
                        try {
                            if (allTables.get(i).text().contains("Энергопотребление")
                                    && !allTables.get(i).text().equals("Энергопотребление ")) {
                                videoCardTmp[j] = allTables.get(i).child(1).text()
                                        .replace("Энергопотребление - ", "")
                                        .replace(".", "");
                                break;
                            }
                            if (allTables.get(i + 1).text().replaceAll("\\(.+\\)", "").isEmpty()
                                    && allTables.get(i + 1).getElementsByClass("i-tip").isEmpty()) {
                                videoCardTmp[j] = "0";
                                break;
                            }
                            if ((allTables.get(i + 1).text() == null || allTables.get(i + 1).text().isEmpty())
                                    && !allTables.get(i + 1).getElementsByClass("i-tip").isEmpty()) {
                                videoCardTmp[j] = "1";
                                break;
                            } else if (allTables.get(i + 1).text() != null || !allTables.get(i + 1).text().isEmpty()) {
                                if (allTables.get(i + 1).text().contains("Основные")) break;
                                videoCardTmp[j] = allTables.get(i + 1).text();
                                break;
                            } else {
                                videoCardTmp[j] = "0";
                                break;
                            }
                        } catch (IndexOutOfBoundsException oops) {
                            break;
                        }
                    }
                }
                if (j == 11) {
                    if (videoCardTmp[j].contains("Мб")) {
                        videoCardTmp[j] = String.valueOf(Double.parseDouble(videoCardTmp[j]
                                .replace(" Мб", "")) / 1000);
                    }
                }
                if (j == 37) {
                    if (videoCardTmp[j] != null &&
                            videoCardTmp[j].replaceAll("\\(.+\\)", "").isBlank()) {
                        videoCardTmp[j] = "1";
                    }
                }
                if (videoCardTmp[j] == null || videoCardTmp[j].isEmpty()) videoCardTmp[j] = "0";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            return new VideoCardEntity(
                    name,
                    videoCardTmp[0],
                    videoCardTmp[1],
                    videoCardTmp[2],
                    Boolean.parseBoolean(videoCardTmp[3]),
                    Boolean.parseBoolean(videoCardTmp[4]),
                    Boolean.parseBoolean(videoCardTmp[5]),
                    Boolean.parseBoolean(videoCardTmp[6]),
                    Integer.parseInt(videoCardTmp[7].replace(" МГц", "")
                            .replace(" ", "").replaceAll("\\(.+\\)", "")),
                    Integer.parseInt(videoCardTmp[8].replace(" МГц", "")
                            .replace(" ", "").replaceAll("\\(.+\\)", "")),
                    Integer.parseInt(videoCardTmp[9].replace(" ", "")),
                    Integer.parseInt(videoCardTmp[10].replace(" ", "")),
                    Double.parseDouble(videoCardTmp[11].replace(" ГБ", "")),
                    videoCardTmp[12],
                    Integer.parseInt(videoCardTmp[13].replace(" МГц", "")
                            .replace(" ", "")),
                    Double.parseDouble(videoCardTmp[14].replace(" ГБ/с", "")),
                    Integer.parseInt(videoCardTmp[15].replace(" бит", "")
                            .replaceAll("\\(.+\\)", "").replace(" ", "")),
                    videoCardTmp[16],
                    Boolean.parseBoolean(videoCardTmp[17]),
                    videoCardTmp[18].replace(" pin", ""),
                    Integer.parseInt(videoCardTmp[19].replace("Вт", "")
                            .replace("Энергопотребление ", "").replace(" ", "")),
                    Integer.parseInt(videoCardTmp[20].replace(" Вт", "")),
                    videoCardTmp[21],
                    Double.parseDouble(videoCardTmp[22].replace(" слота", "")
                            .replace(" слот", "")),
                    Integer.parseInt(videoCardTmp[23].replaceAll("\\(.+\\)", "")
                            .replace(" ", "").replace(" ", "")),
                    Double.parseDouble(videoCardTmp[24].replace(" мм", "")
                            .replaceAll("\\(.+\\)", "").replace(" ", "")),
                    Double.parseDouble(videoCardTmp[25].replace(" мм", "")
                            .replaceAll("\\(.+\\)", "").replace(" ", "")),
                    Double.parseDouble(videoCardTmp[26].replace(" мм", "")),
                    Boolean.parseBoolean(videoCardTmp[27].replace(" ", "")),
                    videoCardTmp[28],
                    Integer.parseInt(videoCardTmp[29].replaceAll("\\(.+\\)", "")),
                    Integer.parseInt(videoCardTmp[30].replaceAll("\\(.+\\)", "")
                            .replace(" ", "")),
                    Integer.parseInt(videoCardTmp[31].replaceAll("\\(.+\\)", "")
                            .replace(" ", "")),
                    videoCardTmp[32],
                    Integer.parseInt(videoCardTmp[33].replaceAll("\\(.+\\)", "")
                            .replace(" ", "")),
                    Integer.parseInt(videoCardTmp[34].replaceAll("\\(.+\\)", "")
                            .replace(" ", "")),
                    videoCardTmp[35],
                    Integer.parseInt(videoCardTmp[36].replaceAll("\\(.+\\)", "")
                            .replace(" ", "")),
                    Integer.parseInt(videoCardTmp[37].replaceAll("\\(.+\\)", "")),
                    Integer.parseInt(videoCardTmp[38])
            );
        } catch (Exception e) {
            System.out.println("Exception in " + url);
            e.printStackTrace();
        }
        return null;
    }
}
