package gameoflife;

import gameoflife.Cell.State;
import static gameoflife.Cell.State.Alive;
import static gameoflife.Cell.State.Dead;

/**
 *
 * @author Loux
 */
public abstract class Strategy {

    public abstract State nextState(Cell cell);

    public static class Default extends Strategy {

        @Override
        public State nextState(Cell cell) {
            int numberOfAliveNeighbours = cell.getNumberOfAliveNeighbours();
            State result = cell.getCurrentState();
            switch (result) {
                case Alive:
                    if (numberOfAliveNeighbours < 2) {
                        result = Dead;
                    } else if (numberOfAliveNeighbours == 2 || numberOfAliveNeighbours == 3) {
                        result = Alive;
                    } else {
                        result = Dead;
                    }
                    break;
                case Dead:
                    if (numberOfAliveNeighbours == 3) {
                        result = Alive;
                    }
                    break;
            }
            return result;
        }

    }

}
