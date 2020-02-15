/**
 * This program is a rudimentary version of Pacman that allows you to
 * control a sprite of Pacman and collect the dots. When starting,
 * the user is presented with a prompt to start the game by pressing S.
 * Once the game is started the user can control the Pacman sprite using the
 * Arrow Keys.
 */

package pacman;

/**
 *
 * @author Peter Adamopoulos, Jiayu Yan, Zanxiang Wang
 */

import java.awt.EventQueue;
import javax.swing.JFrame;

public class Start extends JFrame {
    public Start() {
        initUI();
    }
    
    private void initUI() {
        add(new Pacman_Game());
        setTitle("Pacman");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(380, 420);
        setLocationRelativeTo(null);
    }
    
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            Start ex = new Start();
            ex.setVisible(true);
        });
    }
}