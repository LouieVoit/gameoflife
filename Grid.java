package gameoflife;

import gameoflife.Cell.State;
import java.awt.Graphics;
import javax.swing.JPanel;

/**
 *
 * @author Loux
 */
public class Grid extends JPanel {
    
    private final Geometry geometry_;
    
    public Grid(Geometry geometry) {
        geometry_ = geometry;
    }
    
    public void nextGeneration(Strategy strategy) {
        for (Cell cell : geometry_) {
            State nextState = strategy.nextState(cell);
            cell.setNextState(nextState);
        }
        for (Cell cell : geometry_) {
            cell.evolve();
        }
    }
    
    public void evolve(){
        for (Cell cell : geometry_) {
            cell.evolve();
        }
    }
    
    public void regress(){
        for (Cell cell : geometry_) {
            cell.regress();
        }
    }
    
    @Override
    public void paint(Graphics g){
        geometry_.paint(g);
    }
    
    @Override
    public String toString() {
        return geometry_.toString();
    }
    
}
