package gameoflife;

import static gameoflife.Cell.State.Dead;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Loux
 */
public class Cell {
    public static enum State {
        Alive("A"), Dead("D");
        
        private final String name_;

        private State(String name) {
            this.name_ = name;
        }
        
        @Override
        public String toString() {
            return name_;
        }
    }
    
    private final List<Cell> neighbours_;
    private int currentState_;
    private List<State> life_;
    
    public Cell() {
        neighbours_ = new ArrayList<>();
        currentState_ = 0;
        life_=new ArrayList<>();
        life_.add(Dead);
    }
    
    public void addNeighbour(Cell neighbour) {
        neighbours_.add(neighbour);
    }
    
    public void computeNextState(Strategy strategy) {
        State nextState = strategy.nextState(this);
        life_.add(nextState);
    }
    
    public int tick() {
        return (currentState_++);
    }
    
    public State getCurrentState() {
        return life_.get(currentState_);
    }
    
    public void setCurrentState(State state) {
        life_.set(currentState_, state);
    }
    
    public boolean isAlive() {
        return (getCurrentState().equals(State.Alive));
    }
    
    public boolean isDead() {
        return (getCurrentState().equals(State.Dead));
    }
    
    public int getNumberOfAliveNeighbours() {
        int result = 0;
        for (Cell neighbour : neighbours_) {
            if (neighbour.isAlive()) {
                result++;
            }
        }
        return result;
    }
    
    public int getNumberOfDeadNeighbours() {
        return (neighbours_.size() - getNumberOfAliveNeighbours());
    }
    
    
    @Override
    public String toString() {
        return getCurrentState().toString();
    }
    
}
