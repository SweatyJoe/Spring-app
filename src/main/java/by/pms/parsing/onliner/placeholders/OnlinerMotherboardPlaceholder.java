package by.pms.parsing.onliner.placeholders;

import by.pms.entity.baseEntity.MotherboardEntity;
import by.pms.parsing.WebDriverStarter;
import by.pms.repository.MotherboardRepository;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.transaction.TransactionSystemException;

public class OnlinerMotherboardPlaceholder {
    private final static String[] MOTHERBOARD_SPEC = {
            "Поддержка процессоров",
            "Сокет ",
            "Чипсет",
            "Форм-фактор",
            "Подсветка",
            "Тип памяти ",
            "Количество слотов памяти ",
            "Максимальный объём памяти ",
            "Режим памяти ",
            "Максимальная частота памяти ",
            "Версия PCI Express ",
            "Режимы работы нескольких PCIe x16 слотов ",
            "Всего PCI Express x16 ",
            "Из них PCI Express 2.0 x16",
            "Всего PCI Express x1 ",
            "Из них PCI Express 2.0 x1",
            "Всего PCI Express x4 ",
            "Из них PCI Express 2.0 x4",
            "Всего PCI Express x8 ",
            "Из них PCI Express 2.0 x8",
            "PCI ",
            "PCI X ",
            "Дополнительные характеристики PCI",
            "M.2 ",
            "Интерфейс M.2",
            "SATA 3.0 ",
            "SATA 2.0 ",
            "RAID ",
            "Слот для модуля Wi-Fi ",
            "Wi-Fi ",
            "Bluetooth ",
            "Ethernet ",
            "Поддержка встроенной графики ",
            "Встроенный звук ",
            "Звуковая схема ",
            "USB 2.0 ",
            "USB 3.2 Gen1 Type-A (5 Гбит/с) ",
            "USB 3.2 Gen2 Type-A (10 Гбит/с) ",
            "USB 3.2 Gen1 Type-C (5 Гбит/с) ",
            "USB 3.2 Gen2 Type-C (10 Гбит/с) ",
            "USB 3.2 Gen 2x2 (20 Гбит/с) ",
            "USB-C (Thunderbolt 3)",
            "USB-C (Thunderbolt 4)",
            "Цифровой выход S/PDIF ",
            "Аудио (3.5 мм jack)",
            "COM ",
            "LPT",
            "PS/2",
            "DisplayPort ",
            "mini DisplayPort ",
            "VGA (D-Sub) ",
            "DVI ",
            "HDMI ",
            "Разъемы для СЖО",
            "Разъемы для корпусных вентиляторов",
            "Разъемы для подсветки ARGB 5В",
            "Разъемы для подсветки RGB 12В",
            "Длина ",
            "Ширина "
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
            try {
                if (entity != null) repository.save(entity);
            } catch (TransactionSystemException e) {
                e.printStackTrace();
            }
        }
    }

    private MotherboardEntity urlConvertToEntity() {
        String[] motherboardTmp = new String[59];
        Document doc = Jsoup.parse(WebDriverStarter.start(url));
        Elements allTables = doc.select("td");
        for (int j = 0; j < 59; j++) {
            for (int i = 0; i < allTables.size(); i++) {
                if (allTables.get(i).text().contains(MOTHERBOARD_SPEC[j])) {
                    if (allTables.get(i + 1).text().isEmpty() ||
                            !allTables.get(i + 1).getElementsByClass("i-tip").isEmpty()) {
                        motherboardTmp[j] = "0";
                        break;
                    } else motherboardTmp[j] = allTables.get(i + 1).text();
                }
            }
            if (motherboardTmp[j] == null) motherboardTmp[j] = "0";
        }
        try {
            return new MotherboardEntity(name,
                    motherboardTmp[0],
                    motherboardTmp[1],
                    motherboardTmp[2],
                    motherboardTmp[3],
                    Boolean.parseBoolean(motherboardTmp[4]),
                    motherboardTmp[5],
                    Integer.parseInt(motherboardTmp[6]),
                    Integer.parseInt(motherboardTmp[7].replace("GB", "")),
                    motherboardTmp[8],
                    Integer.parseInt(motherboardTmp[9].replace(" МГц", "")
                            .replace(" ", "")),
                    motherboardTmp[10],
                    motherboardTmp[11],
                    Integer.parseInt(motherboardTmp[12]),
                    Integer.parseInt(motherboardTmp[13]),
                    Integer.parseInt(motherboardTmp[14]),
                    Integer.parseInt(motherboardTmp[15]),
                    Integer.parseInt(motherboardTmp[16]),
                    Integer.parseInt(motherboardTmp[17]),
                    Integer.parseInt(motherboardTmp[18]),
                    Integer.parseInt(motherboardTmp[19]),
                    Integer.parseInt(motherboardTmp[20]),
                    Integer.parseInt(motherboardTmp[21]),
                    motherboardTmp[22],
                    Integer.parseInt(motherboardTmp[23]),
                    motherboardTmp[24],
                    Integer.parseInt(motherboardTmp[25]),
                    Integer.parseInt(motherboardTmp[26]),
                    motherboardTmp[27],
                    Boolean.parseBoolean(motherboardTmp[28]),
                    motherboardTmp[29],
                    Double.parseDouble(motherboardTmp[30]),
                    motherboardTmp[31],
                    Boolean.parseBoolean(motherboardTmp[32]),
                    motherboardTmp[33],
                    Double.parseDouble(motherboardTmp[34]),
                    Integer.parseInt(motherboardTmp[35]),
                    Integer.parseInt(motherboardTmp[36]),
                    Integer.parseInt(motherboardTmp[37]),
                    Integer.parseInt(motherboardTmp[38]),
                    Integer.parseInt(motherboardTmp[39]),
                    Integer.parseInt(motherboardTmp[40]),
                    Integer.parseInt(motherboardTmp[41]),
                    Integer.parseInt(motherboardTmp[42]),
                    Integer.parseInt(motherboardTmp[43]),
                    Integer.parseInt(motherboardTmp[44]),
                    Integer.parseInt(motherboardTmp[45]),
                    Integer.parseInt(motherboardTmp[46]),
                    Integer.parseInt(motherboardTmp[47]),
                    Integer.parseInt(motherboardTmp[48]),
                    Integer.parseInt(motherboardTmp[49]),
                    Integer.parseInt(motherboardTmp[50]),
                    Integer.parseInt(motherboardTmp[51]),
                    Integer.parseInt(motherboardTmp[52]),
                    Integer.parseInt(motherboardTmp[53]),
                    Integer.parseInt(motherboardTmp[54]),
                    Integer.parseInt(motherboardTmp[55]),
                    Integer.parseInt(motherboardTmp[56]),
                    Integer.parseInt(motherboardTmp[57].replace(" мм", "")),
                    Integer.parseInt(motherboardTmp[58].replace(" мм", ""))
            );

        } catch (Exception e) {
            System.out.println("Parse exception in MotherboardPlaceholder");
            e.printStackTrace();
            return null;
        }
    }
}
