package pong;

import java.awt.geom.*;
import javax.swing.*;
import java.awt.*;

class PongObjects extends JPanel{
    Ellipse2D.Float bal;
    int x, y, afstand, sp1Y, sp2Y, balX, balY;
    Rectangle[] line = new Rectangle[10];
    Rectangle sp1, sp2, background;
    
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        
        Graphics2D c = (Graphics2D)g;
        
        x = Pong.x;
        y = Pong.y;
        sp1Y = Pong.sp1Y;
        sp2Y = Pong.sp2Y;
        balX = Pong.balX;
        balY = Pong.balY;
        afstand = y / 9;
        
        c.setColor(Color.black);
        background = new Rectangle(0,0,x,y);
        c.fill(background);
        
        c.setColor(Color.white);
        c.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        sp1 = new Rectangle(5, sp1Y, 10, 100);
        sp2 = new Rectangle(470, sp2Y, 10, 100);
        bal = new Ellipse2D.Float(balX, balY, 10, 10);
        
        c.fill(sp1);
        c.fill(sp2);
        c.fill(bal);
        
        for (int i = 0; i < line.length; i++){
            line[i] = new Rectangle(x/2 - x/50, y - (i * afstand), x/50, y/50);
            c.fill(line[i]);
        }
    }
}