package metods;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import model.Group;
import response.ResponseServer;

import java.util.ArrayList;

public class Groups {
    private ResponseServer response;

    private String userId;
    private String token;

    private int id;
    private String name;
    private String screenName;
    private String photo50;
    private String photo100;
    private String photo200;

    private ArrayList<Group> groups = new ArrayList<Group>();

    private JsonElement jelement = null;
    private JsonObject jobject = null;
    private JsonArray jarray = null;

    public Groups(String userId, String token) {
        this.userId = userId;
        this.token = token;
        Parse();
    }

    private String AnswerServer(String url){
        response  = new ResponseServer();
        response.setUrl(url);
        response.run();
        return response.getResult();
    }

    public void Parse(){
        jelement = new JsonParser().parse(AnswerServer(getControlGroups()));
        jobject = jelement.getAsJsonObject();
        jobject = jobject.getAsJsonObject("response");
        jarray = jobject.getAsJsonArray("items");

        String items = getGroupsID(jarray);
        jelement = new JsonParser().parse(AnswerServer(getInfoGroups(items)));
        jobject = jelement.getAsJsonObject();
        jarray = jobject.getAsJsonArray("response");

        InfoGroups();

    }

    public String getControlGroups(){
        return "https://api.vk.com/method/groups.get?user_id="+userId+"&count=100&filter=admin,editor&access_token="+token+"&v=5.59";
    }

    public String getInfoGroups(String items){
        return "https://api.vk.com/method/groups.getById?group_ids="+items+"&v=5.59";
    }


    private String getGroupsID(JsonArray array){
        String items = "";
        for (JsonElement element : array) {
                items+=element+",";
        }
        items.lastIndexOf(items.length()-1,0);
        return items;
    }

    private void InfoGroups(){
        for (JsonElement element : jarray) {
            jobject = element.getAsJsonObject();
            CreateObject(jobject);
        }
    }

    private void CreateObject(JsonObject object){
        id =         Integer.valueOf(CorrectValues(object.get("id")));
        name =       CorrectValues(object.get("name"));
        screenName = CorrectValues(object.get("screen_name"));
        photo50 =    CorrectValues(object.get("photo_50"));
        photo100 =   CorrectValues(object.get("photo_100"));
        photo200 =   CorrectValues(object.get("photo_200"));

        groups.add(new Group(id,name,screenName,photo50,photo100,photo200));
    }

    private String CorrectValues(JsonElement element){
        return element.toString().replaceAll("\"","");
    }

    public String[] getNames() {
        String [] temp = new String[groups.size()];
        int index = 0;
        for (Group group : groups) {
            temp[index++] = group.getName();
        }
        return temp;
    }

    public ArrayList getGroups(){
        return groups;
    }
}
