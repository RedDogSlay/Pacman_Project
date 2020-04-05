package pacman;

import java.awt.EventQueue;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author jiayu
 */
public class StartTest {
    /**
     * Test of main method, of class Start.
     */
    @Test
    public void testMain() {
        System.out.println("main test is starting");
        String[] args = null;
        Start.main(args);
        EventQueue.invokeLater(() -> {
            Start ex = new Start();
            ex.setVisible(true);
        });
        System.out.println("main test is successful");
    }
    
}
