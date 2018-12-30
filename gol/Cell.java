package gol;

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
    private State state_, nextState_;

    public Cell() {
        neighbours_ = new ArrayList<>();
        state_ = State.Dead;
    }

    public void addNeighbour(Cell neighbour) {
        neighbours_.add(neighbour);
    }

    public State setState(State state) {
        State previousState = state_;
        state_ = state;
        return previousState;
    }

    public State getState() {
        return state_;
    }

    public void setNextState(State state) {
        nextState_ = state;
    }

    public boolean evolve() {
        boolean hasEvolved = (!state_.equals(nextState_));
        state_ = nextState_;
        nextState_ = null;
        return hasEvolved;
    }

    public boolean isAlive() {
        return getState().equals(State.Alive);
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

    public int getNumberOfNeighbours() {
        return (neighbours_.size());
    }

    @Override
    public String toString() {
        return state_.toString();
    }
}
