package model;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class Account {
    private String response = null;
    private String firstName = null;
    private String lastName = null;
    private String UrlPhoto = null;
    private String online = null;

    public Account(String response) {
        this.response = response;
        DataAccount();
    }

    public void DataAccount(){
        JsonElement jelement = new JsonParser().parse(response);
        JsonObject  jobject = jelement.getAsJsonObject();
        JsonArray jarray = jobject.getAsJsonArray("response");
        jobject = jarray.get(0).getAsJsonObject();

        firstName = jobject.get("first_name").toString().replaceAll("\"","");
        lastName = jobject.get("last_name").toString().replaceAll("\"","");
        UrlPhoto = jobject.get("photo_200").toString().replaceAll("\"","");
        online = isOnline(jobject.get("online").toString());

    }

    private String isOnline(String online){
        if(online.equals("1")){return "Online";}
        return "Offline";
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getUrlPhoto() {
        return UrlPhoto;
    }

    public String getOnline() {
        return online;
    }

}
