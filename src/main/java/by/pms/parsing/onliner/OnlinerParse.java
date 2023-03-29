package by.pms.parsing.onliner;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

public class OnlinerParse {
    private static String elems;
    public OnlinerParse(){
        getCPU();
    }


    private void getCPU(){
        int page = 0;
        String url = "https://catalog.onliner.by/cpu?page=" + page + "&region=gomel";
        try{
            Document doc = Jsoup
                    .connect(url)
                    .timeout(7000)
                    .get();
            Elements elements = doc.select("div.schema-product");
            for(int i = 0;i<elements.size();i++){
                System.out.println(elements.get(i).text());
                elems = elems + elements.get(i).text();
            }
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public static String getElems() {
        return elems;
    }

    /*private static void sendRequest() {
        try {
            AbstractDocument.Content content = Request.Get("https://app.scrapingbee.com/api/v1/?api_key=NXJ64ADCJ2ZMX77CATOTMGY2INAHJZEBQUDWPDZFDRRRNWFLRYPPF4YQFAL80RC1739BC9KHCNVVUDDV&url=https://catalog.onliner.by/cpu?page=2&region=gomel")
                    .execute().returnContent();
            System.out.println(content);
        } catch (IOException e) {
            System.out.println(e);
        }
    }*/
}
