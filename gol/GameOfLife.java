package gol;

import java.util.Random;

public class GameOfLife {

    public static final void randomInitialize(Geometry geometry) {
        Random random = new Random();
        for (Cell cell : geometry) {
//            if (random.nextInt(100) > 98) {
            if (random.nextBoolean()) {
                cell.setState(Cell.State.Alive);
            }
        }
    }

    public static final boolean nextGeneration(Geometry geometry, Strategy strategy) {
        boolean hasEvolved;
        for (Cell cell : geometry) {
            Cell.State nextState = strategy.nextState(cell);
            cell.setNextState(nextState);
        }
        hasEvolved = false;
        for (Cell cell : geometry) {
            hasEvolved |= cell.evolve();
        }
        return hasEvolved;
    }

//    public static final void main(String[] args) {
//        Geometry geometry = new Geometry.Rectangle(5, 2);
//        GameOfLife.initialize(geometry);
//        System.out.println(geometry.toString());
//        Strategy strategy = new Strategy.Default();
//        boolean hasEvolved;
//        do {
//            hasEvolved = GameOfLife.nextGeneration(geometry, strategy);
//            System.out.println(geometry.toString());
//        } while (hasEvolved);
//    }
}
