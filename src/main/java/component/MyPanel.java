package component;

import java.awt.*;
import javax.swing.*;
public class MyPanel extends JPanel {
        Image image;
        public MyPanel() {
            try
            {
                image = javax.imageio.ImageIO.read(new java.net.URL(getClass().getResource("bg.jpg"), "Grafik-Daten/Extras/gras.gif"));
            }
            catch(Exception e){}
        }
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        if(image != null) g.drawImage(image, 0,0,200,2000,this);
    }
}