/**
 *
 * @author Loux
 */
public abstract class Strategy {

    public abstract Cell.State nextState(Cell cell);

    public static class Default extends Strategy {

        @Override
        public Cell.State nextState(Cell cell) {
            int numberOfAliveNeighbours = cell.getNumberOfAliveNeighbours();
            Cell.State result = cell.getState();
            switch (result) {
                case Alive:
                    if (numberOfAliveNeighbours < 2) {
                        result = Cell.State.Dead;
                    } else if (numberOfAliveNeighbours == 2 || numberOfAliveNeighbours == 3) {
                        result = Cell.State.Alive;
                    } else {
                        result = Cell.State.Dead;
                    }
                    break;
                case Dead:
                    if (numberOfAliveNeighbours == 3) {
                        result = Cell.State.Alive;
                    }
                    break;
            }
            return result;
        }

    }

}
