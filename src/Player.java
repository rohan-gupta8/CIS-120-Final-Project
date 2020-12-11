/**
 * CIS 120 Game HW
 * (c) University of Pennsylvania
 * @version 2.1, Apr 2017
 */

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.LinkedList;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * A game object displayed using an image.
 * 
 * Note that the image is read from the file when the object is constructed, and that all objects
 * created by this constructor share the same image data (i.e. img is static). This is important for
 * efficiency: your program will go very slowly if you try to create a new BufferedImage every time
 * the draw method is invoked.
 */
public class Player extends GameObj{
    public static final String IMG_FILE = "files/runescape_chathead.png";
    public static final int SIZE = 70;
    public static final int INIT_POS_X = 370;
    public static final int INIT_POS_Y = 600;
    public static final int INIT_VEL_X = 0;
    public static final int INIT_VEL_Y = 0;
    public static int lifepoints;
    private static LinkedList<InventoryObj> inventory = new LinkedList<InventoryObj>(); // the player's inventory, a LL of inventory objects
    private static BufferedImage img;

    public Player(int courtWidth, int courtHeight, String interactMessage) {
        super(INIT_POS_X, INIT_POS_Y, SIZE, SIZE, courtWidth, courtHeight, interactMessage);
        lifepoints = 40;
        try {
            if (img == null) {
                img = ImageIO.read(new File(IMG_FILE));
            }
        } catch (IOException e) {
            System.out.println("Internal Error:" + e.getMessage());
        }
    }

    public void move (int px, int py) {
        this.setPx(px);
        this.setPy(py);
    }
    
    public static int getLifepoints() {
        return lifepoints;
    }
    
    public static void modifyLifepoints(int change) {
        lifepoints = lifepoints + change;
        if (lifepoints > 40) {
            lifepoints = 40;
        }
        if (lifepoints < 0) {
            lifepoints = 0;
        }
    }
    public static LinkedList<InventoryObj> getInventory() {
        return new LinkedList<InventoryObj>(inventory);
    }
    @Override
    public void draw(Graphics g) {
        g.drawImage(img, this.getPx(), this.getPy(), this.getWidth(), this.getHeight(), null);
    }

    public static void addToInventory(InventoryObj o) {
        inventory.add(o);      
        InventoryView.updateInvent();
    }
    
    public static void removeFromInventory(InventoryObj o) {
        inventory.remove(o);
        InventoryView.updateInvent();

    }

    public static void replaceInInventory(InventoryObj item1, InventoryObj item2) {
        int i = inventory.indexOf(item1);
        inventory.set(i, item2);
        InventoryView.updateInvent();
        
    }
    
    public static void clearInventory () {
        inventory.clear();
    }
}
