package gol;

import java.util.Random;

/**
 *
 * @author Loux
 */
public class GameOfLife {
    
    public static final void main(String[] args) {
        Geometry geometry = new Geometry.Rectangle(5, 2);
        Random random = new Random();
        for (Cell cell : geometry) {
            if (random.nextBoolean()) {
                cell.setState(Cell.State.Alive);
            }
        }
        System.out.println(geometry.toString());
        Strategy strategy = new Strategy.Default();
        boolean hasEvolved;
        do {
            for (Cell cell : geometry) {
                Cell.State nextState = strategy.nextState(cell);
                cell.setNextState(nextState);
            }
            hasEvolved = false;
            for (Cell cell : geometry) {
                hasEvolved |= cell.evolve();
            }
            System.out.println(geometry.toString());
        } while (hasEvolved);
    }

}
