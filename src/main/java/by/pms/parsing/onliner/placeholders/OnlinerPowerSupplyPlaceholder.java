package by.pms.parsing.onliner.placeholders;

import by.pms.entity.PowerSupplyEntity;
import by.pms.parsing.WebDriverStarter;
import by.pms.repository.PowerSupplyRepository;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.transaction.TransactionSystemException;

import java.util.Objects;

public class OnlinerPowerSupplyPlaceholder {
    private static final String[] POWER_SPEC = {
            "Назначение",
            "Мощность ",
            "Диапазон входного напряжения сети ",
            "Высота",
            "Ширина",
            "Глубина",
            "Стандарт блока питания ",
            "Количество отдельных линий +12V ",
            "Макс. ток по линии +12V ",
            "Комбинированная нагрузка по +12V ",
            "Коррекция фактора мощности (PFC) ",
            "Сертификат 80 PLUS ",
            "Размер вентилятора блока питания",
            "Подсветка вентилятора ",
            "Модульное подключение кабелей питания ",
            "Питание материнской платы ",
            "CPU 4 pin ",
            "CPU 8 pin ",
            "FDD 4 pin ",
            "IDE 4 pin ",
            "SATA ",
            "PCIe 6 pin ",
            "PCIe 8 pin ",
            "PCIe Gen5 (16 pin) ",
            "USB Power "
    };
    private final String powerSupplyName;
    private final String url;
    private PowerSupplyRepository repository;

    public OnlinerPowerSupplyPlaceholder(String powerSupplyName, String url, PowerSupplyRepository repository) {
        this.powerSupplyName = powerSupplyName;
        this.url = url;
        this.repository = repository;
        fill();
    }

    private void fill() {
        if (repository.findByNameLikeIgnoreCase(powerSupplyName).isEmpty()) {
            PowerSupplyEntity entity = urlConvertToPowerSupplyEntity();
            try {
                if (entity != null) repository.save(entity);
            } catch (TransactionSystemException e) {
                System.out.println("Transaction exception in PowerSupplyEntity.");
            }
        }
    }

    private PowerSupplyEntity urlConvertToPowerSupplyEntity() {
        String[] powerEntityTmp = new String[25];
        Document doc = Jsoup.parse(WebDriverStarter.start(url));
        Elements allTables = doc.select("td");
        if (allTables == null) return null;
        for (int j = 0; j < 25; j++) {
            for (int i = 0; i < allTables.size(); i++) {
                if (allTables.get(i).text().contains(POWER_SPEC[j])) {
                    if (allTables.get(i + 1).text() == null) {
                        if (Objects.equals(allTables.get(i + 1).attr("class"), "i-tip")){
                            powerEntityTmp[j] = "true";
                            break;
                        }
                    } else {
                        powerEntityTmp[j] = allTables.get(i + 1).text();
                    }
                }
            }
            if (powerEntityTmp[j] == null) powerEntityTmp[j] = "0";
        }
        try {
            return new PowerSupplyEntity(
                    powerSupplyName,
                    powerEntityTmp[0],
                    Integer.parseInt(powerEntityTmp[1].replace(" Вт", "").replace(" ","")),
                    powerEntityTmp[2],
                    Integer.parseInt(powerEntityTmp[3].replace(" мм", "")),
                    Double.parseDouble(powerEntityTmp[4].replace(" мм", "")),
                    Integer.parseInt(powerEntityTmp[5].replace(" мм", "")),
                    powerEntityTmp[6],
                    Integer.parseInt(powerEntityTmp[7]),
                    Double.parseDouble(powerEntityTmp[8].replace(" А", "")),
                    Double.parseDouble(powerEntityTmp[9].replace(" Вт", "")),
                    powerEntityTmp[10],
                    powerEntityTmp[11],
                    powerEntityTmp[12], //fan size
                    Boolean.parseBoolean(powerEntityTmp[13]),
                    Boolean.parseBoolean(powerEntityTmp[14]),
                    powerEntityTmp[15],
                    powerEntityTmp[16],
                    powerEntityTmp[17],
                    powerEntityTmp[18],
                    powerEntityTmp[19],
                    powerEntityTmp[20],
                    powerEntityTmp[21],
                    powerEntityTmp[22],
                    powerEntityTmp[23],
                    Boolean.parseBoolean(powerEntityTmp[24])
            );
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
