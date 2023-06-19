package by.pms.parsing.onliner.placeholders;

import by.pms.entity.baseEntity.CpuEntity;
import by.pms.parsing.WebDriverStarter;
import by.pms.repository.CpuRepository;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.transaction.TransactionSystemException;

import java.util.Objects;

/**
 * Класс-заполнитель, овечает за зполнение базы данных
 * данными на основе класса @CpuEntity.
 *
 * @cpuName - наименование цпу;
 * @cpuUrl - ссылка на цпу;
 * @repository - объект репозитория для доступа к бд.
 */

public class OnlinerCPUEntityPlaceholder {
    private static final String[][] CPU_SPEC = {{"socket", "cors", "threads", "tdp", "box", "integratedGraph", "freq", "maxFreq", "memory"}
            , {"Сокет", "Количество ядер", "Максимальное количество потоков", "Расчетная тепловая мощность (TDP)",
            "Тип поставки", "Встроенная графика", "Базовая тактовая частота", "Максимальная частота", "Поддержка памяти"}};
    private final String cpuName;
    private final String cpuUrl;
    private final CpuRepository cpuRepository;

    public OnlinerCPUEntityPlaceholder(String cpuName, String cpuUrl, CpuRepository repository) {
        this.cpuName = cpuName;
        this.cpuUrl = cpuUrl;
        this.cpuRepository = repository;
        fill();
    }

    private void fill() {
        if (cpuRepository.findByNameLikeIgnoreCase(cpuName).isEmpty()) {
            CpuEntity entity = urlConvertToCpuEntity();
            if (entity == null) {
                System.out.println("-- No element found --");
                return;
            }
            try {
                cpuRepository.save(entity);
            } catch (TransactionSystemException transactionSystemException) {
                System.out.println("Transaction Exception.");
            }
        }
    }

    private CpuEntity urlConvertToCpuEntity() {
        String[] cpuEntityTmp = new String[9];
        try {
            Document doc = Jsoup.parse(WebDriverStarter.start(cpuUrl, "c"));
            Elements allTables = doc.select("td");
            if (allTables == null) return null;
            for (int j = 0; j < 9; j++) {
                for (int i = 0; i < allTables.size(); i++) {
                    if (allTables.get(i).text().contains(CPU_SPEC[1][j])) {
                        cpuEntityTmp[j] = allTables.get(i + 1).text();
                    }
                }
                if (j == 1) {
                    if (cpuEntityTmp[j].contains("потоков")) {
                        String[] s = cpuEntityTmp[j].replaceAll("[()]", "")
                                .replace("потоков", "").replace("  ", " ")
                                .split(" ");
                        System.out.println(s.length);
                        cpuEntityTmp[1] = s[0];
                        cpuEntityTmp[2] = s[1];
                        j++;
                    }
                }
                if (cpuEntityTmp[j] == null) cpuEntityTmp[j] = "0";
            }
            return new CpuEntity(cpuName,
                    cpuEntityTmp[0],
                    Integer.parseInt(cpuEntityTmp[1]),
                    Integer.parseInt(cpuEntityTmp[2]),
                    Integer.parseInt(cpuEntityTmp[3].replace(" Вт", "")
                            .replace(" (макс.)", "")
                            .replaceAll("\\(.+\\)", "").replace(" ", "")),
                    !Objects.equals(cpuEntityTmp[4], "OEM"),
                    cpuEntityTmp[5],
                    Double.parseDouble(cpuEntityTmp[6].replace(" ГГц", "")),
                    Double.parseDouble(cpuEntityTmp[7].replace(" ГГц", "")
                            .replaceAll("\\(.+\\)", "")),
                    cpuEntityTmp[8],
                    cpuUrl);
        } catch (Exception e) {
            System.out.println("Exception in " + cpuUrl);
            e.printStackTrace();
        }
        return null;
    }
}
