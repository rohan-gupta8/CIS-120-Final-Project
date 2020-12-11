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
public class Pond extends GameObj implements Resource {
    public static final String IMG_FILE = "files/runescape_pond.png";
    public static final int SIZE = 200;
    public static final int INIT_POS_X = 10;
    public static final int INIT_POS_Y = 490;
    public static final int INIT_VEL_X = 0;
    public static final int INIT_VEL_Y = 0;

    private static BufferedImage img;
    

    public Pond(int courtWidth, int courtHeight, String interactMessage) {
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
        status.setText("You dip your trusty fishing rod into the murky waters...");
        Timer timer = new Timer(2000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {            
                status.setText("You find a raw fish!");
                deplete();
            }
        });
        timer.setRepeats(false);
        timer.start();
        
    }
    
    @Override
    public Point getInteractPos() {
        return new Point(INIT_POS_X + SIZE + 20, INIT_POS_Y + SIZE/2 - 10);
    }
    public void output() {
        
    }
    
    public void deplete() {
        Player.addToInventory(InventoryObj.RAW_FISH);
    }
    
    public boolean getState() {
        return false;
    }
    @Override
    public void draw(Graphics g) {
        g.drawImage(img, this.getPx(), this.getPy(), this.getWidth(), this.getHeight(), null);
    }
}
