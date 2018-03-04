import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class LTPCloudTest {

    public static void main(String[] args) {
        String apikey = "";
        String sentence = "在本院 审理 期间 上诉人河南工程学院 以 其 已 寻求 其它 途径 解决 纠纷 为由 于 2009 年 4 月 7 日 向本院 递交 了 自愿 撤回 本案 上诉 起诉 的 书面 申请";
        String base = "https://api.ltp-cloud.com/analysis/";
        String s = "";

        // Remove spaces
        for(int i=0; i<sentence.length(); i++) {
            if(sentence.charAt(i) != ' ') {
                s = s + sentence.charAt(i);
            }
        }

        try {
            apikey = new Scanner(new File("API_Key.txt")).useDelimiter("\\Z").next();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        try {
            String pattern = "all";
            String format = "json";
            String encoded = URLEncoder.encode(s, StandardCharsets.UTF_8.name());
            String a = "api_key="+apikey+"&text="+encoded+"&pattern="+pattern+"&format="+format;
            String u = base + "?" + a;
            System.out.println(u);
            URL url = new URL(u);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            if(conn.getResponseCode() == 404) {
                System.out.println("Failed request.");
                return;
            }
            System.out.println(conn.getResponseCode());
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;
            StringBuilder result = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                result.append(inputLine + "\n");
            }
            in.close();

            System.out.println(result.toString());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
