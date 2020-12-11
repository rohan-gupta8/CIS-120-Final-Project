
/**
 * CIS 120 Game HW
 * (c) University of Pennsylvania
 * @version 2.1, Apr 2017
 */

import java.awt.*;
import java.util.LinkedList;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

/**
 * GameCourt
 * 
 * This class holds the primary game logic for how different objects interact
 * with one another. Take time to understand how the timer interacts with the
 * different methods and how it repaints the GUI on every tick().
 */
@SuppressWarnings("serial")
public class GameCourt extends JPanel {

    // the state of the game logic
    private Rocks rocks; // the rocks
    private Pond pond; // The fishing pond
    private Fire fire; // the cooking fireplace
    private Furnace furnace; // the smelting furnace
    private Anvil anvil;
    private Player player;
    private Benjamin benjamin;
    private Raksha raksha;
    private LinkedList<Pathfinder.Point> movementQ;
    private int[][] board;
    private GameObj currentInteraction;
    private JTextPane status;
    private static BufferedImage img;

    // Game constants
    public static final int COURT_WIDTH = 750;
    public static final int COURT_HEIGHT = 700;

    public static final int ROCKS = 1;
    public static final int POND = 2;
    public static final int FIRE = 3;
    public static final int FURNACE = 4;
    public static final int ANVIL = 5;
    public static final int BENJAMIN = 6;
    public static final int RAKSHA = 7;
    public static final int UNWALKABLE = 8;
    public static final String IMG_FILE = "files/grass_background.jpg";

    public static final int INTERVAL = 60;

