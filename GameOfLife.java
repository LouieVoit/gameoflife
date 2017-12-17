package gameoflife;

import gameoflife.Geometry.Square;
import java.awt.Color;
import java.util.concurrent.TimeUnit;
import javax.swing.JFrame;

/**
 *
 * @author Loux
 */
public class GameOfLife {
    
    /**
     * @param args the command line arguments
     * @throws java.lang.InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {       
        Square geometry = new Geometry.Square(30);
        geometry.setRandomInitialSeed();
        Grid grid = new Grid(geometry);
        Strategy strategy = new Strategy.Default();
        
        JFrame frame = new JFrame();
        frame.setSize(600,600);
        frame.getContentPane().add(grid);
        frame.setLocationRelativeTo(null);
        frame.setBackground(Color.LIGHT_GRAY);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        
        for (int i = 0; i < 100; i++) {
            grid.nextGeneration(strategy);
            grid.repaint();
            TimeUnit.MILLISECONDS.sleep(200);
        }
    }

}
