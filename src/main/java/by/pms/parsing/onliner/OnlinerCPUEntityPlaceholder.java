package by.pms.parsing.onliner;

import by.pms.entity.CpuEntity;
import by.pms.repository.CpuRepository;
import jakarta.validation.constraints.NotNull;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.Objects;

public class OnlinerCPUEntityPlaceholder {
    private static final String[][] CPU_SPEC = {{"socket", "cors", "threads", "tdp", "box", "integratedGraph", "freq", "maxFreq", "memory"}
            , {"Сокет", "Количество ядер", "Максимальное количество потоков", "Расчетная тепловая мощность (TDP)",
            "Тип поставки", "Встроенная графика", "Базовая тактовая частота", "Максимальная частота", "Поддержка памяти"}};
    private final String cpuName;
    private final String cpuUrl;
    private CpuRepository cpuRepository;

    public OnlinerCPUEntityPlaceholder(@NotNull String cpuName, @NotNull String cpuUrl) {
        this.cpuName = cpuName;
        this.cpuUrl = cpuUrl;
        fill();
    }

    private void fill() {
        if (cpuRepository.findByNameLikeIgnoreCase(cpuName).isEmpty()) {
            cpuRepository.save(urlConvertToCpuEntity());
        }
    }

    private CpuEntity urlConvertToCpuEntity() {
        String[] cpuEntityTmp = new String[9];
        CpuEntity entity = new CpuEntity();
        try {
            Document doc = Jsoup
                    .connect(cpuUrl)
                    .timeout(1000)
                    .get();
            Elements allTables = doc.select("td");
            for (int j = 0; j < allTables.size(); j++) {
                for (int i = 0; i < 10; i++) {
                    if (allTables.get(j).text().equalsIgnoreCase(CPU_SPEC[1][i])) {
                        cpuEntityTmp[i] = allTables.get(j + 1).text();
                    }
                }
            }
            if(cpuEntityTmp[5].equalsIgnoreCase("")){
                cpuEntityTmp[5] = "Нет";
            }
            entity = new CpuEntity(cpuName,
                    cpuEntityTmp[0],
                    Integer.parseInt(cpuEntityTmp[1]),
                    Integer.parseInt(cpuEntityTmp[2]),
                    Integer.parseInt(cpuEntityTmp[3]),
                    !Objects.equals(cpuEntityTmp[4], "OEM"),
                    cpuEntityTmp[5],
                    Double.parseDouble(cpuEntityTmp[6].replace(" ГГц", "")),
                    Double.parseDouble(cpuEntityTmp[7].replace(" ГГц", "")),
                    cpuEntityTmp[8]);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return entity;
    }
}
