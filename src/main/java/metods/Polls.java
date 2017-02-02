package metods;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import model.Answers;
import model.Poll;
import response.ResponseServer;

import java.util.ArrayList;

/**
 * Created by владик on 06.01.2017.
 */
public class Polls {
    ResponseServer response = new ResponseServer();
    private int owner_id;
    private int poll_id;
    private String token;
    private int id;
    private String question;
    private ArrayList<Answers> answers = new ArrayList<Answers>();
    Poll poll;

    public Polls(int owner_id, int poll_id, String token) {
        this.owner_id = owner_id;
        this.poll_id = poll_id;
        this.token = token;
        Parse();
    }

    private void Parse(){
        response.setUrl("https://api.vk.com/method/polls.getById?owner_id="+owner_id+"&poll_id="+poll_id+"&access_token="+token+"&v=5.59");
        response.run();
        JsonElement jelement = new JsonParser().parse(response.getResult());
        JsonObject jobject = jelement.getAsJsonObject();
        jobject = jobject.getAsJsonObject("response");
        id = Integer.valueOf(jobject.get("id").toString().replaceAll("\"",""));
        question = jobject.get("question").toString().replaceAll("\"","");
        JsonArray jarray = jobject.getAsJsonArray("answers");
        for (JsonElement element : jarray) {
            jobject = element.getAsJsonObject();
            answers.add(new Answers(Integer.valueOf(jobject.get("id").toString().replaceAll("\"","")),jobject.get("text").toString().replaceAll("\"","")));
        }
        poll = new Poll(id,question,answers);
    }

    public Poll getPoll() {
        return poll;
    }
}
