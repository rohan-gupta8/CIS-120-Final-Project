import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/* 
Defining the inventory object type, to be handled by the InventoryView
*/


public class InventoryItem {
    private int px; 
    private int py;

    private String name;
    public static final int SIZE = 40;
    private BufferedImage img;

    
    public InventoryItem (String name,int px, int py, String IMG_FILE) {
        this.name = name;
        this.px = px;
        this.py = py;
               
        try {
            img = ImageIO.read(new File(IMG_FILE));
        } catch (IOException e) {
            System.out.println("Internal Error:" + e.getMessage());
        }
    }
    
    public int getPx() {
        return this.px;
    }

    public int getPy() {
        return this.py;
    }
    
    public boolean within(int x, int y) {
        if (x >= this.px && x <= this.px + SIZE && y >= this.py && y <= this.py + SIZE) {
            return true;
        }
        else {
            return false;
        }
    }
    
    public boolean interact() {
        if (getName().equals("cooked_fish")) {
            Player.modifyLifepoints(5);
            return true;
        }
        else if (getName().equals("murky_vial")) {
            Raksha.end();
            return true;
        }
        else {
            return false;
        }
    }
    
    public void draw(Graphics g) {
        g.drawImage(this.img, this.getPx(), this.getPy(), SIZE, SIZE, null);
    }

    public String getName() {
        return name;
    }
    
    
}
