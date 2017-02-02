package frame;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import metods.Polls;
import response.ResponseServer;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

/**
 * Created by владик on 06.01.2017.
 */
public class FrameEnterIDWall extends JFrame{
    JPanel panel = new JPanel(new FlowLayout());
    JTextField post_id = new JTextField();
    JButton send  = new JButton("Выполнить");

    private int owner_id;
    private String token;
    private ResponseServer response;

    public FrameEnterIDWall(int owner_id, String token){
        super("Введите id поста");
        this.owner_id = owner_id;
        this.token = token;
        try{
            setIconImage(ImageIO.read(new File("C:\\Users\\владик\\IdeaProjects\\vkPoll\\src\\icon.png")));
        }catch (Exception e){}
        Listener();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setPreferredSize(new Dimension(350,100));
        post_id.setPreferredSize(new Dimension(170,30));
        panel.add(post_id);
        panel.add(send);
        getContentPane().add(panel);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
    }

    private void Listener(){
        send.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(panel.getComponentCount()>2){
                    panel.remove(2);
                    repaint();
                }
                if (post_id.getText().equals("")){
                    panel.add(new JLabel("Пустое значение"));
                    pack();
                    return;
                }
                response = new ResponseServer();
                response.setUrl("https://api.vk.com/method/wall.getById?posts=-"+post_id.getText()+"&access_token="+token+"&v=5.59");
                response.run();

                    JsonElement jelement = new JsonParser().parse(response.getResult());
                    JsonObject obj = jelement.getAsJsonObject();
                    JsonArray arr = obj.getAsJsonArray("response");
                    obj = arr.get(0).getAsJsonObject();
                    arr = obj.getAsJsonArray("attachments");
                    boolean check = false;
                    for (JsonElement element : arr) {
                        obj = element.getAsJsonObject();
                        String tmp = obj.get("type").toString().replaceAll("\"","");
                        if(tmp.equals("poll")){
                            check = true;
                            obj = obj.getAsJsonObject("poll");
                            Polls polls = new Polls(owner_id,Integer.valueOf(obj.get("id").toString().replaceAll("\"","")),token);
                            new FramePolls(polls.getPoll(),owner_id,token);
                            setVisible(false);
                            break;
                        }
                    }
                    if(!check){
                        panel.add(new JLabel("В записи нет опроса!"));
                        pack();
                    }
               /* }catch (Exception w){
                    panel.add(new JLabel("Некорректные данные"));
                    pack();
                }*/

            }
        });
    }
}
