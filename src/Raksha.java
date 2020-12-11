
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JTextPane;
import javax.swing.Timer;

/**
 * A basic game object starting in the upper left corner of the game court. It is displayed as a
 * square of a specified color.
 */
public class Raksha extends GameObj {
    
    public static final String IMG_FILE = "files/runescape_raksha.png";
    public static final int SIZE = 200;
    public static final int INIT_POS_X = 500;
    public static final int INIT_POS_Y = 230;
    private static BufferedImage img;
    private boolean attackable;
    private boolean fireActive;
    public static int lifepoints;
    public static BufferedImage img2;
    public static BufferedImage img3;
    public static boolean dead;
    public static boolean started;


    public Raksha(int courtWidth, int courtHeight, String interactMessage) {
        super(INIT_POS_X, INIT_POS_Y, SIZE, SIZE, courtWidth, courtHeight, interactMessage);
        attackable = false;
        fireActive = false;
        started = false;
        dead = false;
        lifepoints = 30;
        try {
            if (img == null) {
                img = ImageIO.read(new File(IMG_FILE));
                
            }
            if (img2 == null) {
                img2 = ImageIO.read(new File("files/blue_flame.png"));
            }
            if (img3 == null) {
                img3 = ImageIO.read(new File("files/runescape_raksha_dead.png"));
            }
        } catch (IOException e) {
            System.out.println("Internal Error:" + e.getMessage());
        }
    }
    

    public void leftClick(JTextPane status) {

       if (!Player.getInventory().contains(InventoryObj.SILVER_SWORD)) {
           status.setText("You definitely do not want to fight that bare handed!");
       }
       else if (!Player.getInventory().contains(InventoryObj.COOKED_FISH)) {
           status.setText("There's no chance you're fighting THAT without food!");
       }
       else if (!attackable) {
           status.setText("You warily rouse the sleeping boss...");
           attackable = true;
       }
       else {
           status.setText("You redirect your focus towards Raksha");
       }
    }
    
    public void startEncounter(Player p, JTextPane status) {
     
        if (!started) {
            started = true;
        Timer bossTimer = new Timer(3000, new ActionListener() {
            int c = 1;
            @Override
            public void actionPerformed(ActionEvent e) {  
                if (c % 4 == 3) {
                    status.setText("Raksha prepares to blast you with icy dragonbreath. Move out!");
                }
                else if (c % 4 == 0) {
                    fireActive = true;
                    int x = p.getPx();
                    int y = p.getPy();
                    if (x >= 375 && x < 505 && y >= 300 && y < 430) {
                        Player.modifyLifepoints(-10);
                        status.setText("You are hit by the icy blast! Consume some food by clicking to heal yourself");
                    }
                    else {
                        status.setText("You successfully avoid the attack. Get back to attacking Raksha!");
                    }
                }
                else {
                    fireActive = false;
                    Player.modifyLifepoints(-2);
                    if (Player.getLifepoints() <= 10) {
                        status.setText("Raksha strikes you with a powerful blow. "
                                + "Oh no, your health is getting low. Consume some food by clicking to heal yourself");
                    }
                    else {
                        status.setText("Raksha strikes you with a powerful blow");
                    }
                    
                    
                }
                if (Player.getLifepoints() <= 0) {
                    ((Timer) e.getSource()).stop();
                    status.setText("You died :(");
                }
                c +=1 ;
            }   
        });
        bossTimer.start();
        
        Timer playerTimer = new Timer(2400, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {  
                if (p.getPx() == getInteractPos().x && p.getPy() == getInteractPos().y) {
                    modifyLifepoints(-5);
                    status.setText("You deal a blow to Raksha");
                }
                if (lifepoints <= 0) {
                    bossTimer.stop();
                    status.setText("Almost done! Use the vial of poison to finish him off!");
                    if (dead) {
                        ((Timer) e.getSource()).stop();
                        status.setText("You did it!! Congratulations on becoming Gielenor's hero");
                    }
                }
            }   
        });
        playerTimer.start();
        
        }
        
        
 
        
    }

    public static void end () {
        dead = true;
    }
    public static void modifyLifepoints(int d) {
        lifepoints = lifepoints + d;
        if (lifepoints > 30) {
            lifepoints = 40;
        }
        if (lifepoints < 0) {
            lifepoints = 0;
        }
    }
    public boolean getAttackableState() {
        return attackable;
    }
    public static int getLifepoints() {
        return lifepoints;
    }
    @Override
    public Point getInteractPos() {
        return new Point(INIT_POS_X - 70, INIT_POS_Y + SIZE/2 - 20);
    }
    
    @Override
    public void draw(Graphics g) {
        if (!dead) {
        g.drawImage(img, this.getPx(), this.getPy(), this.getWidth(), this.getHeight(), null);
        }
        else {
            g.drawImage(img3, this.getPx(), this.getPy(), this.getWidth(), this.getHeight(), null);

        }
            
        if (fireActive) {
          g.drawImage(img2, 375, 300, 130, 130, null);
            }
            
        
    }

}