package by.pms.parsing.imarket;

import by.pms.entity.ImarketParseComponents;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

public class ImarketParse {
    private final List<ImarketParseComponents> totalElements = new ArrayList<>();

    public ImarketParse() {
        //getCPU();
    }

    private void getCPU() {
        int retryConnection = 0;
        String url = "https://itmarket.by/komplektuyuschie-i-aksessuary/Komplektuyuschie/processory/?page_size=9999999"; //big size for 1 page
        try {
            Document doc = Jsoup
                    .connect(url)
                    .timeout(6000)  //<5000+ dont work
                    .get();
            Elements content = doc.select("div.product-name a.theme-link");
            Elements price = doc.select("div.product-price");
            for (int i = 0; i < content.size(); i++) {
                totalElements.add(new ImarketParseComponents(content.get(i).text(),
                        "https://itmarket.by" + content.get(i).attributes().get("href"),
                        Float.parseFloat(price.get(i).text().replace(" руб", "").replace(" ", ""))));
            }
        } catch (SocketException ex) {
            if(retryConnection > 1) return; //retry connection 2 times
            retryConnection++;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public List<ImarketParseComponents> getTotalElements() {
        if (totalElements.isEmpty()) getCPU();
        return totalElements;
    }
}
