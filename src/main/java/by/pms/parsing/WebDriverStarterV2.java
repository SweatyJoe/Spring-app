package by.pms.parsing;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class WebDriverStarterV2 {
    public static String start(String url){
        try{
            URL preCon = new URL(url);
            System.out.println("ParserV2 URL " + url);
            HttpURLConnection urlConnection;
            urlConnection = (HttpURLConnection) preCon.openConnection();
            urlConnection.setRequestMethod("GET");
            InputStream inputStream = urlConnection.getInputStream();
            StringBuilder result = new StringBuilder();
            try (Reader reader = new BufferedReader(new InputStreamReader
                    (inputStream, StandardCharsets.UTF_8))) {
                int c = 0;
                while ((c = reader.read()) != -1) {
                    result.append((char) c);
                }
            }
            return result.toString();
        } catch (Exception e){
            System.out.println("        Exception on web driver.");
            e.printStackTrace();
        }
        return null;
    }
}
