
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
 * Note that the image is read from the file when the object is constructed, and
 * that all objects created by this constructor share the same image data (i.e.
 * img is static). This is important for efficiency: your program will go very
 * slowly if you try to create a new BufferedImage every time the draw method is
 * invoked.
 */
public class Anvil extends GameObj implements Resource {
    public static final String IMG_FILE = "files/runescape_anvil.png";
    public static final int SIZE = 120;
    public static final int INIT_POS_X = 10;
    public static final int INIT_POS_Y = 10;

    private static BufferedImage img;

    public Anvil(int courtWidth, int courtHeight, String interactMessage) {
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
        if (Player.getInventory().contains(InventoryObj.SILVER_BAR)
                && Player.getInventory().contains(InventoryObj.RUSTY_HILT)) {
            status.setText("You strike your hammer in an attempt to create a magnificent sword");
            Timer timer = new Timer(2000, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {

                    status.setText("You successfully create a silver sword!");
                    deplete();
                }
            });
            timer.setRepeats(false);
            timer.start();

        } else if (Player.getInventory().contains(InventoryObj.SILVER_BAR)) {
            status.setText("You can create a blade using the silver bar, but you need something "
                    + "to grab "
                    + "it with...perhaps Sir Benjamin can help?");
        } else {
            status.setText("You think that you need some metal to work with this");
        }
    }

    @Override
    public Point getInteractPos() {
        return new Point(INIT_POS_X + SIZE + 20, INIT_POS_Y + SIZE / 2 - 10);
    }

    public void output() {

    }

    public void deplete() {
        if (Player.getInventory().contains(InventoryObj.SILVER_BAR)
                && Player.getInventory().contains(InventoryObj.RUSTY_HILT)) {
            Player.removeFromInventory(InventoryObj.SILVER_BAR);
            Player.replaceInInventory(InventoryObj.RUSTY_HILT, InventoryObj.SILVER_SWORD);
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
