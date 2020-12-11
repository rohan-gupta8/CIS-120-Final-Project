
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JTextPane;
import javax.swing.Timer;
import javax.swing.text.BadLocationException;
import javax.swing.text.StyledDocument;

/**
 * A basic game object starting in the upper left corner of the game court. It is displayed as a
 * square of a specified color.
 */
public class Benjamin extends GameObj {
    
    public static final String IMG_FILE = "files/runescape_benjamin_chathead.png";
    public static final int SIZE = 70;
    public static final int INIT_POS_X = 550;
    public static final int INIT_POS_Y = 600;
    private static BufferedImage img;

    public Benjamin(int courtWidth, int courtHeight, String interactMessage) {
        super(INIT_POS_X, INIT_POS_Y, SIZE, SIZE, courtWidth, courtHeight, interactMessage);
    
        try {
            if (img == null) {
                img = ImageIO.read(new File(IMG_FILE));
            }
        } catch (IOException e) {
            System.out.println("Internal Error:" + e.getMessage());
        }
    }
    

    public void leftClick(JTextPane status) {
        if (Player.getInventory().size() == 0) {
            status.setText("Welcome to Gielenor! To begin with, please try to collect some fish"
                    + " from the nearby pond");
            
        }
        else if (Player.getInventory().contains(InventoryObj.SILVER_SWORD)) {
            status.setText("You're all set now! It's time to take on Raksha. Take this vial of poison to finish him off when he is weak");
            Player.addToInventory(InventoryObj.MURKY_VIAL);
            Timer timer = new Timer(2000, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {  
                    status.setText("Before that, you should make sure you have enough food. "
                            + "Feel free to cook some more.");
                        
                   
                }   
            })  ;
            timer.setRepeats(false);
            timer.start();
        }
        
        else if (Player.getInventory().contains(InventoryObj.SILVER_BAR)) {
            Player.addToInventory(InventoryObj.RUSTY_HILT);
            status.setText("Great work. Here, take one of my old hilts and finish up the sword "
                    + ". It's a bit rusty, but we should be fine");
            
        }   
        
        else if (Player.getInventory().contains(InventoryObj.SILVER_ORE)) {
            status.setText("Nice! Now, use the northern furnace to convert this into a bar of silver");
        }
        
        else if (Player.getInventory().contains(InventoryObj.COOKED_FISH)) {
            status.setText("Great work! Now we have something to restore our health");
            Timer timer = new Timer(2000, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {  
                    
                    status.setText("Next, you should try to forge a weapon. Start by mining the rocks up west.");
                        
                   
                }   
            });
            timer.setRepeats(false);
            timer.start();
            }
        
            
        else if (Player.getInventory().contains(InventoryObj.BURNT_FISH)) {
            status.setText("Uh-oh, that's not good. Try to fish and cook another fish.");
        }
        
        else if (Player.getInventory().contains(InventoryObj.RAW_FISH)) {
            status.setText("Good work! Now try to cook the fish by interacting with the fire. Be careful...the fire is hot!");
        }
       
        try {
            StyledDocument doc = status.getStyledDocument();
            doc.insertString(0, "  ", doc.getStyle("Benjamin"));
        } catch (BadLocationException e1) {
           
        }
    }
    
    @Override
    public Point getInteractPos() {
        return new Point(INIT_POS_X - 50, INIT_POS_Y - 50);
    }
    
    @Override
    public void draw(Graphics g) {
        g.drawImage(img, this.getPx(), this.getPy(), this.getWidth(), this.getHeight(), null);
    }

}