    public GameCourt(JTextPane status) {

        setBorder(BorderFactory.createLineBorder(Color.BLACK));
        movementQ = new LinkedList<Pathfinder.Point>();

        Timer timer = new Timer(INTERVAL, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                tick();
            }
        });
        timer.start();

        this.status = status;
        try {
            if (img == null) {
                img = ImageIO.read(new File(IMG_FILE));
            }
        } catch (IOException e) {
            System.out.println("Internal Error:" + e.getMessage());
        }
        board = new int[COURT_WIDTH][COURT_HEIGHT];

        for (int i = 0; i < COURT_WIDTH; i++) {
            for (int j = 0; j < COURT_HEIGHT; j++) {
                if (i <= 110) {
                    board[i][j] = GameCourt.UNWALKABLE;
                }
                if (i >= 0 && j >= Rocks.INIT_POS_Y && i <= 120 && j <= Rocks.INIT_POS_Y + Rocks.SIZE) {
                    board[i][j] = GameCourt.ROCKS;
                }

                if (i >= 0 && j >= Fire.INIT_POS_Y && i <= 120 && j <= Fire.INIT_POS_Y + Fire.SIZE) {
                    board[i][j] = GameCourt.FIRE;
                }

                if (i >= 0 && j >= Pond.INIT_POS_Y && i <= Pond.INIT_POS_X + Pond.SIZE
                        && j <= Pond.INIT_POS_Y + Pond.SIZE) {
                    board[i][j] = GameCourt.POND;
                }

                if (i >= Furnace.INIT_POS_X && j >= Furnace.INIT_POS_Y && i <= Furnace.INIT_POS_X + Furnace.SIZE
                        && j <= Furnace.INIT_POS_Y + Furnace.SIZE) {
                    board[i][j] = GameCourt.FURNACE;
                }

                if (i >= Benjamin.INIT_POS_X && j >= Benjamin.INIT_POS_Y - 50
                        && i <= Benjamin.INIT_POS_X + Benjamin.SIZE && j < COURT_HEIGHT) {
                    board[i][j] = GameCourt.BENJAMIN;
                }

                if (i >= 0 && j >= Anvil.INIT_POS_Y && i <= 120 && j <= Anvil.INIT_POS_Y + Anvil.SIZE) {
                    board[i][j] = GameCourt.ANVIL;
                }

                if (i >= Raksha.INIT_POS_X - 40 && j >= Raksha.INIT_POS_Y && i < COURT_WIDTH
                        && j <= Raksha.SIZE + Raksha.INIT_POS_Y) {
                    board[i][j] = GameCourt.RAKSHA;
                }
            }

        }

        reset();

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Point p = e.getPoint();

                // updates the model given the coordinates of the mouseclick
                GameObj g = convertPoint(p);

                LinkedList<Pathfinder.Point> path = Pathfinder.BFS(board, player.getPx(), player.getPy(), p.x, p.y);
                if (path != null)
                    movements(path);
                if (g != null) {
                    Point po = g.getInteractPos();
                    movements(Pathfinder.BFS(board, player.getPx(), player.getPy(), po.x, po.y));
                    currentInteraction = g;
                }

                repaint(); // repaints the game board
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
                if (board[p.x][p.y] >= 1 && board[p.x][p.y] <= 7) {
                    setCursor(new Cursor(Cursor.HAND_CURSOR));
                    flag = true;
                }
                if (!flag) {
                    setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                }
            }
        });
    }

    public void movements(LinkedList<Pathfinder.Point> q) {
        movementQ = q;
    }

    public GameObj convertPoint(Point p) {
        switch (board[p.x][p.y]) {
        case 1:
            return rocks;
        case 2:
            return pond;
        case 3:
            return fire;
        case 4:
            return furnace;
        case 5:
            return anvil;
        case 6:
            return benjamin;
        case 7:
            return raksha;
        default:
            return null;
        }
    }

    /**
     * (Re-)set the game to its initial state.
     */
    public void reset() {
        pond = new Pond(COURT_WIDTH, COURT_HEIGHT, "Sample");
        fire = new Fire(COURT_WIDTH, COURT_HEIGHT, "Sample");
        rocks = new Rocks(COURT_WIDTH, COURT_HEIGHT, "Sample");
        furnace = new Furnace(COURT_WIDTH, COURT_HEIGHT, "Sample");
        anvil = new Anvil(COURT_WIDTH, COURT_HEIGHT, "Sample");
        player = new Player(COURT_WIDTH, COURT_HEIGHT, "Sample");
        benjamin = new Benjamin(COURT_WIDTH, COURT_HEIGHT, "Sample");
        raksha = new Raksha(COURT_WIDTH, COURT_HEIGHT, "Sample");
        Player.clearInventory();
        player.move(Player.INIT_POS_X, Player.INIT_POS_Y);
        InventoryView.reset();
        requestFocusInWindow();
        repaint();
        status.setText("Welcome to 2D-Scape. Click on Sir. Benjamin (the white knight) to learn more.");

    }

    /**
     * This method is called every time the timer defined in the constructor
     * triggers.
     */
    void tick() {
        if (status.getText().equals("You died :(")) {
            Timer timer = new Timer(1500, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    status.setText("Welcome to 2D-Scape. Click on Sir. Benjamin (the white knight) to learn more.");
                    reset();
                }
            });
            timer.setRepeats(false);
            timer.start();
            

        }
        if (status.getText().equals("You did it!! Congratulations on becoming Gielenor's hero")) {
            Timer timer = new Timer(2500, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {

                    status.setText("Welcome to 2D-Scape. Click on Sir. Benjamin (the white knight) to learn more.");
                    reset();
                }
            });
            timer.setRepeats(false);
            timer.start();
   
        }

        for (int i = 0; i <= 20; i++) {
            if (movementQ.size() > 0) {
                Pathfinder.Point p = movementQ.removeFirst();
                player.move(p.x, p.y);
            }
        }
        if (currentInteraction != null) {
            if (player.getPx() == currentInteraction.getInteractPos().x
                    && player.getPy() == currentInteraction.getInteractPos().y) {
                currentInteraction.leftClick(this.status);
                if (currentInteraction instanceof Raksha) {
                    Raksha r = (Raksha) currentInteraction;
                    if (r.getAttackableState()) {
                        r.startEncounter(player, status);

                    }
                }
                currentInteraction = null;
            }
        }

        repaint();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(img, 0, 0, null);
        rocks.draw(g);
        pond.draw(g);
        fire.draw(g);
        furnace.draw(g);
        anvil.draw(g);
        player.draw(g);
        benjamin.draw(g);
        raksha.draw(g);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(COURT_WIDTH, COURT_HEIGHT);
    }

}