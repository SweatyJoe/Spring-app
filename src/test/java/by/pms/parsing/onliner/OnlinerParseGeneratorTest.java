package by.pms.parsing.onliner;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class OnlinerParseGeneratorTest {
    public OnlinerParseGeneratorTest() {
        testMethod();
    }

    @Test
    void testMethod() {
        int page = 1;
        String url = "https://catalog.onliner.by/cpu?page=" + page + "&region=gomel";
        try{
            Document doc = Jsoup
                    .connect(url)
                    .timeout(7000)
                    .get();
            Elements elements = doc.select("div.schema-product");
            for(int i = 0;i<elements.size();i++){
                System.out.println(elements.get(i).text());
            }
        } catch (IOException e){
            e.printStackTrace();
        }
    }


}