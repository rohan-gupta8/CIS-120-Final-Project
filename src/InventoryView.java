import java.awt.*;
import java.util.ConcurrentModificationException;
import java.util.LinkedList;
import java.awt.event.*;
import javax.swing.*;

@SuppressWarnings("serial")
public class InventoryView extends JPanel {
    
    private static LinkedList<InventoryItem> items;
    public static final String FILE_STUB = "files/runescape_";
    JLabel pl;
    JLabel bl;
    
    public InventoryView(JLabel p, JLabel b) {
        
        this.pl = p;
        this.bl = b;
        
        
        
        items = new LinkedList<InventoryItem>();
        setBorder(BorderFactory.createLineBorder(Color.BLACK));
        
        Timer timer = new Timer(200, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                tick();
            }
        });
        timer.start();
        
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) 
            {
                Point p = e.getPoint();
                for (InventoryItem item : items) {
                    if (item.within(p.x, p.y)) {
                        if (item.interact())
                            {
                            try {
                            Player.removeFromInventory(InventoryObj.valueOf(item.getName().toUpperCase()));
                            }
                            catch (ConcurrentModificationException ex) {
                                
                            }
                            
                            }
                    }
                }
            }
        });
        
        addMouseMotionListener(new MouseMotionListener() { 
            @Override
            public void mouseDragged(MouseEvent e) {
            }
            
            @Override
            public void mouseMoved(MouseEvent e) {
                Point p = e.getPoint();
                boolean flag = false;
                for (InventoryItem item : items) {
                    if (item.within(p.x, p.y)) {
                        setCursor(new Cursor(Cursor.HAND_CURSOR));
                        flag = true;
                    }
                if (!flag) {
                    setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                }
            }
            }
        });
            
           
       
        repaint();
    }
    
    public static void updateInvent() {
        LinkedList<InventoryObj> inv = Player.getInventory();
        items.clear();
        for (int i = 0; i < inv.size(); i++)  {
            InventoryObj current = inv.get(i);
            int py = 80 + (i/ 4) * 60;
            int px = 20 + (i % 4) * 60;
            String name = current.toString().toLowerCase();
            items.add(new InventoryItem(name, px, py, FILE_STUB + name + ".png"));
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        this.setBackground(new Color(255, 229, 180));

        super.paintComponent(g);
        g.drawLine(0,60, 300, 60);
        for (InventoryItem item : items) {
            item.draw(g);
        }
        bl.setText("Raksha's Lifepoints:" + Integer.toString(Raksha.getLifepoints()));
        pl.setText("Your Lifepoints: " + Integer.toString(Player.getLifepoints()));        
    }
    
    public static void reset() {
        if (items != null) 
            items.clear();
    }
    void tick() {
        repaint();
    }
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(300, 300);
    }
    
}