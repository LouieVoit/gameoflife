package gameoflife;

import gameoflife.Geometry.Square;
import java.util.concurrent.TimeUnit;

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
        Square geometry = new Geometry.Square(10);
        geometry.setRandomInitialSeed();
        Grid grid = new Grid(geometry);
        Strategy strategy = new Strategy.Default();
        System.out.println(grid.toString());
        for (int i = 0; i < 100; i++) {
            grid.computeNextGeneration(strategy);
            grid.tick();
            System.out.println(grid.toString());
            TimeUnit.SECONDS.sleep(1);
        }
    }

}
