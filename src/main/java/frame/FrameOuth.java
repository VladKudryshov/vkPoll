package frame;

import component.MyPanel;
import metods.OuthVK;
import response.ResponseServer;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;


public class FrameOuth extends JFrame {
    JTextField login = new JTextField(20);
    JPasswordField password = new JPasswordField(20);

    public FrameOuth(){
        super("Авторизация");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setPreferredSize(new Dimension(300,360));


        try{
            setIconImage(ImageIO.read(new File("C:\\Users\\владик\\IdeaProjects\\vkPoll\\src\\icon.png")));
            setContentPane(new JLabel(new ImageIcon(ImageIO.read(new File("C:\\Users\\владик\\IdeaProjects\\vkPoll\\src\\bg.jpg")))));
        }catch (Exception e){}
        setLayout(new GridBagLayout());
        GeneratePanel();
        pack();
        setLocationRelativeTo(null);
        setVisible(true);

    }


    private void GeneratePanel(){
        add(new JLabel("Login:"), new GridBagConstraints(0, 0, 1, 1, 0, 0, GridBagConstraints.NORTH, GridBagConstraints.BOTH, new Insets(5, 0, 5, 0), 0, 0));
        add(login, new GridBagConstraints(0, 1, 1, 1, 0, 0, GridBagConstraints.NORTH, GridBagConstraints.BOTH, new Insets(5, 0, 5, 5), 0, 0));
        add(new JLabel("Password:"), new GridBagConstraints(0, 2, 1, 1, 0, 0, GridBagConstraints.NORTH, GridBagConstraints.BOTH, new Insets(0, 0, 5, 0), 0, 0));
        add(password, new GridBagConstraints(0, 3, 1, 1, 0, 0, GridBagConstraints.NORTH, GridBagConstraints.BOTH, new Insets(0, 0, 5, 5), 0, 0));
        JButton enter = new JButton("Войти");
        add(enter, new GridBagConstraints(0, 4, 1, 1, 0, 0, GridBagConstraints.EAST, GridBagConstraints.CENTER, new Insets(5, 5, 5, 5), 0, 0));

        enter.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(getComponentCount()>5){
                    remove(5);
                    repaint();
                    pack();
                }
                ResponseServer response = new ResponseServer();
                response.setUrl("https://oauth.vk.com/token?grant_type=password&client_id=2274003&client_secret=hHbZxrka2uZ6jB1inYsH&username="+login.getText()+"&password="+password.getText());
                response.run();

                new FrameAccount(new OuthVK(response.getResult()));
                setVisible(false);


                add(new JLabel(response.getResult()), new GridBagConstraints(0, 5, 1, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.CENTER, new Insets(5, 5, 5, 5), 0, 0));
                pack();
                return;


            }
        });
    }
}
