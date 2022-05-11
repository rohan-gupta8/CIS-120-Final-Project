/**
 * CIS 120 Game HW
 * (c) University of Pennsylvania
 * @version 2.1, Apr 2017
 */

// imports necessary libraries for Java swing
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

/**
 * Game Main class that specifies the frame and widgets of the GUI
 */
public class Game implements Runnable {
    public void run() {
        // NOTE : recall that the 'final' keyword notes immutability even for local
        // variables.

        // Top-level frame in which game components live
        // Be sure to change "TOP LEVEL FRAME" to the name of your game
        final JFrame frame = new JFrame("2D-Scape");
        frame.setLocation(300, 300);

        JTextPane textPane = new JTextPane();
        textPane.setFont(new Font("Serif", 0, 22));
        textPane.setEditorKit(new MyEditorKit());
        textPane.setText("Welcome to 2D-Scape");
        textPane.setBackground(new Color(255, 250, 205));
        textPane.setEditable(false);
        textPane.setPreferredSize(new Dimension(120, 120));
        StyledDocument doc = textPane.getStyledDocument();

        Style style = doc.addStyle("Player", null);
        Style style2 = doc.addStyle("Benjamin", null);

        ImageIcon imageIcon = new ImageIcon("files/runescape_chathead.png");
        Image image = imageIcon.getImage();
        Image newimg = image.getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH);
        imageIcon = new ImageIcon(newimg);

        ImageIcon imageIcon2 = new ImageIcon("files/runescape_benjamin_chathead.png");
        Image image2 = imageIcon2.getImage();
        Image newimg2 = image2.getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH);
        imageIcon2 = new ImageIcon(newimg2);

        StyleConstants.setIcon(style, imageIcon);
        StyleConstants.setIcon(style2, imageIcon2);

        SimpleAttributeSet center = new SimpleAttributeSet();
        StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);

        doc.setParagraphAttributes(0, doc.getLength(), center, false);

        // Main playing area
        final GameCourt court = new GameCourt(textPane);
        frame.add(court, BorderLayout.CENTER);

        JLabel inventLabel = new JLabel("Inventory");
        JLabel playerLabel = new JLabel("Your Lifepoints : 40");

        JLabel bossLabel = new JLabel("Raksha's Lifepoints : 30");
        playerLabel.setFont(new Font("Serif", 0, 24));
        bossLabel.setFont(new Font("Serif", 0, 24));

        inventLabel.setBounds(100, 20, 120, 50);

        playerLabel.setBounds(50, 500, playerLabel.getPreferredSize().width, playerLabel.getPreferredSize().height);
        bossLabel.setBounds(40, 600, bossLabel.getPreferredSize().width, bossLabel.getPreferredSize().height);

        final InventoryView invent = new InventoryView(playerLabel, bossLabel);
        invent.setLayout(null);
        inventLabel.setFont(new Font("Serif", 0, 24));
        invent.add(inventLabel);
        invent.add(playerLabel);
        invent.add(bossLabel);
        frame.add(invent, BorderLayout.EAST);
        frame.add(textPane, BorderLayout.SOUTH);

        final JPanel control_panel = new JPanel();
        frame.add(control_panel, BorderLayout.NORTH);

        final JButton reset = new JButton("Reset");
        reset.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                court.reset();
            }
        });
        control_panel.add(reset);

        // Put the frame on the screen
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        // Start game
        court.reset();
    }

    /**
     * Main method run to start and run the game. Initializes the GUI elements
     * specified in Game and
     * runs it. IMPORTANT: Do NOT delete! You MUST include this in your final
     * submission.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Game());
    }
}