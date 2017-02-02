package frame;

import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import metods.Groups;
import metods.OuthVK;
import model.Account;
import model.Group;
import response.ResponseServer;

public class FrameAccount extends JFrame {
    private JLabel user;
    private JLabel online;
    private JComboBox groups;
    private JButton send = new JButton("Выполнить");
    private Groups group;
    private URL photo;
    private String token;
    private String userId;
    private ArrayList postId;
    OuthVK outh = null;
    Account account = null;

    public FrameAccount(OuthVK outh) {
        super(outh.getUser());
        this.outh = outh;
        this.SetValuesOuth();
        this.setDefaultCloseOperation(3);
        this.setLayout(new GridBagLayout());
        this.GeneratePanel();
        this.pack();
        this.setLocationRelativeTo((Component)null);
        this.setVisible(true);
        this.setResizable(false);
    }

    private void GeneratePanel() {
        this.SetValues();
        this.Propeties();
        this.Listener();
        this.add(new JLabel(new ImageIcon(this.photo)), new GridBagConstraints(0, 0, 1, 6, 0.0D, 0.0D, 11, 1, new Insets(5, 5, 5, 5), 0, 0));
        this.add(this.user, new GridBagConstraints(1, 0, 1, 1, 0.0D, 0.0D, 10, 1, new Insets(5, 5, 5, 5), 0, 0));
        this.add(this.online, new GridBagConstraints(1, 1, 1, 1, 0.0D, 0.0D, 11, 2, new Insets(0, 5, 5, 0), 0, 0));
        this.add(new JLabel("Выберите группу:"), new GridBagConstraints(1, 2, 1, 1, 0.0D, 0.0D, 11, 2, new Insets(0, 5, 5, 0), 0, 0));
        this.add(this.groups, new GridBagConstraints(1, 3, 1, 1, 0.0D, 0.0D, 11, 2, new Insets(0, 5, 5, 0), 0, 0));
        this.add(this.send, new GridBagConstraints(1, 4, 1, 1, 0.0D, 0.0D, 11, 2, new Insets(0, 5, 5, 0), 0, 0));
    }

    private void Listener() {
        this.send.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int owner_id = 0;
                ArrayList items = FrameAccount.this.group.getGroups();
                Iterator var4 = items.iterator();

                while(var4.hasNext()) {
                    Group item = (Group)var4.next();
                    if(FrameAccount.this.groups.getSelectedItem().toString().equals(item.getName())) {
                        owner_id = item.getId();
                    }
                }

                owner_id *= -1;
                new FrameEnterIDWall(owner_id, FrameAccount.this.token);
            }
        });
    }

    private void Propeties() {
        this.PropetiesLabel(this.user);
    }

    private void PropetiesLabel(JLabel label) {
        label.setFont(new Font("Dialog", 2, 22));
        label.setAlignmentY(0.0F);
    }

    private void SetValuesElement() {
        ResponseServer response = new ResponseServer();
        response.setUrl("https://api.vk.com/method/users.get?user_id=" + this.userId + "&fields=photo_200,online,counters&access_token=" + this.token + "&v=5.59");
        response.run();
        this.account = new Account(response.getResult());
        this.user = new JLabel(this.outh.getUser());

        try {
            this.photo = new URL(this.account.getUrlPhoto());
        } catch (Exception var3) {
            ;
        }

        this.online = new JLabel(this.account.getOnline());
        this.group = new Groups(this.userId, this.token);
        this.groups = new JComboBox(this.group.getNames());
    }

    private void SetValuesOuth() {
        this.token = this.outh.getToken();
        this.userId = this.outh.getUserId();
    }

    private void SetValues() {
        this.SetValuesOuth();
        this.SetValuesElement();
    }
}
