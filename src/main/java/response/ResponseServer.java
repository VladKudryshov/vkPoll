package response;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;

import static org.apache.http.protocol.HTTP.USER_AGENT;

public class ResponseServer  extends  Thread{
    private URL url = null;
    private HttpURLConnection con = null;
    private BufferedReader in = null;
    private StringBuffer response = new StringBuffer();
    private String result;

    public ResponseServer() {
    }

    @Override
    public void run(){
        try {
            Thread.sleep(100);
            ResponseServerVK();
        }catch (Exception e){}
    }

    public void ResponseServerVK(){

        CreateConnection();
        GenerateResult();
        CloseConnection();

    }

    private void CreateConnection(){
        try {

            con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("User-Agent", USER_AGENT);
            con.setRequestProperty("Content-Type", "application/json");
            con.setDoOutput(true);

        }catch (Exception e){}
    }

    private void CloseConnection(){
        con.disconnect();
    }

    private void GenerateResult(){
        String inputLine = "";
        try {
            in = new BufferedReader(new InputStreamReader(con.getInputStream(), Charset.forName("UTF-8")));

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }

        }catch (Exception e){
            result = "Error answer server";
        }
        result = response.toString();
    }

    public void setUrl(String url) {
        try{
            this.url = new URL(url);
        }catch (MalformedURLException e){
            e.toString();
        }
    }

    public String getResult() {
        return result;
    }
}
