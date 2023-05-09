package by.pms.parsing.onliner;

import by.pms.entity.CpuEntity;
import by.pms.repository.CpuRepository;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.transaction.TransactionSystemException;

import java.time.Duration;
import java.util.Objects;

/**
 * Класс-заполнитель, овечает за зполнение базы данных
 * данными на основе класса @CpuEntity.
 * @cpuName - наименование цпу;
 * @cpuUrl - ссылка на цпу;
 * @repository - объект репозитория для доступа к бд.
 * */

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
                System.out.println("-|- Transaction Exception");
            }
        }
    }

    private CpuEntity urlConvertToCpuEntity() {
        String[] cpuEntityTmp = new String[9];
        CpuEntity entity = new CpuEntity();
        try {
            System.setProperty("webdriver.chrome.driver", "selenium\\chromedriver.exe");
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--remote-allow-origins=*"); //, "--no-startup-window"

            WebDriver wDriver = new ChromeDriver(options);
            Duration duration = Duration.ofMillis(8000);
            wDriver.manage().timeouts().pageLoadTimeout(duration);
            wDriver.get(cpuUrl);

            Document doc = Jsoup.parse(wDriver.getPageSource());

            wDriver.quit();
            Elements allTables = doc.select("td");
            if (allTables == null) return null;
            for (int j = 0; j < 9; j++) {
                for (int i = 0; i < allTables.size(); i++) {
                    if (allTables.get(i).text().contains(CPU_SPEC[1][j])) {
                        cpuEntityTmp[j] = allTables.get(i + 1).text();
                        i++;
                    }
                }
            }
            if (cpuEntityTmp[5].isBlank()) {
                cpuEntityTmp[5] = "Нет";
            }
            entity = new CpuEntity(cpuName,
                    cpuEntityTmp[0],
                    Integer.parseInt(cpuEntityTmp[1]),
                    Integer.parseInt(cpuEntityTmp[2]),
                    Integer.parseInt(cpuEntityTmp[3].replace(" Вт", "").replace(" (макс.)", "")),
                    !Objects.equals(cpuEntityTmp[4], "OEM"),
                    cpuEntityTmp[5],
                    Double.parseDouble(cpuEntityTmp[6].replace(" ГГц", "")),
                    Double.parseDouble(cpuEntityTmp[7].replace(" ГГц", "")),
                    cpuEntityTmp[8],
                    cpuUrl);
        } catch (NumberFormatException wrongFormat) {
            System.out.println("Wrong format exception");
            wrongFormat.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return entity;
    }
}
