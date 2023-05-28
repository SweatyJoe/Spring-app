package by.pms.parsing.prooffice;

import by.pms.parsing.Components;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

public class ProofficeParseThread implements Runnable {
    private final String[] COMPONENTS = {
            "protsessory/",
            "bloki-pitaniya/",
            "videokarty/",
            "zhestkie-diski/",
            "materinskie-platy/",
            "moduli-pamyati/",
            "ssd/",
            "korpusy/",
            "sistemy-okhlazhdenija/filter/prm_23402-is-val_prm_23402_kuler_dlja_protsessora/apply" ///?PAGEN_1=2
    };
    private final List<Components> components = new ArrayList<>();
    private final int controlIterator;

    public ProofficeParseThread(int controlIterator) {
        this.controlIterator = controlIterator;
    }

    public List<Components> getComponents() {
        return components;
    }

    private void getAllComponents(String url) {
        try {
            for (int i = 1; true; i++) {
                String castUrl = url;
                if(i > 1){
                    castUrl+="?PAGEN_1=" + i;
                }
                Document doc = Jsoup
                        .connect(castUrl)
                        .timeout(7000)
                        .get();
                Elements content = doc.select("div.item_info");
                for (Element element : content) {
                    components.add(new Components(element.selectFirst("div.item-title").text()
                            .replace("БП", "Блок питания"),
                            "https://prooffice.by" + element.selectFirst("div.item-title").child(0).attr("href"),
                            Float.parseFloat(element.selectFirst(".price_matrix_wrapper")
                                    .child(0).text().replace(" руб. с НДС", "")
                                    .replace(" ", ""))));
                }
            }
        } catch (Exception e) {
            System.out.println("ignore this");
            e.printStackTrace();
        }

    }

    @Override
    public void run() {
        try {
            getAllComponents(URLGenerator());
        } catch (Exception e) {
            System.out.println("Runtime Exception. ProofficeParseThread.class");
        }
    }

    private String URLGenerator() {
        String url = "https://prooffice.by/catalog/kompyuternye-komplektuyushchie/";
        url += COMPONENTS[controlIterator];
        return url;
    }
}
