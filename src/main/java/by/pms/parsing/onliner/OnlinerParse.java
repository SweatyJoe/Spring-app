package by.pms.parsing.onliner;

import org.apache.http.client.fluent.Content;
import org.apache.http.client.fluent.Request;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.File;
import java.io.IOException;

public class OnlinerParse {
    private static String elems;

    public OnlinerParse() {
        this.sendRequest();
    }

    public static String getElems() {
        return elems;
    }

    private static String urlGen(int pageNumber, String component) {
        return "https://catalog.onliner.by/" + component + "?page=" + pageNumber;
    }

    private void sendRequest() {
        try {
            int pageIterator = 0;
            int lastPage = 1;
            for (; pageIterator < lastPage; pageIterator++) {
                Content content = Request.Get("https://app.scrapingbee.com/api/v1/?api_key=NXJ64ADCJ2ZMX77CATOTMGY2INAHJZEBQUDWPDZFDRRRNWFLRYPPF4YQFAL80RC1739BC9KHCNVVUDDV&url=" +
                                urlGen(pageIterator, "cpu"))
                        .execute().returnContent();
                lastPage = content.toString().lastIndexOf("schema-pagination__pages-link"); //find last page

                //save page as html-file
            }
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    private void readAndResearch(){
        for(int i =0;;i++){
            try{
                Document doc = Jsoup
                        .parse(new File("https://catalog.onliner.by/cpu?page=" + i));

            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    /*private void getCPU() {
        int page = 1;
        String url = "https://catalog.onliner.by/cpu?page=" + page + "&region=gomel";
        try {
            Document doc = Jsoup
                    .connect(url)
                    //.timeout(8000)
                    .get();
            Elements elements = doc.select("div.schema-product__group");
            System.out.println("size " + elements.size());
            for (int i = 0; i < elements.size(); i++) {

                System.out.println(elements.get(i).text());
                elems = elems + elements.get(i).text();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/
}
