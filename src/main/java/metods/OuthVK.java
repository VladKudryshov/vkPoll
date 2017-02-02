package metods;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import model.Account;
import response.ResponseServer;


import javax.swing.*;


public class OuthVK {
    private String response;
    private String token;
    private String userId;
    private String User;
    private Account account;

    public OuthVK(String response) {
        this.response = response;
        Parse();
        GetInfoUser();
    }

    private void Parse(){
        JsonElement jelement = new JsonParser().parse(response);
        JsonObject jobject = jelement.getAsJsonObject();
        this.userId = jobject.get("user_id").toString();
        this.token = jobject.get("access_token").toString().replaceAll("\"","");
    }

    private void GetInfoUser(){
        ResponseServer response = new ResponseServer();
        response.setUrl("https://api.vk.com/method/users.get?user_id="+userId+"&fields=photo_200,online,counters&access_token="+token+"&v=5.59");
        response.run();
        account = new Account(response.getResult());
        User = account.getFirstName()+ " " + account.getLastName();
    }

    public String getToken() {
        return token;
    }

    public String getUserId() {
        return userId;
    }

    public String getUser(){
        return User;
    }
}
