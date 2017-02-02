package frame;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sun.org.apache.xpath.internal.operations.Bool;
import model.Answers;
import model.Poll;
import response.ResponseServer;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

/**
 * Created by владик on 06.01.2017.
 */
public class FramePolls extends JFrame {
    JPanel panel = new JPanel(new FlowLayout());
    JTextField post_id = new JTextField();
    JButton send  = new JButton("Выполнить");

    ResponseServer response = new ResponseServer();
    int owner_id;
    String token;

    ArrayList<Integer> Members = new ArrayList<Integer>();
    ArrayList<Integer> NoMembers = new ArrayList<Integer>();


    private Poll poll;

    public FramePolls(Poll poll, int owner_id, String token){
        super("Опрос");
        this.poll = poll;
        this.owner_id = owner_id;
        this.token = token;
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        try{
            setIconImage(ImageIO.read(new File("C:\\Users\\владик\\IdeaProjects\\vkPoll\\src\\icon.png")));
        }catch (Exception e){}
        setLayout(new GridBagLayout());
        GeneratePanel();
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
    }

    private void GeneratePanel(){
        //panel = new JPanel(new GridBagLayout());
        //panel.setVisible(true);
        add(new JLabel("Название опроса"),new GridBagConstraints(0, 0, 1, 1, 0, 0, GridBagConstraints.NORTH, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
        add(new JLabel(poll.getQuestion()),new GridBagConstraints(1, 0, 1, 1, 0, 0, GridBagConstraints.NORTH, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
        GetInfo();
        int inc = 0;
        for (Answers answers : poll.getAnswers()) {
            add(new JLabel((1+inc)+"й ответ"), new GridBagConstraints(0, inc+1, 1, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
            add(new JLabel(answers.getText()), new GridBagConstraints(1, inc+1, 1, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
            add(new JLabel("("+Members.get(inc).toString()+"|"+NoMembers.get(inc)+")"), new GridBagConstraints(2, inc+1, 1, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
            inc++;
        }
    }

    private String GetAnswersToString(){
        String temp = "";
        int count = poll.getAnswers().size();
        for (Answers answers : poll.getAnswers()) {
            count--;
            if(count>0) {
                temp += answers.getId() + ",";
            }else{
                temp += answers.getId() + ",";
            }
        }
        return temp;
    }

    private void GetInfo(){
        response.setUrl("https://api.vk.com/method/polls.getVoters?owner_id="+owner_id+"&poll_id="+poll.getId()+"&answer_ids="+GetAnswersToString()+"&count=1000&access_token="+token+"&v=5.59");
        response.run();
        JsonElement el = new JsonParser().parse(response.getResult());
        JsonObject obj = el.getAsJsonObject();
        JsonArray arr = obj.getAsJsonArray("response");
        int index = 0;
        for (JsonElement element : arr) {
            int count1 = 0;
            int count2 = 0;
            obj = element.getAsJsonObject();
            obj = obj.getAsJsonObject("users");
            JsonArray array = obj.getAsJsonArray("items");
            for (JsonElement jsonElement : array) {
                index++;
                if (isMember(Integer.valueOf(jsonElement.getAsString()))){
                    count1++;
                }
                else{
                    count2++;
                }
            }
            Members.add(count1);
            NoMembers.add(count2+count1);
            index=0;
        }
    }

    private boolean isMember(int id_member){

        response = new ResponseServer();
        response.setUrl("https://api.vk.com/method/groups.isMember?group_id="+(-1*owner_id)+"&user_id="+id_member+"&access_token="+token+"&v=5.59");
        response.run();
        JsonElement el = new JsonParser().parse(response.getResult());
        JsonObject obj = el.getAsJsonObject();
        int result = Integer.valueOf(obj.get("response").toString());
        if(result==1) return true;
        return false;
    }
}