package gameoflife;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Arrays;
import java.util.Iterator;
import javax.swing.JPanel;

/**
 *
 * @author Loux
 */
public abstract class Geometry implements Iterable<Cell> {

    protected Cell[][] cells_;

    public static class Square extends Geometry {

        public Square(int nbRows) {
            cells_ = constructCells(nbRows);
        }

        private Cell[][] constructCells(int nbRows) {
            Cell[][] cells = new Cell[nbRows][nbRows];
            for (int ind = 0; ind < nbRows; ind++) {
                cells[ind][0] = new Cell();
                cells[0][ind] = new Cell();
                cells[nbRows - 1][ind] = new Cell();
                cells[ind][nbRows - 1] = new Cell();
            }
            for (int i = 1; i < nbRows - 1; i++) {
                for (int j = 1; j < nbRows - 1; j++) {
                    Cell cell = new Cell();
                    cells[i][j] = cell;
                }
            }
            for (int i = 1; i < nbRows - 1; i++) {
                for (int j = 1; j < nbRows - 1; j++) {
                    Cell cell = cells[i][j];
                    cell.addNeighbour(cells[i - 1][j - 1]);
                    cell.addNeighbour(cells[i - 1][j]);
                    cell.addNeighbour(cells[i - 1][j + 1]);
                    cell.addNeighbour(cells[i][j + 1]);
                    cell.addNeighbour(cells[i + 1][j + 1]);
                    cell.addNeighbour(cells[i + 1][j]);
                    cell.addNeighbour(cells[i + 1][j - 1]);
                    cell.addNeighbour(cells[i][j - 1]);
                }
            }
            for (int ind = 1; ind < nbRows - 1; ind++) {
                cells[ind][0].addNeighbour(cells[ind][1]);
                cells[ind][0].addNeighbour(cells[ind - 1][0]);
                cells[ind][0].addNeighbour(cells[ind - 1][1]);
                cells[ind][0].addNeighbour(cells[ind + 1][1]);
                cells[ind][0].addNeighbour(cells[ind + 1][0]);
                //
                cells[0][ind].addNeighbour(cells[1][ind]);
                cells[0][ind].addNeighbour(cells[0][ind - 1]);
                cells[0][ind].addNeighbour(cells[1][ind - 1]);
                cells[0][ind].addNeighbour(cells[1][ind + 1]);
                cells[0][ind].addNeighbour(cells[0][ind + 1]);
                //
                cells[ind][nbRows - 1].addNeighbour(cells[ind][nbRows - 2]);
                cells[ind][nbRows - 1].addNeighbour(cells[ind - 1][nbRows - 1]);
                cells[ind][nbRows - 1].addNeighbour(cells[ind - 1][nbRows - 2]);
                cells[ind][nbRows - 1].addNeighbour(cells[ind + 1][nbRows - 1]);
                cells[ind][nbRows - 1].addNeighbour(cells[ind + 1][nbRows - 2]);
                //
                cells[nbRows - 1][ind].addNeighbour(cells[nbRows - 2][ind]);
                cells[nbRows - 1][ind].addNeighbour(cells[nbRows - 1][ind - 1]);
                cells[nbRows - 1][ind].addNeighbour(cells[nbRows - 2][ind - 1]);
                cells[nbRows - 1][ind].addNeighbour(cells[nbRows - 2][ind + 1]);
                cells[nbRows - 1][ind].addNeighbour(cells[nbRows - 1][ind + 1]);
            }
            cells[0][0].addNeighbour(cells[0][1]);
            cells[0][0].addNeighbour(cells[1][1]);
            cells[0][0].addNeighbour(cells[1][0]);
            //
            cells[0][nbRows - 1].addNeighbour(cells[0][nbRows - 2]);
            cells[0][nbRows - 1].addNeighbour(cells[1][nbRows - 2]);
            cells[0][nbRows - 1].addNeighbour(cells[1][nbRows - 1]);
            //
            cells[nbRows - 1][0].addNeighbour(cells[nbRows - 2][0]);
            cells[nbRows - 1][0].addNeighbour(cells[nbRows - 2][1]);
            cells[nbRows - 1][0].addNeighbour(cells[nbRows - 1][1]);
            //
            cells[nbRows - 1][nbRows - 1].addNeighbour(cells[nbRows - 2][nbRows - 2]);
            cells[nbRows - 1][nbRows - 1].addNeighbour(cells[nbRows - 2][nbRows - 1]);
            cells[nbRows - 1][nbRows - 1].addNeighbour(cells[nbRows - 1][nbRows - 2]);
            return cells;
        }

        @Override
        public int getWidth() {
            return (cells_.length);
        }

        @Override
        public int getHeight() {
            return (getWidth());
        }
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (int ind = 0; ind < cells_.length; ind++) {
            builder.append(Arrays.toString(cells_[ind]));
            builder.append("\n");
        }
        return builder.toString();
    }
    
    public Cell[][] getCells() {
        return cells_.clone();
    }
    
    public abstract int getWidth();
    
    public abstract int getHeight();

    @Override
    public Iterator<Cell> iterator() {
        Iterator<Cell> iterator = new Iterator<Cell>() {
            private int i_ = 0, j_ = 0;

            @Override
            public boolean hasNext() {
                return !(i_ == cells_.length - 1 && j_ == cells_[i_].length - 1);
            }

            @Override
            public Cell next() {
                Cell next = cells_[i_][j_];
                j_ = (j_ + 1) % cells_[i_].length;
                if (j_ == 0) {
                    i_ = (i_ + 1);
                }
                return (next);
            }
        };
        return iterator;
    }

}
