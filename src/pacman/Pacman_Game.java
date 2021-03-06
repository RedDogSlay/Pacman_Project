/**
 * This program is a rudimentary version of Pacman that allows you to
 * control a sprite of Pacman and collect the dots while dodging the enemy ghosts.
 * When starting, the user is presented with a prompt to start the game by pressing S.
 * Once the game is started the user can control the Pacman sprite using the
 * Arrow Keys. The user gains points by collecting dots and has a maximum of 3 lives.
 * Once the user collects all the dots, the map resets and the user can keep gaining
 * a higher score. Once the player runs out of lives, the game resets and the player
 * restarts with 0 points.
 */

package pacman;

/**
 *
 * @author Peter Adamopoulos, Jiayu Yan, Zanxiang Wang
 */

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Event;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Pacman_Game extends JPanel implements ActionListener {
    
    private Dimension d;
    private final Font smallFont = new Font("Helvetica", Font.BOLD, 14);
    private final Color dotColor = new Color(192, 192, 0);
    private Color mapColor;
    
    private boolean inGame = false;
    private boolean dying = false;
    
    private final int CELL_SIZE = 24;
    private final int N_CELLS = 15;
    private final int SCREEN_SIZE = N_CELLS * CELL_SIZE;
    private final int PACMAN_SPEED = 6;
    private final int MAX_GHOSTS = 12;
    private int N_GHOSTS = 6;
    private int lives, score;
    
    private int[] dx, dy;
    private int[] ghost_x, ghost_y, ghost_dx, ghost_dy, ghostSpeed;
    private Image pacman_sprite;
    private Image ghost_sprite;
    
    private int pacman_x, pacman_y, pacmand_x, pacmand_y;
    private int req_dx, req_dy;
    
    // Creates the map for the game
    private final short map[] = {19, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 22,
        17, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 20,
        17, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 20,
        17, 16, 16, 16, 16, 16, 16, 24, 16, 16, 16, 16, 16, 16, 20,
        17, 16, 16, 16, 16, 16, 20, 0, 17, 16, 16, 16, 16, 16, 20,
        17, 16, 16, 16, 16, 16, 20, 0, 17, 16, 16, 16, 16, 16, 20,
        17, 16, 16, 16, 24, 24, 28, 0, 25, 24, 24, 16, 16, 16, 20,
        17, 16, 16, 20,  0,  0,  0, 0,  0,  0,  0, 17, 16, 16, 20,
        17, 16, 16, 16, 18, 18, 22, 0, 19, 18, 18, 16, 16, 16, 20,
        17, 16, 16, 16, 16, 16, 20, 0, 17, 16, 16, 16, 16, 16, 20,
        17, 16, 16, 16, 16, 16, 20, 0, 17, 16, 16, 16, 16, 16, 20,
        17, 16, 16, 16, 16, 16, 16, 18, 16, 16, 16, 16, 16, 16, 20,
        17, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 20,
        17, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 20,
        25, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24,24, 24, 28
    };
    
    private final int validSpeeds[] = {1, 2, 3, 4, 6, 8};
    private final int maxSpeed = 6;
    
    private int currentSpeed = 3;
    private short[] screenData;
    private Timer timer;
    
    public Pacman_Game() {
        loadImages();
        initVariables();
        initPacman_Game();
    }
    
    private void initPacman_Game() {
        addKeyListener(new TAdapter());
        setFocusable(true);
        setBackground(Color.black);
    }
    
    private void initVariables() {
        screenData = new short[N_CELLS * N_CELLS];
        mapColor = new Color(25, 25, 166);
        d = new Dimension(400, 400);
        dx = new int[4];
        dy = new int[4];
        ghost_x = new int[MAX_GHOSTS];
        ghost_dx = new int[MAX_GHOSTS];
        ghost_y = new int[MAX_GHOSTS];
        ghost_dy = new int[MAX_GHOSTS];
        ghostSpeed = new int[MAX_GHOSTS];
        timer = new Timer(40, this);
        timer.start();
    }
    
    @Override
    public void addNotify() {
        super.addNotify();
        initGame();
    }


    private void playGame(Graphics2D g2d) {
        if (dying) {

            death();

        } else {

            movePacman();
            drawPacman(g2d);
            moveGhosts(g2d);
            checkMaze();
        }
    }
    
    //Creates the intro screen for the game before you play
    private void showIntroScreen(Graphics2D g2d) {

        g2d.setColor(new Color(0, 32, 48));
        g2d.fillRect(50, SCREEN_SIZE / 2 - 30, SCREEN_SIZE - 100, 50);
        g2d.setColor(Color.white);
        g2d.drawRect(50, SCREEN_SIZE / 2 - 30, SCREEN_SIZE - 100, 50);
        
        String s = "Press S to Start Game.";
        Font small = new Font("Times New Roman", Font.BOLD, 14);
        FontMetrics metr = this.getFontMetrics(small);
        
        g2d.setColor(Color.white);
        g2d.setFont(small);
        g2d.drawString(s, (SCREEN_SIZE - metr.stringWidth(s)) / 2, SCREEN_SIZE / 2);
    }
    
    private void drawScore(Graphics2D g) {

        int i;
        String s;

        g.setFont(smallFont);
        g.setColor(new Color(96, 128, 255));
        s = "Score: " + score;
        g.drawString(s, SCREEN_SIZE / 2 + 96, SCREEN_SIZE + 16);

        for (i = 0; i < lives; i++) {
            g.drawImage(pacman_sprite, i * 28 + 8, SCREEN_SIZE + 1, this);
        }
    }

    private void checkMaze() {

        short i = 0;
        boolean finished = true;

        while (i < N_CELLS * N_CELLS && finished) {

            if ((screenData[i] & 48) != 0) {
                finished = false;
            }

            i++;
        }

        if (finished) {

            score += 50;

            if (N_GHOSTS < MAX_GHOSTS) {
                N_GHOSTS++;
            }

            if (currentSpeed < maxSpeed) {
                currentSpeed++;
            }
            initLevel();
        }
    }

    private void death() {

        lives--;

        if (lives == 0) {
            inGame = false;
        }

        continueLevel();
    }

    private void moveGhosts(Graphics2D g2d) {

        short i;
        int pos;
        int count;

        for (i = 0; i < N_GHOSTS; i++) {
            if (ghost_x[i] % CELL_SIZE == 0 && ghost_y[i] % CELL_SIZE == 0) {
                pos = ghost_x[i] / CELL_SIZE + N_CELLS * (int) (ghost_y[i] / CELL_SIZE);

                count = 0;

                if ((screenData[pos] & 1) == 0 && ghost_dx[i] != 1) {
                    dx[count] = -1;
                    dy[count] = 0;
                    count++;
                }

                if ((screenData[pos] & 2) == 0 && ghost_dy[i] != 1) {
                    dx[count] = 0;
                    dy[count] = -1;
                    count++;
                }

                if ((screenData[pos] & 4) == 0 && ghost_dx[i] != -1) {
                    dx[count] = 1;
                    dy[count] = 0;
                    count++;
                }

                if ((screenData[pos] & 8) == 0 && ghost_dy[i] != -1) {
                    dx[count] = 0;
                    dy[count] = 1;
                    count++;
                }

                if (count == 0) {

                    if ((screenData[pos] & 15) == 15) {
                        ghost_dx[i] = 0;
                        ghost_dy[i] = 0;
                    } else {
                        ghost_dx[i] = -ghost_dx[i];
                        ghost_dy[i] = -ghost_dy[i];
                    }

                } else {

                    count = (int) (Math.random() * count);

                    if (count > 3) {
                        count = 3;
                    }

                    ghost_dx[i] = dx[count];
                    ghost_dy[i] = dy[count];
                }

            }

            ghost_x[i] = ghost_x[i] + (ghost_dx[i] * ghostSpeed[i]);
            ghost_y[i] = ghost_y[i] + (ghost_dy[i] * ghostSpeed[i]);
            drawGhost(g2d, ghost_x[i] + 1, ghost_y[i] + 1);

            if (pacman_x > (ghost_x[i] - 12) && pacman_x < (ghost_x[i] + 12)
                    && pacman_y > (ghost_y[i] - 12) && pacman_y < (ghost_y[i] + 12)
                    && inGame) {

                dying = true;
            }
        }
    }

    private void drawGhost(Graphics2D g2d, int x, int y) {

        g2d.drawImage(ghost_sprite, x, y, this);
    }
    
    // Function for controlling Pacman
    private void movePacman() {
        int pos;
        short ch;
        
        if (req_dx == -pacmand_x && req_dy == -pacmand_y) {
            pacmand_x = req_dx;
            pacmand_y = req_dy;
        }
        if (pacman_x % CELL_SIZE == 0 && pacman_y % CELL_SIZE == 0) {
            pos = pacman_x / CELL_SIZE + N_CELLS * (int) (pacman_y / CELL_SIZE);
            ch = screenData[pos];
            if ((ch & 16) != 0) {
                screenData[pos] = (short) (ch & 15);
                score++;
            }
            if (req_dx != 0 || req_dy != 0) {
                if (!((req_dx == -1 && req_dy == 0 && (ch & 1) != 0)
                        || (req_dx == 1 && req_dy == 0 && (ch & 4) != 0)
                        || (req_dx == 0 && req_dy == -1 && (ch & 2) != 0)
                        || (req_dx == 0 && req_dy == 1 && (ch & 8) != 0))) {
                    pacmand_x = req_dx;
                    pacmand_y = req_dy;
                }
            }
            // Checks if Pacman is not moving
            if ((pacmand_x == -1 && pacmand_y == 0 && (ch & 1) != 0)
                    || (pacmand_x == 1 && pacmand_y == 0 && (ch & 4) != 0)
                    || (pacmand_x == 0 && pacmand_y == -1 && (ch & 2) != 0)
                    || (pacmand_x == 0 && pacmand_y == 1 && (ch & 8) != 0)) {
                pacmand_x = 0;
                pacmand_y = 0;
            }
        }
        pacman_x = pacman_x + PACMAN_SPEED * pacmand_x;
        pacman_y = pacman_y + PACMAN_SPEED * pacmand_y;
    }
    
    private void drawPacman(Graphics2D g2d) {
        {
            g2d.drawImage(pacman_sprite, pacman_x + 1, pacman_y + 1, this);
        }
    }
    
    // Function to create the map
    private void drawLevel(Graphics2D g2d) {
        
        short i = 0;
        int x, y;
        
        for (y = 0; y < SCREEN_SIZE; y += CELL_SIZE) {
            for (x = 0; x < SCREEN_SIZE; x += CELL_SIZE) {

                g2d.setColor(mapColor);
                g2d.setStroke(new BasicStroke(2));
                if ((screenData[i] & 1) != 0) { 
                    g2d.drawLine(x, y, x, y + CELL_SIZE - 1);
                }
                if ((screenData[i] & 2) != 0) { 
                    g2d.drawLine(x, y, x + CELL_SIZE - 1, y);
                }
                if ((screenData[i] & 4) != 0) { 
                    g2d.drawLine(x + CELL_SIZE - 1, y, x + CELL_SIZE - 1,
                            y + CELL_SIZE - 1);
                }
                if ((screenData[i] & 8) != 0) { 
                    g2d.drawLine(x, y + CELL_SIZE - 1, x + CELL_SIZE - 1,
                            y + CELL_SIZE - 1);
                }
                if ((screenData[i] & 16) != 0) { 
                    g2d.setColor(dotColor);
                    g2d.fillRect(x + 11, y + 11, 2, 2);
                }
                i++;
            }
        }
    }
    
    // Initializes the game
    private void initGame() {
        initLevel();
        lives = 3;
        score = 0;
        N_GHOSTS = 6;
        currentSpeed = 3;
    }
    
    private void initLevel() {
        int i;
        for (i = 0; i < N_CELLS * N_CELLS; i++) {
            screenData[i] = map[i];
        }
        continueLevel();
    }
    
    private void continueLevel() {

        short i;
        int dx = 1;
        int random;

        for (i = 0; i < N_GHOSTS; i++) {

            ghost_y[i] = 4 * CELL_SIZE;
            ghost_x[i] = 4 * CELL_SIZE;
            ghost_dy[i] = 0;
            ghost_dx[i] = dx;
            dx = -dx;
            random = (int) (Math.random() * (currentSpeed + 1));

            if (random > currentSpeed) {
                random = currentSpeed;
            }

            ghostSpeed[i] = validSpeeds[random];
        }
        pacman_x = 7 * CELL_SIZE;
        pacman_y = 11 * CELL_SIZE;
        pacmand_x = 0;
        pacmand_y = 0;
        req_dx = 0;
        req_dy = 0;
        dying = false;
    }
    
    private void loadImages() {
        pacman_sprite = new ImageIcon("images/pacman.png").getImage();
        ghost_sprite = new ImageIcon("images/ghost.png").getImage();
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        doDrawing(g);
    }
    
    private void doDrawing(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        
        g2d.setColor(Color.black);
        g2d.fillRect(0, 0, d.width, d.height);
        
        drawLevel(g2d);
        drawScore(g2d);
        
        if (inGame) {
            playGame(g2d);
        } else {
            showIntroScreen(g2d);
        }
    }
    
    class TAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            
            int key = e.getKeyCode();
            
            if (inGame) {
                if (key == KeyEvent.VK_LEFT) {
                    req_dx = -1;
                    req_dy = 0;
                } else if (key == KeyEvent.VK_RIGHT) {
                    req_dx = 1;
                    req_dy = 0;
                } else if (key == KeyEvent.VK_UP) {
                    req_dx = 0;
                    req_dy = -1;
                } else if (key == KeyEvent.VK_DOWN) {
                    req_dx = 0;
                    req_dy = 1;
                } else if (key == KeyEvent.VK_ESCAPE && timer.isRunning()) {
                    inGame = false;
                } else if (key == KeyEvent.VK_SPACE) {
                    if (timer.isRunning()) {
                        timer.stop();
                    } else {
                        timer.start();
                    }
                }
            } else {
                if (key == 's' || key == 'S') {
                    inGame = true;
                    initGame();
                }
            }
        }
        
        @Override
        public void keyReleased(KeyEvent e) {
            int key = e.getKeyCode();
            if (key == Event.LEFT || key == Event.RIGHT
                    || key == Event.UP || key == Event.DOWN) {
                //req_dx = 0;
                //req_dy = 0;
            }
        }
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
    }
}