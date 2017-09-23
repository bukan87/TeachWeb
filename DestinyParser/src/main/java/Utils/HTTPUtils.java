package Utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @author by Ilin_ai on 21.09.2017.
 */
public class HTTPUtils {

    public static final String key = "31da3bbd072c48f895be72c7dcd4e70b";

    public static String SendGet(String url){
        // TODO нужно защититься от того что пропадает коннект к сайту
        HttpURLConnection con;
        try {
            con = (HttpURLConnection)new URL(url).openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("X-API-Key", key);
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            return response.toString();
        } catch (IOException e) {
            System.out.println("Не удалось подключиться к ресурусу: " + url);
            //e.printStackTrace();
            return null;
        }
    }
}
