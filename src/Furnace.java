/**
 * CIS 120 Game HW
 * (c) University of Pennsylvania
 * @version 2.1, Apr 2017
 */

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
 * A game object displayed using an image.
 * 
 * Note that the image is read from the file when the object is constructed, and that all objects
 * created by this constructor share the same image data (i.e. img is static). This is important for
 * efficiency: your program will go very slowly if you try to create a new BufferedImage every time
 * the draw method is invoked.
 */
public class Furnace extends GameObj implements Resource {
    public static final String IMG_FILE = "files/runescape_furnace.png";
    public static final int SIZE = 160;
    public static final int INIT_POS_X = 240;
    public static final int INIT_POS_Y = 0;
    public static final int INIT_VEL_X = 0;
    public static final int INIT_VEL_Y = 0;

    private static BufferedImage img;

    public Furnace(int courtWidth, int courtHeight, String interactMessage) {
        super(INIT_POS_X, INIT_POS_Y, SIZE, SIZE, courtWidth, courtHeight, interactMessage);
    
        try {
            if (img == null) {
                img = ImageIO.read(new File(IMG_FILE));
            }
        } catch (IOException e) {
            System.out.println("Internal Error:" + e.getMessage());
        }
    }

    @Override 
    public void leftClick(JTextPane status) {
       if (Player.getInventory().contains(InventoryObj.SILVER_ORE)) {
           status.setText("You attempt to smelt the ore..");
           Timer timer = new Timer(2000, new ActionListener() {
               @Override
               public void actionPerformed(ActionEvent e) {  
                   
                   status.setText("You successfully smelt some ore and retrieve a silver bar!");
                   deplete();
               }   
           });
           timer.setRepeats(false);
           timer.start();
           
       }
       else {
           status.setText("You think you need some ore to heat in the furnace...");
       }
      
    }
    
    @Override
    public Point getInteractPos() {
        return new Point(INIT_POS_X + SIZE/2, INIT_POS_Y + SIZE + 20);
    }
    
    public void output() {
        
    }
    
    public void deplete() {
        if (Player.getInventory().contains(InventoryObj.SILVER_ORE)) {
            Player.removeFromInventory(InventoryObj.SILVER_ORE);
            Player.addToInventory(InventoryObj.SILVER_BAR);
        }      
    }
    
    private boolean depleted;
    public boolean getState() {
        return depleted;
    }
    @Override
    public void draw(Graphics g) {
        g.drawImage(img, this.getPx(), this.getPy(), this.getWidth(), this.getHeight(), null);
    }
}
