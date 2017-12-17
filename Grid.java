package gameoflife;

/**
 *
 * @author Loux
 */
public class Grid {
    
    private final Geometry geometry_;
    
    public Grid(Geometry geometry) {
        geometry_ = geometry;
    }
    
    public void computeNextGeneration(Strategy strategy) {
        for (Cell cell : geometry_) {
            cell.computeNextState(strategy);
        }
    }
    
    public void tick() {
        for (Cell cell : geometry_) {
            cell.tick();
        }
    }
    
    @Override
    public String toString() {
        return geometry_.toString();
    }
    
}
