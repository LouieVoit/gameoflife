/**
 *
 * @author Loux
 */
public class GameOfLife {

    private Geometry geometry_;

    public GameOfLife() {
        geometry_ = new Geometry.Rectangle(100, 100);
    }

    public void setGeometry(Geometry geometry) {
        geometry_ = geometry;
    }

    public void setRandomInitialSeed() {
        for (Cell cell : geometry_) {
            double rndm = Math.random();
            if (rndm < 0.5) {
                cell.setState(Cell.State.Alive);
            }
        }
    }

    public boolean nextGeneration(Strategy strategy) {
        for (Cell cell : geometry_) {
            Cell.State nextState = strategy.nextState(cell);
            cell.setNextState(nextState);
        }
        boolean hasEvolved = false;
        for (Cell cell : geometry_) {
            hasEvolved |= cell.evolve();
        }
        return hasEvolved;
    }

    public Geometry getGeometry() {
        return geometry_;
    }

    @Override
    public String toString() {
        return geometry_.toString();
    }

}
