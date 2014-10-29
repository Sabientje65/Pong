package pong;

import java.awt.event.*;
import javax.swing.*;
import java.awt.*;

class Pong{
    PongObjects obj;
    JFrame frame;
    static Timer  timer;
    static int x, y, sp1Y, sp2Y, balX, balY;
    int sp1Points = 0, sp2Points = 0, speed = 1, maxSpeed, ySpeed = 0;
    boolean hit, sp1Point, sp2Point, start = true;
    
    Pong(){
        String MaxSpeed = JOptionPane.showInputDialog(null, "Voer een nummer in tussen 1 en 5");
        
        try{
            maxSpeed = Integer.parseInt(MaxSpeed);
            if (maxSpeed > 5 | maxSpeed < 1)
                maxSpeed = 5;
        } catch(NumberFormatException nfe){
            JOptionPane.showMessageDialog(null, "Error " + MaxSpeed + " is geen nummer.");
            maxSpeed = 5;
        }
        
        frame = new JFrame("Pong");
        
        frame.getContentPane().setBackground(Color.black);
        
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 500);
        frame.setLocationRelativeTo(null);
        
        x = frame.getWidth();
        y = frame.getHeight();
        sp1Y = 190;
        sp2Y = 190;
        balX = 240;
        balY = 240;
        
        frame.addKeyListener(monSP1);
        frame.addKeyListener(monSP2);
        
        obj = new PongObjects();
        frame.add(obj);
        frame.setVisible(true);
        
        timer = new Timer(Math.round(1000/60), timerListener);
    }
    
    KeyAdapter monSP1 = new KeyAdapter(){
        @Override
        public void keyTyped(KeyEvent evt){
            char key = evt.getKeyChar();
            
            switch(key){
                case ' ':
                    if (sp1Point | sp2Point){
                        ySpeed = 0;
                        if (sp1Point){
                            sp1Point = false;
                            hit = true;
                        }
                        if (sp2Point){
                            sp2Point = false;
                            hit = false;
                        }
                        if (speed < maxSpeed)
                            speed++;
                    }
                    break;
                case 'w':
                    if (sp1Y > 0)
                        sp1Y-=5;
                    break;
                case 's':
                    if (sp1Y < 360)
                        sp1Y+=5;
                    break;
            }
        }
    };
    
    KeyAdapter monSP2 = new KeyAdapter(){
        @Override
        public void keyPressed(KeyEvent evt){
            int key = evt.getKeyCode();
            
            switch(key){
                case KeyEvent.VK_UP:
                    if (sp2Y > 0)
                        sp2Y-=5;
                    break;
                case KeyEvent.VK_DOWN:
                    if (sp2Y < 360)
                        sp2Y+=5;
                    break;
            }
        }
    };
        
    ActionListener timerListener = new ActionListener(){
        @Override
        public void actionPerformed(ActionEvent ae){
            frame.setTitle("(" + sp1Points + " - " + sp2Points + ")");
            
            x = frame.getWidth();
            y = frame.getHeight();
            
            if (start){
                hit = Math.random() > 0.5;
                
                start = false;
            }
            
            if (!sp1Point & !sp2Point){
                if (hit)
                    balX-=speed;
                else
                    balX+=speed;

                if ((balX == 15 | balX == 16) && balY > sp1Y - 5 && balY < sp1Y + 100){
                    hit = false;
                    
                    if (balY < sp1Y + 50){
                        if (balY < sp1Y + 10)
                            ySpeed = -4;
                        else if (balY < sp1Y + 20)
                            ySpeed = -3;
                        else if (balY < sp1Y + 30)
                            ySpeed = -2;
                        else if (balY < sp1Y + 40)
                            ySpeed = -1;
                        else
                            ySpeed = 0;
                    }
                    
                    if (balY > sp1Y + 50){
                        if (balY > sp1Y + 90)
                            ySpeed = 4;
                        else if (balY > sp1Y + 80)
                            ySpeed = 3;
                        else if (balY > sp1Y + 70)
                            ySpeed = 2;
                        else if (balY > sp1Y + 60)
                            ySpeed = 1;
                        else
                            ySpeed = 0;
                    }
                }
                if ((balX == 460 | balX == 462 | balX == 463 | balX == 464) && balY < sp2Y + 100 && balY > sp2Y - 5){
                    hit = true;
                    
                    if (balY < sp2Y + 50){
                        if (balY < sp2Y + 10)
                            ySpeed = -4;
                        else if (balY < sp2Y + 20)
                            ySpeed = -3;
                        else if (balY < sp2Y + 30)
                            ySpeed = -2;
                        else if (balY < sp2Y + 40)
                            ySpeed = -1;
                        else
                            ySpeed = 0;
                    }
                    
                    if (balY > sp2Y + 50){
                        if (balY > sp2Y + 90)
                            ySpeed = 4;
                        else if (balY > sp2Y + 80)
                            ySpeed = 3;
                        else if (balY > sp2Y + 70)
                            ySpeed = 2;
                        else if (balY > sp2Y + 60)
                            ySpeed = 1;
                        else
                            ySpeed = 0;
                    }
                }

                if (balX < 10){
                    sp2Point = true;
                    sp2Points++;
                    balX = 15;
                    balY = sp1Y;
                    if (sp2Points == 10){
                        JOptionPane.showMessageDialog(null,"Speler 2 heeft gewonnen!");
                        timer.stop();
                        newGame(JOptionPane.showConfirmDialog(null, "Opnieuw spelen?"));
                    }
                }
                if (balX > 500){
                    sp1Point = true;
                    sp1Points++;
                    balX = 460;
                    balY = sp2Y;
                    if (sp1Points == 10){
                        JOptionPane.showMessageDialog(null,"Speler 1 heeft gewonnen!");
                        timer.stop();
                        newGame(JOptionPane.showConfirmDialog(null, "Opnieuw spelen?"));
                    }
                }
                
                balY += ySpeed;
                if (balY > 450)
                    ySpeed = -ySpeed;
                if(balY < 0)
                    ySpeed = ySpeed - ySpeed * 2;
            }
                
            if (sp2Point){
                balY = sp1Y + 50;
            }

            if (sp1Point){
                balY = sp2Y + 50;
            }
            
            obj.repaint();
        }
    };
    
    void newGame(int val){
        if (val == JOptionPane.YES_OPTION){
            frame.dispose();
            Pong pong = new Pong();
            sp1Points = 0;
            sp2Points = 0;
            ySpeed = 0;
            speed = 1;
            timer.start();
        }
        else
            System.exit(-1);
    }
}