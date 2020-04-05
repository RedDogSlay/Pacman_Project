package pacman;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author jiayu
 */
public class Pacman_GameTest {
    
    /**
     * Test of addNotify method, of class Pacman_Game.
     */
    @Test
    public void testAddNotify() {
        System.out.println("addNotify test is starting");
        Pacman_Game instance = new Pacman_Game();
        instance.addNotify();
        System.out.println("addNotify is successfull");

    }

    /**
     * Test of actionPerformed method, of class Pacman_Game.
     */
    @Test
    public void testActionPerformed() {
        System.out.println("actionPerformed test is starting");
        ActionEvent e = null;
        Pacman_Game instance = new Pacman_Game();
        instance.actionPerformed(e);
        System.out.println("actionPerformed is successfull");

    }
     /**
     * Test of paintComponent method, of class Pacman_Game.
     */
    @Test
    public void paintComponent() {
        System.out.println("paintComponent test is starting");
        Graphics g = null;
        Pacman_Game instance = new Pacman_Game();
        instance.paintComponent(g);
        System.out.println("paintComponent is successfull");

    }
    
}
