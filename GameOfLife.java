package gameoflife;

import gameoflife.Cell.State;
import java.awt.Graphics;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;

/**
 *
 * @author Loux
 */
public class GameOfLife {

    private final Geometry geometry_;

    public GameOfLife(Geometry geometry) {
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
            State nextState = strategy.nextState(cell);
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
