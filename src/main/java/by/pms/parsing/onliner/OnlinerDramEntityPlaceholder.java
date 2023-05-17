package by.pms.parsing.onliner;

import by.pms.entity.DramEntity;
import by.pms.parsing.WebDriverStarter;
import by.pms.repository.DramRepository;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.transaction.TransactionSystemException;

public class OnlinerDramEntityPlaceholder {
    private static final String[] DRAM_SPEC = {"Набор ", "Общий объем ",
            "Объем одного модуля ", "Тип ", "ECC ", "Частота ", "PC-индекс ",
            "CAS Latency ", "Тайминги ", "Напряжение питания ", "Профили XMP ",
            "Охлаждение ", "Низкопрофильный модуль ", "Подсветка элементов платы"
    };
    private String dramName;
    private String url;
    private DramRepository repository;

    public OnlinerDramEntityPlaceholder(String dramName, String url, DramRepository repository) {
        this.dramName = dramName;
        this.url = url;
        this.repository = repository;
    }

    private void fill() {
        if (repository.findByNameLikeIgnoreCase(dramName).isEmpty()) {
            DramEntity entity = urlConvertToEntity();
            try {
                if (entity != null) repository.save(entity);
            } catch (TransactionSystemException e) {
                System.out.println("Transaction exception in DramEntity.");
            }
        }
    }

    private DramEntity urlConvertToEntity() {

        String[] dramEntityTmp = new String[15];

        Document doc = Jsoup.parse(WebDriverStarter.start(url));
        Elements allTables = doc.select("td");
        if (allTables == null) return null;
        for (int j = 0; j < 13; j++) {
            for (int i = 0; i < allTables.size(); i++) {
                if (allTables.get(i).text().contains(DRAM_SPEC[j])) {
                    if (j == 4 || j == 11 || j == 12) {
                        if (allTables.get(i + 1).attr("class").equals("i-x")) dramEntityTmp[j] = "true";
                        else dramEntityTmp[j] = "false";
                        i++;
                        continue;
                    }
                    if (allTables.get(i + 1).text() == null) {
                        dramEntityTmp[j] = "null";
                    } else dramEntityTmp[j] = allTables.get(i + 1).text();
                    i++;
                }
            }
        }
        try {
            if (dramEntityTmp[10] == null) {
                dramEntityTmp[10] = "0";
            }

            return new DramEntity(
                    dramName,
                    dramEntityTmp[0],
                    Integer.parseInt(dramEntityTmp[1]),
                    Integer.parseInt(dramEntityTmp[2]),
                    dramEntityTmp[3],
                    Boolean.parseBoolean(dramEntityTmp[4]),
                    Integer.parseInt(dramEntityTmp[5].replace(" МГц", "")),
                    dramEntityTmp[6],
                    Double.parseDouble(dramEntityTmp[7].replace("T", "")),
                    dramEntityTmp[8],
                    Double.parseDouble(dramEntityTmp[9].replace(" В", "")),
                    Double.parseDouble(dramEntityTmp[10]),
                    Boolean.parseBoolean(dramEntityTmp[11]),
                    Boolean.parseBoolean(dramEntityTmp[12]),
                    dramEntityTmp[13]
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
